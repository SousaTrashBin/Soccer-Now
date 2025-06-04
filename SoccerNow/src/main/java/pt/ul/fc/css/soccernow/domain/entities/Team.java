package pt.ul.fc.css.soccernow.domain.entities;

import jakarta.persistence.*;
import org.hibernate.proxy.HibernateProxy;
import pt.ul.fc.css.soccernow.domain.entities.game.Game;
import pt.ul.fc.css.soccernow.domain.entities.tournament.Placement;
import pt.ul.fc.css.soccernow.domain.entities.tournament.Tournament;
import pt.ul.fc.css.soccernow.domain.entities.user.Player;
import pt.ul.fc.css.soccernow.util.PlacementEnum;
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

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "team_id")
    private Set<Placement> placements = new LinkedHashSet<>();

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany
    @JoinTable(name = "teams_games",
            joinColumns = @JoinColumn(name = "team_id"),
            inverseJoinColumns = @JoinColumn(name = "game_id"))
    private Set<Game> games = new LinkedHashSet<>();

    public Set<Game> getGames() {
        return games;
    }

    public void setGames(Set<Game> games) {
        this.games = games;
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

    private Stream<Game> getPendingGamesStream() {
        return this.getGames()
                .stream()
                .filter(Predicate.not(Game::isClosed));
    }

    @Override
    public void delete() {
        super.delete();
        List<Player> playersCopy = new ArrayList<>(getPlayers());
        for (Player player : playersCopy) {
            removePlayer(player);
        }
    }

    public List<Game> getPendingGames() {
        return getPendingGamesStream().collect(Collectors.toList());
    }

    public boolean hasPendingGames() {
        return getPendingGamesStream().findAny().isPresent();
    }

    public boolean hasPendingGamesWithPlayer(Player player) {
        return getPendingGamesStream().anyMatch(game -> game.hasPlayer(player));
    }

    public boolean hasPendingTournaments() {
        return getPlacements().stream().anyMatch(Predicate.not(Placement::isFinished));
    }

    public long getPlayersCardCount() {
        return players.stream().map(Player::getCardCount).reduce(0L, Long::sum);
    }

    public long getVictoryCount() {
        return games.stream()
                .map(Game::whoWonId)
                .filter(Objects::nonNull)
                .filter(id -> id.equals(getId()))
                .count();
    }


    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Team team = (Team) o;
        return getId() != null && Objects.equals(getId(), team.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

    public void registerGame(Game game) {
        games.add(game);
    }

    public void addGame(Game game) {
        games.add(game);
    }

    public void addTournament(Tournament tournament) {
        Placement placement = new Placement();
        placement.setTournament(tournament);
        placement.setValue(PlacementEnum.PENDING);
        placements.add(placement);
    }

    public void removeTournament(Tournament tournament) {
        placements.removeIf(p -> tournament.equals(p.getTournament()));
    }

    public Placement getPlacementForTournament(Tournament tournament) {
        return placements.stream()
                .filter(p -> p.getTournament().equals(tournament))
                .findFirst()
                .orElse(null);
    }
}
