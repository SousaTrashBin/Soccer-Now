package pt.ul.fc.css.soccernow.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import pt.ul.fc.css.soccernow.domain.entities.user.Player;
import pt.ul.fc.css.soccernow.util.CardEnum;
import pt.ul.fc.css.soccernow.util.PlayerSearchParams;

import java.util.ArrayList;
import java.util.List;

import static pt.ul.fc.css.soccernow.domain.entities.game.QCard.card;
import static pt.ul.fc.css.soccernow.domain.entities.game.QPlayerGameStats.playerGameStats;
import static pt.ul.fc.css.soccernow.domain.entities.user.QPlayer.player;

public interface PlayerRepository extends SoftDeletedRepository<Player> {
    List<Player> findByDeletedAtIsNullAndName(String name);

    default List<Player> findNotDeletedByName(String name) {
        return findByDeletedAtIsNullAndName(name);
    }

    default List<Player> findAllNotDeleted(PlayerSearchParams params) {
        List<BooleanExpression> conditions = new ArrayList<>();

        conditions.add(player.deletedAt.isNull());

        if (params.getName() != null && !params.getName().isBlank()) {
            conditions.add(player.name.containsIgnoreCase(params.getName()));
        }

        BooleanExpression positionCondition = params.getPreferredPositions().isEmpty()
                ? Expressions.TRUE
                : player.preferredPosition.in(params.getPreferredPositions());

        conditions.add(positionCondition);

        JPQLQuery<Long> cardCountSubquery = JPAExpressions
                .select(card.count())
                .from(playerGameStats)
                .join(playerGameStats.receivedCards, card)
                .where(playerGameStats.player.eq(player)
                        .and(card.cardType.ne(CardEnum.NONE)));

        if (params.getNumReceivedCards() != null) {
            conditions.add(cardCountSubquery.eq(params.getNumReceivedCards().longValue()));
        }
        if (params.getMinReceivedCards() != null) {
            conditions.add(cardCountSubquery.gt(params.getMinReceivedCards().longValue()));
        }
        if (params.getMaxReceivedCards() != null) {
            conditions.add(cardCountSubquery.lt(params.getMaxReceivedCards().longValue()));
        }

        JPQLQuery<Integer> goalCountQuery = JPAExpressions
                .select(playerGameStats.scoredGoals.sum().coalesce(0))
                .from(playerGameStats)
                .where(playerGameStats.player.eq(player));

        if (params.getNumScoredGoals() != null) {
            conditions.add(goalCountQuery.eq(params.getNumScoredGoals()));
        }
        if (params.getMinScoredGoals() != null) {
            conditions.add(goalCountQuery.gt(params.getMinScoredGoals()));
        }
        if (params.getMaxScoredGoals() != null) {
            conditions.add(goalCountQuery.lt(params.getMaxScoredGoals()));
        }

        JPQLQuery<Long> gameCountQuery = JPAExpressions
                .select(playerGameStats.count().coalesce(0L))
                .from(playerGameStats)
                .where(playerGameStats.player.eq(player));

        if (params.getNumGames() != null) {
            conditions.add(gameCountQuery.eq(params.getNumGames().longValue()));
        }
        if (params.getMinGames() != null) {
            conditions.add(gameCountQuery.gt(params.getMinGames().longValue()));
        }
        if (params.getMaxGames() != null) {
            conditions.add(gameCountQuery.lt(params.getMaxGames().longValue()));
        }

        BooleanBuilder predicate = conditions.stream()
                .reduce(BooleanExpression::and)
                .map(BooleanBuilder::new)
                .orElse(new BooleanBuilder());

        Pageable pageable = params.getSize() != null
                ? PageRequest.of(0, params.getSize())
                : PageRequest.of(0, Integer.MAX_VALUE);

        return findAll(predicate, pageable).getContent();
    }
}
