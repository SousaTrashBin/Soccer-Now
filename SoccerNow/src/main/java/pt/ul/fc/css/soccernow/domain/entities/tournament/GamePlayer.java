package pt.ul.fc.css.soccernow.domain.entities.tournament;

import jakarta.persistence.*;
import pt.ul.fc.css.soccernow.domain.entities.user.Player;
import pt.ul.fc.css.soccernow.util.FutsalPositionEnum;

import java.util.UUID;

@Entity
@Table(name = "game_players")
public class GamePlayer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "played_in_position", nullable = false)
    private FutsalPositionEnum playedInPosition;

    @ManyToOne(optional = false)
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public FutsalPositionEnum getPlayedInPosition() {
        return playedInPosition;
    }

    public void setPlayedInPosition(FutsalPositionEnum playedInPosition) {
        this.playedInPosition = playedInPosition;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
