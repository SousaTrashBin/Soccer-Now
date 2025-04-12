package pt.ul.fc.css.soccernow.domain.entities.tournament.point;

import jakarta.persistence.*;
import java.util.UUID;
import pt.ul.fc.css.soccernow.domain.entities.Team;

@Entity
@Table(name = "team_points")
public class TeamPoints {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id", nullable = false)
  private UUID id;

  @Column(name = "current_points", nullable = false)
  private Integer currentPoints;

  @ManyToOne
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
}
