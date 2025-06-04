package pt.ul.fc.css.soccernow.domain.dto.tournament;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;
import pt.ul.fc.css.soccernow.domain.dto.games.GameInfoDTO;
import pt.ul.fc.css.soccernow.domain.entities.tournament.Tournament;
import pt.ul.fc.css.soccernow.util.TournamentStatusEnum;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * DTO for {@link Tournament}
 */
public class TournamentDTO implements Serializable {
    @Schema(example = "World Cup")
    @Pattern(regexp = "^\\p{L}+( \\p{L}+)*$")
    @Length(max = 100)
    private String name;
    private TournamentStatusEnum status = TournamentStatusEnum.OPEN;
    private UUID id;
    private List<GameInfoDTO> games = new ArrayList<>();

    public TournamentDTO() {
    }

    public TournamentDTO(
            String name,
            TournamentStatusEnum status, UUID id, Boolean isFinished, List<GameInfoDTO> games) {
        this.id = id;
        this.games = games;
        this.name = name;
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public TournamentDTO setId(UUID id) {
        this.id = id;
        return this;
    }

    public List<GameInfoDTO> getGames() {
        return games;
    }

    public TournamentDTO setGames(List<GameInfoDTO> games) {
        this.games = games;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TournamentDTO entity = (TournamentDTO) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.games, entity.games);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status, games);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "status = " + status + ", " +
                "games = " + games + ")";
    }

    public String getName() {
        return name;
    }

    public TournamentDTO setName(String name) {
        this.name = name;
        return this;
    }

    public TournamentStatusEnum getStatus() {
        return status;
    }

    public TournamentDTO setStatus(TournamentStatusEnum status) {
        this.status = status;
        return this;
    }

    @JsonIgnore
    public Integer getNumberOfClosedGames() {
        return (int)games.stream()
                         .filter(GameInfoDTO::getIsClosed)
                         .count();
    }

    @JsonIgnore
    public Integer getNumberOfOpenedGames() {
        return (int)games.stream()
                         .filter(g -> !g.getIsClosed())
                         .count();
    }
}
