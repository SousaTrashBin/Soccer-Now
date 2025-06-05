package pt.ul.fc.css.soccernow.domain.entities.tournament.point;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import pt.ul.fc.css.soccernow.domain.entities.Team;
import pt.ul.fc.css.soccernow.domain.entities.game.GameStats;
import pt.ul.fc.css.soccernow.domain.entities.game.GameTeam;
import pt.ul.fc.css.soccernow.domain.entities.tournament.Tournament;

import java.util.ArrayList;
import java.util.List;

@Entity
public class PointTournament extends Tournament {
    public static final int MINIMUM_TEAM_SIZE = 8;
    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    @OrderBy("currentPoints DESC")
    private List<TeamPoints> teamPoints = new ArrayList<>();

    public List<TeamPoints> getTeamPoints() {
        return teamPoints;
    }

    public void setTeamPoints(List<TeamPoints> teamPoints) {
        this.teamPoints = teamPoints;
    }

    public boolean hasTeam(Team savedTeam) {
        return this.teamPoints.stream()
                .anyMatch(teamPoints -> teamPoints.getTeam().equals(savedTeam));
    }

    public void removeTeam(Team savedTeam) {
        teamPoints.removeIf(teamPoints -> teamPoints.getTeam().getId().equals(savedTeam.getId()));
        savedTeam.removeTournament(this);
    }

    public void addTeam(Team savedTeam) {
        TeamPoints teamPoints = new TeamPoints();
        teamPoints.setTeam(savedTeam);
        this.teamPoints.add(teamPoints);
        savedTeam.addTournament(this);
    }

    public boolean hasMinimumTeams() {
        return teamPoints.size() >= MINIMUM_TEAM_SIZE;
    }

    @Override
    public void updateScore(GameTeam gameTeamOne, GameTeam gameTeamTwo, GameStats gameStats) {
        Integer teamOneGoals = gameStats.getTeamOneGoals();
        Integer teamTwoGoals = gameStats.getTeamTwoGoals();
        teamPoints.forEach(teamPoints -> teamPoints.updatePoints(gameTeamOne, gameTeamTwo, teamOneGoals, teamTwoGoals));
    }
}
