package pt.ul.fc.css.soccernow.domain.entities.game;

import jakarta.persistence.*;
import pt.ul.fc.css.soccernow.domain.entities.user.Referee;
import pt.ul.fc.css.soccernow.util.CardEnum;

import java.util.UUID;

@Entity
@Table(name = "card")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "card_type", nullable = false)
    private CardEnum cardType;

    @ManyToOne(optional = false,cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "player_id", nullable = false)
    private PlayerGameStats player;

    @ManyToOne(optional = false,cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "referee_id", nullable = false)
    private Referee referee;

    public Referee getReferee() {
        return referee;
    }

    public void setReferee(Referee referee) {
        this.referee = referee;
    }

    public PlayerGameStats getPlayer() {
        return player;
    }

    public void setPlayer(PlayerGameStats player) {
        this.player = player;
    }

    public CardEnum getCardType() {
        return cardType;
    }

    public void setCardType(CardEnum cardType) {
        this.cardType = cardType;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

}
