package pt.ul.fc.css.soccernow.domain.entities.game;

import jakarta.persistence.*;
import pt.ul.fc.css.soccernow.domain.entities.Address;
import pt.ul.fc.css.soccernow.domain.entities.tournament.Tournament;
import pt.ul.fc.css.soccernow.domain.entities.user.Player;
import pt.ul.fc.css.soccernow.domain.entities.user.Referee;
import pt.ul.fc.css.soccernow.util.SoftDeleteEntity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "games")
public class Game extends SoftDeleteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @OneToOne(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true, optional = false)
    private GameTeam gameTeamOne;

    @OneToOne(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true, optional = false)
    private GameTeam gameTeamTwo;

    @OneToOne(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "game_stats_id")
    private GameStats gameStats;

    @ManyToOne(optional = false)
    @JoinColumn(name = "primary_referee_id", nullable = false)
    private Referee primaryReferee;

    @ManyToMany
    @JoinTable(
            name = "game_secondaryReferees",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "secondaryReferees_id"))
    private Set<Referee> secondaryReferees = new HashSet<>();

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "located_in_id", nullable = false)
    private Address locatedIn;

    @Column(name = "happens_in", nullable = false)
    private LocalDateTime happensIn;

    @Column(name = "is_closed", nullable = false)
    private Boolean isClosed = false;

    @ManyToOne
    @JoinColumn(name = "tournament_id")
    private Tournament tournament;

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public Boolean getIsClosed() {
        return isClosed;
    }

    public void setIsClosed(Boolean isClosed) {
        this.isClosed = isClosed;
    }

    public LocalDateTime getHappensIn() {
        return happensIn;
    }

    public void setHappensIn(LocalDateTime happensIn) {
        this.happensIn = happensIn;
    }

    public Address getLocatedIn() {
        return locatedIn;
    }

    public void setLocatedIn(Address locatedIn) {
        this.locatedIn = locatedIn;
    }

    public Set<Referee> getSecondaryReferees() {
        return secondaryReferees;
    }

    public void setSecondaryReferees(Set<Referee> secondaryReferees) {
        this.secondaryReferees = secondaryReferees;
    }

    public Referee getPrimaryReferee() {
        return primaryReferee;
    }

    public void setPrimaryReferee(Referee primaryReferee) {
        this.primaryReferee = primaryReferee;
    }

    public GameStats getGameStats() {
        return gameStats;
    }

    public void setGameStats(GameStats gameStats) {
        this.gameStats = gameStats;
    }

    public GameTeam getGameTeamTwo() {
        return gameTeamTwo;
    }

    public void setGameTeamTwo(GameTeam gameTeamTwo) {
        this.gameTeamTwo = gameTeamTwo;
    }

    public GameTeam getGameTeamOne() {
        return gameTeamOne;
    }

    public void setGameTeamOne(GameTeam gameTeamOne) {
        this.gameTeamOne = gameTeamOne;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void close() {
        this.isClosed = true;
    }

    public Set<Player> getPlayers() {
        Set<Player> allPlayers = gameTeamOne.getPlayers();
        allPlayers.addAll(getGameTeamTwo().getPlayers());
        return allPlayers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Game game = (Game) o;
        return Objects.equals(getId(), game.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
