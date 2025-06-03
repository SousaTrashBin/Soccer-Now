package pt.ul.fc.css.soccernow.domain.dto.games;

import com.fasterxml.jackson.annotation.JsonIgnore;
import pt.ul.fc.css.soccernow.domain.dto.tournament.TournamentInfoDTO;
import pt.ul.fc.css.soccernow.domain.dto.user.RefereeInfoDTO;
import pt.ul.fc.css.soccernow.util.TimeOfDay;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * DTO for {@link pt.ul.fc.css.soccernow.domain.entities.game.Game}
 */
public class GameDTO implements Serializable {
    private GameTeamDTO gameTeamTwo;
    private UUID id;
    private GameTeamDTO gameTeamOne;
    private GameStatsDTO gameStats;
    private RefereeInfoDTO primaryReferee;
    private Set<RefereeInfoDTO> secondaryReferees = new HashSet<>();
    private AddressDTO locatedIn;
    private LocalDateTime happensIn;
    private Boolean isClosed = false;
    private TournamentInfoDTO tournament;

    public GameDTO() {
    }

    public GameDTO(
            GameTeamDTO gameTeamTwo, UUID id, GameTeamDTO gameTeamOne, GameStatsDTO gameStats, RefereeInfoDTO primaryReferee, Set<RefereeInfoDTO> secondaryReferees, AddressDTO locatedIn, LocalDateTime happensIn, Boolean isClosed, TournamentInfoDTO tournament) {
        this.id = id;
        this.gameTeamOne = gameTeamOne;
        this.gameStats = gameStats;
        this.primaryReferee = primaryReferee;
        this.secondaryReferees = secondaryReferees;
        this.locatedIn = locatedIn;
        this.happensIn = happensIn;
        this.isClosed = isClosed;
        this.tournament = tournament;
        this.gameTeamTwo = gameTeamTwo;
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

    public Boolean getIsClosed() {
        return isClosed;
    }

    public GameDTO setIsClosed(Boolean isClosed) {
        this.isClosed = isClosed;
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
                Objects.equals(this.isClosed, entity.isClosed) &&
                Objects.equals(this.tournament, entity.tournament);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, gameTeamOne, gameStats, primaryReferee, secondaryReferees, locatedIn, happensIn, isClosed, tournament);
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
                "isClosed = " + isClosed + ", " +
                "tournament = " + tournament + ")";
    }

    public GameTeamDTO getGameTeamTwo() {
        return gameTeamTwo;
    }

    public GameDTO setGameTeamTwo(GameTeamDTO gameTeamTwo) {
        this.gameTeamTwo = gameTeamTwo;
        return this;
    }

    @JsonIgnore
    public TimeOfDay getTimeOfDay() {
        return TimeOfDay.fromDateTime(happensIn);
    }
}
