package com.soccernow.ui.soccernowui.dto.tournament;

import com.soccernow.ui.soccernowui.dto.games.GameInfoDTO;
import com.soccernow.ui.soccernowui.util.TournamentStatusEnum;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


public class TournamentDTO implements Serializable {
    @Pattern(regexp = "^\\p{L}+( \\p{L}+)*$")
    @Length(max = 100)
    private String name;
    private TournamentStatusEnum status = TournamentStatusEnum.OPEN;
    private UUID id;
    private Boolean isFinished = false;
    private List<GameInfoDTO> games = new ArrayList<>();

    public TournamentDTO() {
    }

    public TournamentDTO(
            String name,
            TournamentStatusEnum status, UUID id, Boolean isFinished, List<GameInfoDTO> games) {
        this.id = id;
        this.isFinished = isFinished;
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

    public Boolean getIsFinished() {
        return isFinished;
    }

    public TournamentDTO setIsFinished(Boolean isFinished) {
        this.isFinished = isFinished;
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
                Objects.equals(this.isFinished, entity.isFinished) &&
                Objects.equals(this.games, entity.games);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, isFinished, games);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "isFinished = " + isFinished + ", " +
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
}
