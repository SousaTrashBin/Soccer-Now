package pt.ul.fc.css.soccernow.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import pt.ul.fc.css.soccernow.domain.entities.Address;
import pt.ul.fc.css.soccernow.domain.entities.QAddress;
import pt.ul.fc.css.soccernow.domain.entities.game.Game;
import pt.ul.fc.css.soccernow.domain.entities.game.QGame;
import pt.ul.fc.css.soccernow.domain.entities.game.QGameStats;
import pt.ul.fc.css.soccernow.util.GameSearchParams;
import pt.ul.fc.css.soccernow.util.TimeOfDay;

import java.util.ArrayList;
import java.util.List;

import static pt.ul.fc.css.soccernow.domain.entities.game.QGame.game;

public interface GameRepository extends SoftDeletedRepository<Game> {
    default List<Game> findAllNotDeleted(GameSearchParams params) {
        List<BooleanExpression> conditions = new ArrayList<>();

        conditions.add(game.deletedAt.isNull());

        if (params.getTimeOfDay() != null) {
            TimeOfDay.TimeSpan span = TimeOfDay.toTimeSpan(params.getTimeOfDay());

            int startHour = span.start().getHour();
            int startMinute = span.start().getMinute();
            int startSecond = span.start().getSecond();

            int endHour = span.end().getHour();
            int endMinute = span.end().getMinute();
            int endSecond = span.end().getSecond();

            conditions.add(
                    game.happensIn.hour().goe(startHour)
                            .and(game.happensIn.hour().loe(endHour))
                            .and(game.happensIn.minute().goe(startMinute))
                            .and(game.happensIn.minute().loe(endMinute))
                            .and(game.happensIn.second().goe(startSecond))
                            .and(game.happensIn.second().loe(endSecond))
            );
        }

        if (!params.getStatuses().isEmpty()) {
            conditions.add(game.status.in(params.getStatuses()));
        }

        QGame goalSubGame = new QGame("goalSubGame");
        QGameStats goalSubStats = new QGameStats("goalSubStats");

        JPQLQuery<Integer> numberOfGoals = JPAExpressions
                .select(goalSubStats.teamOneGoals.add(goalSubStats.teamTwoGoals))
                .from(goalSubGame)
                .join(goalSubGame.gameStats, goalSubStats)
                .where(goalSubGame.eq(game));

        if (params.getNumGoals() != null) {
            conditions.add(numberOfGoals.eq(params.getNumGoals()));
        }
        if (params.getMinGoals() != null) {
            conditions.add(numberOfGoals.gt(params.getMinGoals()));
        }
        if (params.getMaxGoals() != null) {
            conditions.add(numberOfGoals.lt(params.getMaxGoals()));
        }

        QGame addressSubGame = new QGame("addressSubGame");
        QAddress addressSub = new QAddress("addressSub");

        JPQLQuery<Address> gameAddressSubquery = JPAExpressions
                .select(addressSubGame.locatedIn)
                .from(addressSubGame)
                .join(addressSubGame.locatedIn, addressSub)
                .where(addressSubGame.eq(game));

        List<BooleanExpression> addressConditions = new ArrayList<>();
        if (params.getPostalCode() != null) {
            addressConditions.add(addressSub.postalCode.eq(params.getPostalCode()));
        }
        if (params.getCity() != null) {
            addressConditions.add(addressSub.city.eq(params.getCity()));
        }
        if (params.getCountry() != null) {
            addressConditions.add(addressSub.country.eq(params.getCountry()));
        }
        if (params.getStreet() != null) {
            addressConditions.add(addressSub.street.eq(params.getStreet()));
        }

        if (!addressConditions.isEmpty()) {
            BooleanBuilder addressPredicate = new BooleanBuilder();
            addressConditions.forEach(addressPredicate::and);
            gameAddressSubquery.where(addressPredicate);
            conditions.add(game.locatedIn.in(gameAddressSubquery));
        }

        BooleanBuilder predicate = conditions.stream()
                .reduce(BooleanExpression::and)
                .map(BooleanBuilder::new)
                .orElse(new BooleanBuilder());

        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);

        return findAll(predicate, pageable).getContent();
    }
}
