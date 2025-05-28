package pt.ul.fc.css.soccernow.domain.dto.tournament;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * DTO for {@link pt.ul.fc.css.soccernow.domain.entities.tournament.Tournament}
 */
public class TournamentInfoDTO implements Serializable {
    private UUID id;
    @NotNull
    private Boolean isFinished = false;

    public TournamentInfoDTO() {
    }

    public TournamentInfoDTO(UUID id, Boolean isFinished) {
        this.id = id;
        this.isFinished = isFinished;
    }

    public UUID getId() {
        return id;
    }

    public TournamentInfoDTO setId(UUID id) {
        this.id = id;
        return this;
    }

    public Boolean getIsFinished() {
        return isFinished;
    }

    public TournamentInfoDTO setIsFinished(Boolean isFinished) {
        this.isFinished = isFinished;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TournamentInfoDTO entity = (TournamentInfoDTO) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.isFinished, entity.isFinished);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, isFinished);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "isFinished = " + isFinished + ")";
    }
}
