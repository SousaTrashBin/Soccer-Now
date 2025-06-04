package pt.ul.fc.css.soccernow.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import pt.ul.fc.css.soccernow.domain.entities.game.QGame;
import pt.ul.fc.css.soccernow.domain.entities.tournament.point.PointTournament;
import pt.ul.fc.css.soccernow.domain.entities.tournament.point.QPointTournament;
import pt.ul.fc.css.soccernow.util.GameStatusEnum;
import pt.ul.fc.css.soccernow.util.TournamentSearchParams;

import java.util.ArrayList;
import java.util.List;

import static pt.ul.fc.css.soccernow.domain.entities.tournament.point.QPointTournament.pointTournament;

public interface PointTournamentRepository extends SoftDeletedRepository<PointTournament> {
    default List<PointTournament> findAllNotDeleted(TournamentSearchParams params) {
        List<BooleanExpression> conditions = new ArrayList<>();

        conditions.add(pointTournament.deletedAt.isNull());

        if (params.getName() != null && !params.getName().isBlank()) {
            conditions.add(pointTournament.name.containsIgnoreCase(params.getName()));
        }

        params.getTeams().forEach(teamName ->
                conditions.add(pointTournament.teamPoints.any().team.name.containsIgnoreCase(teamName))
        );

        QPointTournament tournamentOpenedSub = new QPointTournament("tournamentOpenedSub");
        QGame gameOpenedSub = new QGame("gameOpenedSub");

        JPQLQuery<Long> tournamentGamesOpenedSubquery = JPAExpressions
                .select(gameOpenedSub.count())
                .from(tournamentOpenedSub)
                .join(tournamentOpenedSub.games, gameOpenedSub)
                .where(
                        tournamentOpenedSub.eq(pointTournament)
                                .and(gameOpenedSub.status.eq(GameStatusEnum.OPENED))
                );

        if (params.getNumGamesOpened() != null) {
            conditions.add(tournamentGamesOpenedSubquery.eq(params.getNumGamesOpened().longValue()));
        }
        if (params.getMinGamesOpened() != null) {
            conditions.add(tournamentGamesOpenedSubquery.gt(params.getMinGamesOpened().longValue()));
        }
        if (params.getMaxGamesOpened() != null) {
            conditions.add(tournamentGamesOpenedSubquery.lt(params.getMaxGamesOpened().longValue()));
        }

        QPointTournament tournamentClosedSub = new QPointTournament("tournamentClosedSub");
        QGame gameClosedSub = new QGame("gameClosedSub");

        JPQLQuery<Long> tournamentGamesClosedSubquery = JPAExpressions
                .select(gameClosedSub.count())
                .from(tournamentClosedSub)
                .join(tournamentClosedSub.games, gameClosedSub)
                .where(
                        tournamentClosedSub.eq(pointTournament)
                                .and(gameClosedSub.status.eq(GameStatusEnum.CLOSED))
                );

        if (params.getNumGamesClosed() != null) {
            conditions.add(tournamentGamesClosedSubquery.eq(params.getNumGamesClosed().longValue()));
        }
        if (params.getMinGamesClosed() != null) {
            conditions.add(tournamentGamesClosedSubquery.gt(params.getMinGamesClosed().longValue()));
        }
        if (params.getMaxGamesClosed() != null) {
            conditions.add(tournamentGamesClosedSubquery.lt(params.getMaxGamesClosed().longValue()));
        }

        BooleanBuilder predicate = conditions.stream()
                .reduce(BooleanExpression::and)
                .map(BooleanBuilder::new)
                .orElse(new BooleanBuilder());

        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
        return findAll(predicate, pageable).getContent();
    }
}
