package com.soccernow.ui.soccernowui.dto.games;

import com.soccernow.ui.soccernowui.dto.tournament.TournamentInfoDTO;
import com.soccernow.ui.soccernowui.dto.user.RefereeInfoDTO;
import com.soccernow.ui.soccernowui.util.GameStatusEnum;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class GameDTO implements Serializable {

    @NotNull(message = "Team Two is required.")
    private GameTeamDTO gameTeamTwo;
    private UUID id;
    @NotNull(message = "Team One is required.")
    private GameTeamDTO gameTeamOne;
    private GameStatsDTO gameStats;
    @NotNull(message = "Game must have a Primary Referee.")
    private RefereeInfoDTO primaryReferee;
    private Set<RefereeInfoDTO> secondaryReferees = new HashSet<>();
    @NotNull(message = "Address is required and requires the field Street.")
    private AddressDTO locatedIn;
    @NotNull(message = "Date is required.")
    private LocalDateTime happensIn;
    private TournamentInfoDTO tournament;
    private GameStatusEnum status;

    public GameDTO() {
    }

    public GameDTO(
            GameTeamDTO gameTeamTwo, UUID id, GameTeamDTO gameTeamOne, GameStatsDTO gameStats, RefereeInfoDTO primaryReferee, Set<RefereeInfoDTO> secondaryReferees, AddressDTO locatedIn, LocalDateTime happensIn, TournamentInfoDTO tournament, GameStatusEnum status) {
        this.id = id;
        this.gameTeamOne = gameTeamOne;
        this.gameStats = gameStats;
        this.primaryReferee = primaryReferee;
        this.secondaryReferees = secondaryReferees;
        this.locatedIn = locatedIn;
        this.happensIn = happensIn;

        this.tournament = tournament;
        this.gameTeamTwo = gameTeamTwo;
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public GameDTO setId(UUID id) {
        this.id = id;
        return this;
    }

    public GameTeamDTO getGameTeamOne() {
        return gameTeamOne;
    }

    public GameDTO setGameTeamOne(GameTeamDTO gameTeamOne) {
        this.gameTeamOne = gameTeamOne;
        return this;
    }

    public GameStatsDTO getGameStats() {
        return gameStats;
    }

    public GameDTO setGameStats(GameStatsDTO gameStats) {
        this.gameStats = gameStats;
        return this;
    }

    public RefereeInfoDTO getPrimaryReferee() {
        return primaryReferee;
    }

    public GameDTO setPrimaryReferee(RefereeInfoDTO primaryReferee) {
        this.primaryReferee = primaryReferee;
        return this;
    }

    public Set<RefereeInfoDTO> getSecondaryReferees() {
        return secondaryReferees;
    }

    public GameDTO setSecondaryReferees(Set<RefereeInfoDTO> secondaryReferees) {
        this.secondaryReferees = secondaryReferees;
        return this;
    }

    public AddressDTO getLocatedIn() {
        return locatedIn;
    }

    public GameDTO setLocatedIn(AddressDTO locatedIn) {
        this.locatedIn = locatedIn;
        return this;
    }

    public LocalDateTime getHappensIn() {
        return happensIn;
    }

    public GameDTO setHappensIn(LocalDateTime happensIn) {
        this.happensIn = happensIn;
        return this;
    }

    public TournamentInfoDTO getTournament() {
        return tournament;
    }

    public GameDTO setTournament(TournamentInfoDTO tournament) {
        this.tournament = tournament;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameDTO entity = (GameDTO) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.gameTeamOne, entity.gameTeamOne) &&
                Objects.equals(this.gameStats, entity.gameStats) &&
                Objects.equals(this.primaryReferee, entity.primaryReferee) &&
                Objects.equals(this.secondaryReferees, entity.secondaryReferees) &&
                Objects.equals(this.locatedIn, entity.locatedIn) &&
                Objects.equals(this.happensIn, entity.happensIn) &&
                Objects.equals(this.status, entity.status) &&
                Objects.equals(this.tournament, entity.tournament);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, gameTeamOne, gameStats, primaryReferee, secondaryReferees, locatedIn, happensIn, status, tournament);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "gameTeamOne = " + gameTeamOne + ", " +
                "gameStats = " + gameStats + ", " +
                "primaryReferee = " + primaryReferee + ", " +
                "secondaryReferees = " + secondaryReferees + ", " +
                "locatedIn = " + locatedIn + ", " +
                "happensIn = " + happensIn + ", " +
                "status = " + status + ", " +
                "tournament = " + tournament + ")";
    }

    public GameTeamDTO getGameTeamTwo() {
        return gameTeamTwo;
    }

    public GameDTO setGameTeamTwo(GameTeamDTO gameTeamTwo) {
        this.gameTeamTwo = gameTeamTwo;
        return this;
    }

    public GameStatusEnum getStatus() {
        return status;
    }

    public void setStatus(GameStatusEnum status) {
        this.status = status;
    }
}
