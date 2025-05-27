package pt.ul.fc.css.soccernow.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPAExpressions;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import pt.ul.fc.css.soccernow.domain.entities.game.QPlayerGameStats;
import pt.ul.fc.css.soccernow.domain.entities.user.Player;
import pt.ul.fc.css.soccernow.domain.entities.user.QPlayer;
import pt.ul.fc.css.soccernow.util.CardEnum;
import pt.ul.fc.css.soccernow.util.PlayerSearchParams;

import java.util.List;

public interface PlayerRepository extends SoftDeletedRepository<Player>, QuerydslPredicateExecutor<Player> {
    List<Player> findByDeletedAtIsNullAndName(String name);

    default List<Player> findNotDeletedByName(String name) {
        return findByDeletedAtIsNullAndName(name);
    }

    default List<Player> findAllNotDeleted(PlayerSearchParams params) {
        QPlayer player = QPlayer.player;
        QPlayerGameStats pgs = QPlayerGameStats.playerGameStats;

        BooleanBuilder predicate = new BooleanBuilder().and(player.deletedAt.isNull());

        if (params.getPlayerName() != null && !params.getPlayerName().trim().isEmpty()) {
            predicate.and(player.name.containsIgnoreCase(params.getPlayerName()));
        }

        if (params.getPreferredPosition() != null) {
            predicate.and(player.preferredPosition.eq(params.getPreferredPosition()));
        }

        if (params.getNumReceivedCards() != null) {
            predicate.and(JPAExpressions
                    .select(pgs.givenCard.count().coalesce(0L))
                    .from(pgs)
                    .where(pgs.player.eq(player).and(pgs.givenCard.ne(CardEnum.NONE)))
                    .eq(params.getNumReceivedCards().longValue()));
        }
        if (params.getMinReceivedCards() != null) {
            predicate.and(JPAExpressions
                    .select(pgs.givenCard.count().coalesce(0L))
                    .from(pgs)
                    .where(pgs.player.eq(player).and(pgs.givenCard.ne(CardEnum.NONE)))
                    .goe(params.getMinReceivedCards().longValue()));
        }
        if (params.getMaxReceivedCards() != null) {
            predicate.and(JPAExpressions
                    .select(pgs.givenCard.count().coalesce(0L))
                    .from(pgs)
                    .where(pgs.player.eq(player).and(pgs.givenCard.ne(CardEnum.NONE)))
                    .loe(params.getMaxReceivedCards().longValue()));
        }

        if (params.getScoredGoals() != null) {
            predicate.and(JPAExpressions
                    .select(pgs.scoredGoals.sum().coalesce(0))
                    .from(pgs)
                    .where(pgs.player.eq(player))
                    .eq(params.getScoredGoals()));
        }

        if (params.getMinScoredGoals() != null) {
            predicate.and(JPAExpressions
                    .select(pgs.scoredGoals.sum().coalesce(0))
                    .from(pgs)
                    .where(pgs.player.eq(player))
                    .gt(params.getMinScoredGoals()));
        }
        if (params.getMaxScoredGoals() != null) {
            predicate.and(JPAExpressions
                    .select(pgs.scoredGoals.sum().coalesce(0))
                    .from(pgs)
                    .where(pgs.player.eq(player))
                    .lt(params.getMaxScoredGoals()));
        }

        if (params.getNumGames() != null) {
            predicate.and(JPAExpressions
                    .select(pgs.count().coalesce(0L))
                    .from(pgs)
                    .where(pgs.player.eq(player))
                    .eq(params.getNumGames().longValue()));
        }

        if (params.getMinNumGames() != null) {
            predicate.and(JPAExpressions
                    .select(pgs.count().coalesce(0L))
                    .from(pgs)
                    .where(pgs.player.eq(player))
                    .goe(params.getMinNumGames().longValue()));
        }

        if (params.getMaxNumGames() != null) {
            predicate.and(JPAExpressions
                    .select(pgs.count().coalesce(0L))
                    .from(pgs)
                    .where(pgs.player.eq(player))
                    .loe(params.getMaxNumGames().longValue()));
        }

        Pageable pageable = params.getSize() != null
                ? PageRequest.of(0, params.getSize())
                : PageRequest.of(0, Integer.MAX_VALUE);

        return findAll(predicate, pageable).getContent();
    }
}
