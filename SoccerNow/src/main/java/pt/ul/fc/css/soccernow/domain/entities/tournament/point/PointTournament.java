package pt.ul.fc.css.soccernow.domain.entities.tournament.point;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import pt.ul.fc.css.soccernow.domain.entities.tournament.Tournament;

@Entity
public class PointTournament extends Tournament {
  @Size(min = 8)
  @OneToMany(orphanRemoval = true)
  @OrderBy("currentPoints")
  private List<TeamPoints> teamPoints = new ArrayList<>();

  public List<TeamPoints> getTeamPoints() {
    return teamPoints;
  }

  public void setTeamPoints(List<TeamPoints> teamPoints) {
    this.teamPoints = teamPoints;
  }
}
