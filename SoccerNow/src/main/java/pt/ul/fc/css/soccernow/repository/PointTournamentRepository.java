package pt.ul.fc.css.soccernow.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import pt.ul.fc.css.soccernow.domain.entities.tournament.point.PointTournament;
import pt.ul.fc.css.soccernow.util.GameStatusEnum;
import pt.ul.fc.css.soccernow.util.TournamentSearchParams;

import java.util.ArrayList;
import java.util.List;

import static pt.ul.fc.css.soccernow.domain.entities.game.QGame.game;
import static pt.ul.fc.css.soccernow.domain.entities.tournament.QTournament.tournament;
import static pt.ul.fc.css.soccernow.domain.entities.tournament.point.QPointTournament.pointTournament;

public interface PointTournamentRepository extends SoftDeletedRepository<PointTournament> {
    default List<PointTournament> findAllNotDeleted(TournamentSearchParams params) {
        List<BooleanExpression> conditions = new ArrayList<>();

        conditions.add(pointTournament.deletedAt.isNull());
        if (params.getName() != null && !params.getName().isBlank()) {
            conditions.add(pointTournament.name.containsIgnoreCase(params.getName()));
        }

        params.getTeams().forEach(teamName -> conditions.add(pointTournament.teamPoints.any().team.name.containsIgnoreCase(teamName)));

        JPQLQuery<Long> tournamentGamesOpenedSubquery = JPAExpressions
                .select(game.count())
                .from(tournament)
                .join(tournament.games, game)
                .where(game.status.eq(GameStatusEnum.OPENED));
        if (params.getNumGamesOpened() != null) {
            conditions.add(tournamentGamesOpenedSubquery.eq(params.getNumGamesOpened().longValue()));
        }
        if (params.getMinGamesClosed() != null) {
            conditions.add(tournamentGamesOpenedSubquery.gt(params.getMinGamesClosed().longValue()));
        }
        if (params.getMaxGamesClosed() != null) {
            conditions.add(tournamentGamesOpenedSubquery.lt(params.getMaxGamesClosed().longValue()));
        }

        JPQLQuery<Long> tournamentGamesClosedSubquery = JPAExpressions
                .select(game.count())
                .from(tournament)
                .join(tournament.games, game)
                .where(game.status.eq(GameStatusEnum.CLOSED));
        if (params.getNumGamesClosed() != null) {
            conditions.add(tournamentGamesClosedSubquery.eq(params.getNumGamesClosed().longValue()));
        }
        if (params.getMinGamesClosed() != null) {
            conditions.add(tournamentGamesOpenedSubquery.gt(params.getMinGamesClosed().longValue()));
        }
        if (params.getMaxGamesClosed() != null) {
            conditions.add(tournamentGamesOpenedSubquery.lt(params.getMaxGamesClosed().longValue()));
        }

        BooleanBuilder predicate = conditions.stream()
                .reduce(BooleanExpression::and)
                .map(BooleanBuilder::new)
                .orElse(new BooleanBuilder());

        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);

        return findAll(predicate, pageable).getContent();
    }
}
