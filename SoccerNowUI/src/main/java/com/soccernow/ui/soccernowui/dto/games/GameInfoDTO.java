package com.soccernow.ui.soccernowui.dto.games;

import jakarta.validation.constraints.NotNull;
import pt.ul.fc.css.soccernow.domain.dto.tournament.TournamentInfoDTO;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * DTO for {@link pt.ul.fc.css.soccernow.domain.entities.game.Game}
 */
public class GameInfoDTO implements Serializable {
    private UUID id;
    private AddressDTO locatedIn;
    private LocalDateTime happensIn;
    @NotNull
    private Boolean isClosed = false;
    private TournamentInfoDTO tournament;

    public GameInfoDTO() {
    }

    public GameInfoDTO(UUID id, AddressDTO locatedIn, LocalDateTime happensIn, Boolean isClosed, TournamentInfoDTO tournament) {
        this.id = id;
        this.locatedIn = locatedIn;
        this.happensIn = happensIn;
        this.isClosed = isClosed;
        this.tournament = tournament;
    }

    public UUID getId() {
        return id;
    }

    public GameInfoDTO setId(UUID id) {
        this.id = id;
        return this;
    }

    public AddressDTO getLocatedIn() {
        return locatedIn;
    }

    public GameInfoDTO setLocatedIn(AddressDTO locatedIn) {
        this.locatedIn = locatedIn;
        return this;
    }

    public LocalDateTime getHappensIn() {
        return happensIn;
    }

    public GameInfoDTO setHappensIn(LocalDateTime happensIn) {
        this.happensIn = happensIn;
        return this;
    }

    public Boolean getIsClosed() {
        return isClosed;
    }

    public GameInfoDTO setIsClosed(Boolean isClosed) {
        this.isClosed = isClosed;
        return this;
    }

    public TournamentInfoDTO getTournament() {
        return tournament;
    }

    public GameInfoDTO setTournament(TournamentInfoDTO tournament) {
        this.tournament = tournament;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameInfoDTO entity = (GameInfoDTO) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.locatedIn, entity.locatedIn) &&
                Objects.equals(this.happensIn, entity.happensIn) &&
                Objects.equals(this.isClosed, entity.isClosed) &&
                Objects.equals(this.tournament, entity.tournament);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, locatedIn, happensIn, isClosed, tournament);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "locatedIn = " + locatedIn + ", " +
                "happensIn = " + happensIn + ", " +
                "isClosed = " + isClosed + ", " +
                "tournament = " + tournament + ")";
    }
}
