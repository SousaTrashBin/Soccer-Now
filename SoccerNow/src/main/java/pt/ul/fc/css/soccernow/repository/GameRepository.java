package pt.ul.fc.css.soccernow.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import pt.ul.fc.css.soccernow.domain.entities.Address;
import pt.ul.fc.css.soccernow.domain.entities.game.Game;
import pt.ul.fc.css.soccernow.util.GameSearchParams;
import pt.ul.fc.css.soccernow.util.TimeOfDay;

import java.util.ArrayList;
import java.util.List;

import static pt.ul.fc.css.soccernow.domain.entities.QAddress.address;
import static pt.ul.fc.css.soccernow.domain.entities.game.QGame.game;
import static pt.ul.fc.css.soccernow.domain.entities.game.QGameStats.gameStats;

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

        JPQLQuery<Integer> numberOfGoals = JPAExpressions.select(gameStats.teamOneGoals.add(gameStats.teamTwoGoals))
                .from(game)
                .join(game.gameStats, gameStats);

        if (params.getNumGoals() != null) {
            conditions.add(numberOfGoals.eq(params.getNumGoals()));
        }
        if (params.getMinGoals() != null) {
            conditions.add(numberOfGoals.gt(params.getMinGoals()));
        }
        if (params.getMaxGoals() != null) {
            conditions.add(numberOfGoals.lt(params.getMaxGoals()));
        }

        JPQLQuery<Address> gameAddressSubquery = JPAExpressions.select(game.locatedIn).from(game).join(game.locatedIn, address);
        if (params.getPostalCode() != null) {
            gameAddressSubquery.where(address.postalCode.eq(params.getPostalCode()));
        }
        if (params.getCity() != null) {
            gameAddressSubquery.where(address.city.eq(params.getCity()));
        }
        if (params.getCountry() != null) {
            gameAddressSubquery.where(address.country.eq(params.getCountry()));
        }
        if (params.getStreet() != null) {
            gameAddressSubquery.where(address.street.eq(params.getStreet()));
        }
        conditions.add(game.locatedIn.in(gameAddressSubquery));

        BooleanBuilder predicate = conditions.stream()
                .reduce(BooleanExpression::and)
                .map(BooleanBuilder::new)
                .orElse(new BooleanBuilder());

        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);

        return findAll(predicate, pageable).getContent();
    }
}
