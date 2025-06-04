package pt.ul.fc.css.soccernow.domain.entities.tournament;

import jakarta.persistence.*;
import pt.ul.fc.css.soccernow.util.PlacementEnum;

import java.util.UUID;

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

    @Override
    public String toString() {
        return "Placement{" +
                "id=" + id +
                ", value=" + value +
                '}';
    }

    @ManyToOne
    @JoinColumn(name = "tournament_id")
    private Tournament tournament;

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public PlacementEnum getValue() {
        return value;
    }

    public void setValue(PlacementEnum value) {
        this.value = value;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public boolean isFinished() {
        return value != PlacementEnum.PENDING;
    }
}
