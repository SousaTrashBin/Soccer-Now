package pt.ul.fc.css.soccernow.domain.dto.tournament;

import jakarta.validation.constraints.NotNull;
import pt.ul.fc.css.soccernow.util.PlacementEnum;

import java.io.Serializable;
import java.util.Objects;

/**
 * DTO for {@link pt.ul.fc.css.soccernow.domain.entities.tournament.Placement}
 */
public class PlacementInfoDTO implements Serializable {
    private PlacementEnum value = PlacementEnum.PENDING;
    @NotNull
    private TournamentInfoDTO tournament;

    public PlacementInfoDTO() {
    }

    public PlacementInfoDTO(PlacementEnum value, TournamentInfoDTO tournament) {
        this.value = value;
        this.tournament = tournament;
    }

    public PlacementEnum getValue() {
        return value;
    }

    public PlacementInfoDTO setValue(PlacementEnum value) {
        this.value = value;
        return this;
    }

    public TournamentInfoDTO getTournament() {
        return tournament;
    }

    public PlacementInfoDTO setTournament(TournamentInfoDTO tournament) {
        this.tournament = tournament;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlacementInfoDTO entity = (PlacementInfoDTO) o;
        return Objects.equals(this.value, entity.value) &&
                Objects.equals(this.tournament, entity.tournament);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, tournament);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "value = " + value + ", " +
                "tournament = " + tournament + ")";
    }
}
