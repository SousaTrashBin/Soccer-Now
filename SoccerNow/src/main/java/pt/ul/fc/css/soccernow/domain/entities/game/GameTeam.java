package pt.ul.fc.css.soccernow.domain.entities.game;

import jakarta.persistence.*;
import org.hibernate.proxy.HibernateProxy;
import pt.ul.fc.css.soccernow.domain.entities.Team;
import pt.ul.fc.css.soccernow.domain.entities.tournament.GamePlayer;
import pt.ul.fc.css.soccernow.domain.entities.user.Player;
import pt.ul.fc.css.soccernow.util.FutsalPositionEnum;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(name = "game_teams")
public class GameTeam {
    public static final int FUTSAL_TEAM_SIZE = 5;
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "game_team_id")
    private Set<GamePlayer> gamePlayers = new HashSet<>();

    @ManyToOne(optional = false)
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    @OneToOne(optional = false, orphanRemoval = true)
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Set<GamePlayer> getGamePlayers() {
        return gamePlayers;
    }

    public void setGamePlayers(Set<GamePlayer> gamePlayers) {
        this.gamePlayers = gamePlayers;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public boolean hasPlayer(Player player) {
        return getGamePlayers().stream()
                .map(GamePlayer::getPlayer)
                .anyMatch(player::equals);
    }

    public boolean hasExactlyOneGoalKeeper() {
        return getGamePlayers().stream()
                .map(GamePlayer::getPlayedInPosition)
                .filter(futsalPosition -> futsalPosition.equals(FutsalPositionEnum.GOALIE))
                .count() == 1;
    }

    public boolean hasTheRightSize() {
        return getGamePlayers().size() == FUTSAL_TEAM_SIZE;
    }

    public boolean isClosed() {
        return getGame().isClosed();
    }

    public Set<Player> getPlayers() {
        return gamePlayers.stream().map(GamePlayer::getPlayer).collect(Collectors.toSet());
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        GameTeam gameTeam = (GameTeam) o;
        return getId() != null && Objects.equals(getId(), gameTeam.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "team = " + team + ", " +
                "game = " + game + ")";
    }

    public boolean hasAllPlayersOnTeam(Team team) {
        return getPlayers().stream().allMatch(gamePlayer -> gamePlayer.hasTeam(team));
    }
}
