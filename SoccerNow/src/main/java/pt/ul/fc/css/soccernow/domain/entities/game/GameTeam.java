package pt.ul.fc.css.soccernow.domain.entities.game;

import jakarta.persistence.*;
import pt.ul.fc.css.soccernow.domain.entities.Team;
import pt.ul.fc.css.soccernow.domain.entities.tournament.GamePlayer;
import pt.ul.fc.css.soccernow.domain.entities.user.Player;
import pt.ul.fc.css.soccernow.util.FutsalPositionEnum;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

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
                               .map(futsalPosition -> futsalPosition.equals(FutsalPositionEnum.GOALIE))
                               .count() == 1;
    }

    public boolean hasTheRightSize() {
        return getGamePlayers().size() == FUTSAL_TEAM_SIZE;
    }
}
