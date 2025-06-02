package pt.ul.fc.css.soccernow.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import pt.ul.fc.css.soccernow.domain.entities.user.Referee;
import pt.ul.fc.css.soccernow.util.CardEnum;
import pt.ul.fc.css.soccernow.util.GameStatusEnum;
import pt.ul.fc.css.soccernow.util.RefereeSearchParams;

import java.util.ArrayList;
import java.util.List;

import static pt.ul.fc.css.soccernow.domain.entities.game.QCard.card;
import static pt.ul.fc.css.soccernow.domain.entities.game.QGame.game;
import static pt.ul.fc.css.soccernow.domain.entities.user.QReferee.referee;

public interface RefereeRepository extends SoftDeletedRepository<Referee> {
    default List<Referee> findAllNotDeleted(RefereeSearchParams params) {
        List<BooleanExpression> conditions = new ArrayList<>();

        conditions.add(referee.deletedAt.isNull());
        if (params.getName() != null && !params.getName().isBlank()) {
            conditions.add(referee.name.containsIgnoreCase(params.getName()));
        }
        JPQLQuery<Long> cardCountSubquery = JPAExpressions
                .select(card.count())
                .from(referee)
                .join(referee.issuedCards, card)
                .where(card.cardType.ne(CardEnum.NONE));
        if (params.getNumCardsGiven() != null) {
            conditions.add(cardCountSubquery.eq(params.getNumCardsGiven().longValue()));
        }
        if (params.getMinCardsGiven() != null) {
            conditions.add(cardCountSubquery.gt(params.getMinCardsGiven().longValue()));
        }
        if (params.getMaxCardsGiven() != null) {
            conditions.add(cardCountSubquery.lt(params.getMaxCardsGiven().longValue()));
        }
        JPQLQuery<Long> gameCountSubquery = JPAExpressions
                .select(game.count())
                .from(game)
                .where(game.status.eq(GameStatusEnum.CLOSED))
                .where(game.primaryReferee.eq(referee)
                        .or(game.secondaryReferees.contains(referee)));
        if (params.getNumGames() != null) {
            conditions.add(gameCountSubquery.eq(params.getNumGames().longValue()));
        }
        if (params.getMinGames() != null) {
            conditions.add(gameCountSubquery.gt(params.getMinGames().longValue()));
        }
        if (params.getMaxGames() != null) {
            conditions.add(gameCountSubquery.lt(params.getMaxGames().longValue()));
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
