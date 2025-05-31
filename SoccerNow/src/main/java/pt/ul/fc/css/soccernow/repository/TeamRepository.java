package pt.ul.fc.css.soccernow.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import pt.ul.fc.css.soccernow.domain.entities.Team;
import pt.ul.fc.css.soccernow.util.PlacementEnum;
import pt.ul.fc.css.soccernow.util.TeamSearchParams;

import java.util.ArrayList;
import java.util.List;

import static pt.ul.fc.css.soccernow.domain.entities.QTeam.team;
import static pt.ul.fc.css.soccernow.domain.entities.game.QGame.game;
import static pt.ul.fc.css.soccernow.domain.entities.tournament.QPlacement.placement;
import static pt.ul.fc.css.soccernow.domain.entities.user.QPlayer.player;
import static pt.ul.fc.css.soccernow.util.PlacementEnum.*;

public interface TeamRepository extends SoftDeletedRepository<Team> {
    Team findByName(String name);

    default List<Team> findAllNotDeleted(TeamSearchParams params) {
        List<BooleanExpression> conditions = new ArrayList<>();

        conditions.add(team.deletedAt.isNull());
        if (params.getName() != null && !params.getName().isBlank()) {
            conditions.add(team.name.containsIgnoreCase(params.getName()));
        }

        JPQLQuery<Long> getPlayerCountSubquery = JPAExpressions
                .select(player.count())
                .from(team)
                .join(team.players, player)
                .where(team.eq(team));

        if (params.getNumPlayers() != null) {
            conditions.add(getPlayerCountSubquery.eq(params.getNumPlayers().longValue()));
        }
        if (params.getMinPlayers() != null) {
            conditions.add(getPlayerCountSubquery.gt(params.getMinPlayers().longValue()));
        }
        if (params.getMaxPlayers() != null) {
            conditions.add(getPlayerCountSubquery.lt(params.getMaxPlayers().longValue()));
        }

        JPQLQuery<Long> getNumberOfDrawsSubquery = JPAExpressions
                .select(game.count())
                .from(team)
                .join(team.games, game)
                .where(game.gameStats.teamOneGoals.eq(game.gameStats.teamTwoGoals));
        if (params.getNumDraws() != null) {
            conditions.add(getNumberOfDrawsSubquery.eq(params.getNumDraws().longValue()));
        }
        if (params.getMinDraws() != null) {
            conditions.add(getNumberOfDrawsSubquery.gt(params.getMinDraws().longValue()));
        }
        if (params.getMaxDraws() != null) {
            conditions.add(getNumberOfDrawsSubquery.lt(params.getMaxDraws().longValue()));
        }

        JPQLQuery<Long> getNumberOfVictoriesSubquery = JPAExpressions
                .select(game.count())
                .from(team)
                .join(team.games, game)
                .where(
                        (game.gameTeamOne.team.eq(team).and(game.gameStats.teamOneGoals.gt(game.gameStats.teamTwoGoals)))
                                .or(game.gameTeamTwo.team.eq(team).and(game.gameStats.teamTwoGoals.gt(game.gameStats.teamOneGoals)))
                );
        if (params.getNumVictories() != null) {
            conditions.add(getNumberOfVictoriesSubquery.eq(params.getNumVictories().longValue()));
        }
        if (params.getMinVictories() != null) {
            conditions.add(getNumberOfVictoriesSubquery.gt(params.getMinVictories().longValue()));
        }
        if (params.getMaxVictories() != null) {
            conditions.add(getNumberOfVictoriesSubquery.lt(params.getMaxVictories().longValue()));
        }

        JPQLQuery<Long> getNumberOfLossesSubquery = JPAExpressions
                .select(game.count())
                .from(team)
                .join(team.games, game)
                .where(
                        (game.gameTeamOne.team.eq(team).and(game.gameStats.teamOneGoals.lt(game.gameStats.teamTwoGoals)))
                                .or(game.gameTeamTwo.team.eq(team).and(game.gameStats.teamTwoGoals.lt(game.gameStats.teamOneGoals)))
                );
        if (params.getNumLosses() != null) {
            conditions.add(getNumberOfLossesSubquery.eq(params.getNumLosses().longValue()));
        }
        if (params.getMinLosses() != null) {
            conditions.add(getNumberOfLossesSubquery.gt(params.getMinLosses().longValue()));
        }
        if (params.getMaxLosses() != null) {
            conditions.add(getNumberOfLossesSubquery.lt(params.getMaxLosses().longValue()));
        }

        BooleanExpression missingAnyPosition = params.getMissingPositions().stream()
                .map(futsalPositionEnum -> {
                    JPQLQuery<Long> positionCountSubquery = JPAExpressions
                            .select(player.count())
                            .from(team)
                            .join(team.players, player)
                            .where(player.preferredPosition.eq(futsalPositionEnum));
                    return positionCountSubquery.eq(0L);
                })
                .reduce(BooleanExpression::or)
                .orElse(Expressions.TRUE);
        conditions.add(missingAnyPosition);

        List<PlacementEnum> achievementPlacements = List.of(FIRST, SECOND, THIRD);
        JPQLQuery<Long> achievementCountSubquery = JPAExpressions
                .select(placement.count())
                .from(team)
                .join(team.placements, placement)
                .where(placement.value.in(achievementPlacements));

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
