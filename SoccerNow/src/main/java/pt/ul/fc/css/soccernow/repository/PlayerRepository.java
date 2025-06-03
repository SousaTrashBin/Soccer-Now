package pt.ul.fc.css.soccernow.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import pt.ul.fc.css.soccernow.domain.entities.game.QCard;
import pt.ul.fc.css.soccernow.domain.entities.game.QPlayerGameStats;
import pt.ul.fc.css.soccernow.domain.entities.user.Player;
import pt.ul.fc.css.soccernow.util.CardEnum;
import pt.ul.fc.css.soccernow.util.PlayerSearchParams;

import java.util.ArrayList;
import java.util.List;

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

        QPlayerGameStats statsCardSub = new QPlayerGameStats("statsCardSub");
        QCard cardSub = new QCard("cardSub");

        JPQLQuery<Long> cardCountSubquery = JPAExpressions
                .select(cardSub.count())
                .from(statsCardSub)
                .join(statsCardSub.receivedCards, cardSub)
                .where(statsCardSub.player.eq(player)
                        .and(cardSub.cardType.ne(CardEnum.NONE)));

        if (params.getNumReceivedCards() != null) {
            conditions.add(cardCountSubquery.eq(params.getNumReceivedCards().longValue()));
        }
        if (params.getMinReceivedCards() != null) {
            conditions.add(cardCountSubquery.gt(params.getMinReceivedCards().longValue()));
        }
        if (params.getMaxReceivedCards() != null) {
            conditions.add(cardCountSubquery.lt(params.getMaxReceivedCards().longValue()));
        }

        QPlayerGameStats statsGoalSub = new QPlayerGameStats("statsGoalSub");

        JPQLQuery<Integer> goalCountQuery = JPAExpressions
                .select(statsGoalSub.scoredGoals.sum().coalesce(0))
                .from(statsGoalSub)
                .where(statsGoalSub.player.eq(player));

        if (params.getNumScoredGoals() != null) {
            conditions.add(goalCountQuery.eq(params.getNumScoredGoals()));
        }
        if (params.getMinScoredGoals() != null) {
            conditions.add(goalCountQuery.gt(params.getMinScoredGoals()));
        }
        if (params.getMaxScoredGoals() != null) {
            conditions.add(goalCountQuery.lt(params.getMaxScoredGoals()));
        }

        QPlayerGameStats statsGameSub = new QPlayerGameStats("statsGameSub");

        JPQLQuery<Long> gameCountQuery = JPAExpressions
                .select(statsGameSub.count().coalesce(0L))
                .from(statsGameSub)
                .where(statsGameSub.player.eq(player));

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
