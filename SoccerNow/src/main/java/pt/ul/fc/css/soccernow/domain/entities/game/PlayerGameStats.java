package pt.ul.fc.css.soccernow.domain.entities.game;

import jakarta.persistence.*;
import org.hibernate.proxy.HibernateProxy;
import pt.ul.fc.css.soccernow.domain.entities.user.Player;
import pt.ul.fc.css.soccernow.util.CardEnum;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "player_game_stats")
public class PlayerGameStats {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "given_card")
    private CardEnum givenCard = CardEnum.NONE;

    @Column(name = "scored_goals", nullable = false)
    private Integer scoredGoals = 0;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "game_stats_id", nullable = false)
    private GameStats gameStats;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "player_id")
    private Player player;

    public GameStats getGameStats() {
        return gameStats;
    }

    public void setGameStats(GameStats gameStats) {
        this.gameStats = gameStats;
    }

    public Integer getScoredGoals() {
        return scoredGoals;
    }

    public void setScoredGoals(Integer scoredGoals) {
        this.scoredGoals = scoredGoals;
    }

    public CardEnum getGivenCard() {
        return givenCard;
    }

    public void setGivenCard(CardEnum givenCard) {
        this.givenCard = givenCard;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }


    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        PlayerGameStats that = (PlayerGameStats) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "givenCard = " + givenCard + ", " +
                "scoredGoals = " + scoredGoals + ", " +
                "gameStats = " + gameStats + ", " +
                "player = " + player + ")";
    }
}
