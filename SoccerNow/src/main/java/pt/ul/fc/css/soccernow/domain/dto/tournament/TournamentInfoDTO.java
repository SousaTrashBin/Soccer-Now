package pt.ul.fc.css.soccernow.domain.dto.tournament;

import pt.ul.fc.css.soccernow.util.TournamentStatusEnum;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * DTO for {@link pt.ul.fc.css.soccernow.domain.entities.tournament.Tournament}
 */
public class TournamentInfoDTO implements Serializable {
    private String name;
    private TournamentStatusEnum status = TournamentStatusEnum.OPEN;
    private UUID id;

    public TournamentInfoDTO() {
    }

    public TournamentInfoDTO(
            String name,
            TournamentStatusEnum status
            , UUID id) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public TournamentInfoDTO setId(UUID id) {
        this.id = id;
        return this;
    }

    @Override
    public String toString() {
        return "TournamentInfoDTO(" +
                "name = '" + name + '\'' +
                ", status = " + status +
                ", id = " + id +
                ')';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TournamentInfoDTO entity = (TournamentInfoDTO) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.status, entity.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getStatus(), getId());
    }

    public String getName() {
        return name;
    }

    public TournamentInfoDTO setName(String name) {
        this.name = name;
        return this;
    }

    public TournamentStatusEnum getStatus() {
        return status;
    }

    public TournamentInfoDTO setStatus(TournamentStatusEnum status) {
        this.status = status;
        return this;
    }
}
