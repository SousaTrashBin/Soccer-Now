package pt.ul.fc.css.soccernow.domain.entities;

import jakarta.persistence.*;
import pt.ul.fc.css.soccernow.domain.entities.game.GameTeam;
import pt.ul.fc.css.soccernow.domain.entities.tournament.Placement;
import pt.ul.fc.css.soccernow.domain.entities.user.Player;
import pt.ul.fc.css.soccernow.util.SoftDeleteEntity;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
@Table(name = "teams")
public class Team extends SoftDeleteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToMany
    @JoinTable(
            name = "team_players",
            joinColumns = @JoinColumn(name = "team_id"),
            inverseJoinColumns = @JoinColumn(name = "player_id"))
    @OrderBy("name")
    private Set<Player> players = new LinkedHashSet<>();

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "team_id")
    private Set<Placement> placements = new LinkedHashSet<>();

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "team", orphanRemoval = true)
    private List<GameTeam> gameTeams = new ArrayList<>();

    public List<GameTeam> getGameTeams() {
        return gameTeams;
    }

    public void setGameTeams(List<GameTeam> gameTeams) {
        this.gameTeams = gameTeams;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Placement> getPlacements() {
        return placements;
    }

    public void setPlacements(Set<Placement> placements) {
        this.placements = new LinkedHashSet<>(placements);
    }

    public Set<Player> getPlayers() {
        return players;
    }

    public void setPlayers(Set<Player> players) {
        this.players = new LinkedHashSet<>(players);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public boolean hasPlayer(Player player) {
        return players.contains(player);
    }

    public void addPlayer(Player player) {
        players.add(player);
        player.addTeam(this);
    }

    public void removePlayer(Player player) {
        players.remove(player);
        player.removeTeam(this);
    }

    private Stream<GameTeam> getPendingGamesStream() {
        return this.getGameTeams()
                .stream()
                .filter(Predicate.not(GameTeam::isClosed));
    }

    public List<GameTeam> getPendingGames() {
        return getPendingGamesStream().collect(Collectors.toList());
    }

    public boolean hasPendingGames() {
        return getPendingGamesStream().findAny().isPresent();
    }

    public boolean hasPendingGamesWithPlayer(Player player) {
        return getPendingGamesStream()
                .anyMatch(gameTeam -> gameTeam.hasPlayer(player));
    }

    public boolean hasPendingTournaments() {
        return getPlacements().stream().anyMatch(Predicate.not(Placement::isFinished));
    }

    public boolean containsAllPlayers(Set<Player> players) {
        return this.players.containsAll(players);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Team team = (Team) o;
        return Objects.equals(getId(), team.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
