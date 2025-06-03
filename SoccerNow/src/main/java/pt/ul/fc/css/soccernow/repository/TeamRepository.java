package pt.ul.fc.css.soccernow.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import pt.ul.fc.css.soccernow.domain.entities.QTeam;
import pt.ul.fc.css.soccernow.domain.entities.Team;
import pt.ul.fc.css.soccernow.domain.entities.game.QGame;
import pt.ul.fc.css.soccernow.domain.entities.tournament.QPlacement;
import pt.ul.fc.css.soccernow.domain.entities.user.QPlayer;
import pt.ul.fc.css.soccernow.util.PlacementEnum;
import pt.ul.fc.css.soccernow.util.TeamSearchParams;

import java.util.ArrayList;
import java.util.List;

import static pt.ul.fc.css.soccernow.domain.entities.QTeam.team;
import static pt.ul.fc.css.soccernow.util.PlacementEnum.*;

public interface TeamRepository extends SoftDeletedRepository<Team> {
    Team findByName(String name);

    default List<Team> findAllNotDeleted(TeamSearchParams params) {
        List<BooleanExpression> conditions = new ArrayList<>();

        conditions.add(team.deletedAt.isNull());
        if (params.getName() != null && !params.getName().isBlank()) {
            conditions.add(team.name.containsIgnoreCase(params.getName()));
        }

        QTeam teamSub = new QTeam("teamSub");
        QPlayer playerSub = new QPlayer("playerSub");

        JPQLQuery<Long> getPlayerCountSubquery = JPAExpressions
                .select(playerSub.count())
                .from(teamSub)
                .join(teamSub.players, playerSub)
                .where(teamSub.eq(team));
        if (params.getNumPlayers() != null) {
            conditions.add(getPlayerCountSubquery.eq(params.getNumPlayers().longValue()));
        }
        if (params.getMinPlayers() != null) {
            conditions.add(getPlayerCountSubquery.gt(params.getMinPlayers().longValue()));
        }
        if (params.getMaxPlayers() != null) {
            conditions.add(getPlayerCountSubquery.lt(params.getMaxPlayers().longValue()));
        }

        QTeam teamDrawsSub = new QTeam("teamDrawsSub");
        QGame gameDrawsSub = new QGame("gameDrawsSub");

        JPQLQuery<Long> getNumberOfDrawsSubquery = JPAExpressions
                .select(gameDrawsSub.count())
                .from(teamDrawsSub)
                .join(teamDrawsSub.games, gameDrawsSub)
                .where(
                        teamDrawsSub.eq(team)
                                .and(gameDrawsSub.gameStats.teamOneGoals.eq(gameDrawsSub.gameStats.teamTwoGoals))
                );
        if (params.getNumDraws() != null) {
            conditions.add(getNumberOfDrawsSubquery.eq(params.getNumDraws().longValue()));
        }
        if (params.getMinDraws() != null) {
            conditions.add(getNumberOfDrawsSubquery.gt(params.getMinDraws().longValue()));
        }
        if (params.getMaxDraws() != null) {
            conditions.add(getNumberOfDrawsSubquery.lt(params.getMaxDraws().longValue()));
        }

        QTeam teamWinsSub = new QTeam("teamWinsSub");
        QGame gameWinsSub = new QGame("gameWinsSub");

        JPQLQuery<Long> getNumberOfVictoriesSubquery = JPAExpressions
                .select(gameWinsSub.count())
                .from(teamWinsSub)
                .join(teamWinsSub.games, gameWinsSub)
                .where(teamWinsSub.eq(team).and(
                        gameWinsSub.gameTeamOne.team.eq(teamWinsSub)
                                .and(gameWinsSub.gameStats.teamOneGoals.gt(gameWinsSub.gameStats.teamTwoGoals))
                                .or(gameWinsSub.gameTeamTwo.team.eq(teamWinsSub)
                                        .and(gameWinsSub.gameStats.teamTwoGoals.gt(gameWinsSub.gameStats.teamOneGoals)))
                ));

        if (params.getNumVictories() != null) {
            conditions.add(getNumberOfVictoriesSubquery.eq(params.getNumVictories().longValue()));
        }
        if (params.getMinVictories() != null) {
            conditions.add(getNumberOfVictoriesSubquery.gt(params.getMinVictories().longValue()));
        }
        if (params.getMaxVictories() != null) {
            conditions.add(getNumberOfVictoriesSubquery.lt(params.getMaxVictories().longValue()));
        }

        QTeam teamLossesSub = new QTeam("teamLossesSub");
        QGame gameLossesSub = new QGame("gameLossesSub");

        JPQLQuery<Long> getNumberOfLossesSubquery = JPAExpressions
                .select(gameLossesSub.count())
                .from(teamLossesSub)
                .join(teamLossesSub.games, gameLossesSub)
                .where(teamLossesSub.eq(team).and(
                        gameLossesSub.gameTeamOne.team.eq(teamLossesSub)
                                .and(gameLossesSub.gameStats.teamOneGoals.lt(gameLossesSub.gameStats.teamTwoGoals))
                                .or(gameLossesSub.gameTeamTwo.team.eq(teamLossesSub)
                                        .and(gameLossesSub.gameStats.teamTwoGoals.lt(gameLossesSub.gameStats.teamOneGoals)))
                ));
        if (params.getNumLosses() != null) {
            conditions.add(getNumberOfLossesSubquery.eq(params.getNumLosses().longValue()));
        }
        if (params.getMinLosses() != null) {
            conditions.add(getNumberOfLossesSubquery.gt(params.getMinLosses().longValue()));
        }
        if (params.getMaxLosses() != null) {
            conditions.add(getNumberOfLossesSubquery.lt(params.getMaxLosses().longValue()));
        }

        QTeam teamPosSub = new QTeam("teamPosSub");
        QPlayer playerPosSub = new QPlayer("playerPosSub");

        BooleanExpression missingAnyPosition = params.getMissingPositions().stream()
                .map(pos -> JPAExpressions
                        .select(playerPosSub.count())
                        .from(teamPosSub)
                        .join(teamPosSub.players, playerPosSub)
                        .where(teamPosSub.eq(team).and(playerPosSub.preferredPosition.eq(pos)))
                        .eq(0L))
                .reduce(BooleanExpression::or)
                .orElse(Expressions.TRUE);

        conditions.add(missingAnyPosition);

        QTeam teamAchSub = new QTeam("teamAchSub");
        QPlacement placementSub = new QPlacement("placementSub");

        List<PlacementEnum> achievementPlacements = List.of(FIRST, SECOND, THIRD);
        JPQLQuery<Long> achievementCountSubquery = JPAExpressions
                .select(placementSub.count())
                .from(teamAchSub)
                .join(teamAchSub.placements, placementSub)
                .where(teamAchSub.eq(team).and(placementSub.value.in(achievementPlacements)));

        if (params.getNumAchievements() != null) {
            conditions.add(achievementCountSubquery.eq(params.getNumAchievements().longValue()));
        }
        if (params.getMinAchievements() != null) {
            conditions.add(achievementCountSubquery.gt(params.getMinAchievements().longValue()));
        }
        if (params.getMaxAchievements() != null) {
            conditions.add(achievementCountSubquery.lt(params.getMaxAchievements().longValue()));
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
