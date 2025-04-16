package pt.ul.fc.css.soccernow.domain.entities.tournament;

import jakarta.persistence.*;
import java.util.UUID;
import pt.ul.fc.css.soccernow.util.PlacementEnum;

@Entity
@Table(name = "placements")
public class Placement {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id", nullable = false)
  private UUID id;

  @Enumerated(EnumType.STRING)
  @Column(name = "value", nullable = false)
  private PlacementEnum value = PlacementEnum.PENDING;

  @ManyToOne
  @JoinColumn(name = "tournament_id")
  private Tournament tournament;

  public Tournament getTournament() {
    return tournament;
  }

  public void setTournament(Tournament tournament) {
    this.tournament = tournament;
  }

  public PlacementEnum getPlacementEnum() {
    return value;
  }

  public void setPlacementEnum(PlacementEnum value) {
    this.value = value;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }
}
