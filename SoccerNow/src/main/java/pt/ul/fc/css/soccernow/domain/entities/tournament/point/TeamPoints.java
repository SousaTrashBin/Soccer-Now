package pt.ul.fc.css.soccernow.domain.entities.tournament.point;

import jakarta.persistence.*;
import pt.ul.fc.css.soccernow.domain.entities.Team;
import pt.ul.fc.css.soccernow.domain.entities.game.GameTeam;
import pt.ul.fc.css.soccernow.domain.entities.tournament.Placement;
import pt.ul.fc.css.soccernow.util.PlacementEnum;

import java.util.UUID;

@Entity
@Table(name = "team_points")
public class TeamPoints {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "current_points", nullable = false)
    private Integer currentPoints = 0;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "team_id")
    private Team team;

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Integer getCurrentPoints() {
        return currentPoints;
    }

    public void setCurrentPoints(Integer currentPoints) {
        this.currentPoints = currentPoints;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void updatePoints(GameTeam gameTeamOne, GameTeam gameTeamTwo, Integer teamOneGoals, Integer teamTwoGoals) {
        if (this.team.equals(gameTeamOne.getTeam())) {
            this.currentPoints += calculatePoints(teamOneGoals, teamTwoGoals);
        } else if (this.team.equals(gameTeamTwo.getTeam())) {
            this.currentPoints += calculatePoints(teamTwoGoals, teamOneGoals);
        }
    }

    private int calculatePoints(int g1, int g2) {
        if (g1 > g2) return 3;
        if (g1 == g2) return 1;
        return 0;
    }

    public Placement updatePlacementForTournament(PointTournament tournament, int rank) {
        PlacementEnum placement;
        switch (rank) {
            case 0 -> placement = PlacementEnum.FIRST;
            case 1 -> placement = PlacementEnum.SECOND;
            case 2 -> placement = PlacementEnum.THIRD;
            default -> placement = PlacementEnum.PARTICIPATED;
        }

        Placement teamPlacement = this.getTeam().getPlacementForTournament(tournament);
        if (teamPlacement != null) {
            teamPlacement.setValue(placement);
        }
        return teamPlacement;
    }
}
