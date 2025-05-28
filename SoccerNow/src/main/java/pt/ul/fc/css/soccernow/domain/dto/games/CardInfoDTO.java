package pt.ul.fc.css.soccernow.domain.dto.games;

import jakarta.validation.constraints.NotNull;
import pt.ul.fc.css.soccernow.domain.dto.user.RefereeInfoDTO;
import pt.ul.fc.css.soccernow.util.CardEnum;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * DTO for {@link pt.ul.fc.css.soccernow.domain.entities.game.Card}
 */
public class CardInfoDTO implements Serializable {
    private UUID id;
    private CardEnum cardType;
    @NotNull
    private PlayerGameStatsDTO player;
    private RefereeInfoDTO referee;

    public CardInfoDTO() {
    }

    public CardInfoDTO(UUID id, CardEnum cardType, PlayerGameStatsDTO player, RefereeInfoDTO referee) {
        this.id = id;
        this.cardType = cardType;
        this.player = player;
        this.referee = referee;
    }

    public CardInfoDTO(CardEnum cardEnum, RefereeInfoDTO refereeInfoDTO) {
        this.cardType = cardEnum;
        this.referee = refereeInfoDTO;
    }

    public UUID getId() {
        return id;
    }

    public CardInfoDTO setId(UUID id) {
        this.id = id;
        return this;
    }

    public CardEnum getCardType() {
        return cardType;
    }

    public CardInfoDTO setCardType(CardEnum cardType) {
        this.cardType = cardType;
        return this;
    }

    public PlayerGameStatsDTO getPlayer() {
        return player;
    }

    public CardInfoDTO setPlayer(PlayerGameStatsDTO player) {
        this.player = player;
        return this;
    }

    public RefereeInfoDTO getReferee() {
        return referee;
    }

    public CardInfoDTO setReferee(RefereeInfoDTO referee) {
        this.referee = referee;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CardInfoDTO entity = (CardInfoDTO) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.cardType, entity.cardType) &&
                Objects.equals(this.player, entity.player) &&
                Objects.equals(this.referee, entity.referee);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cardType, player, referee);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "cardType = " + cardType + ", " +
                "player = " + player + ", " +
                "referee = " + referee + ")";
    }
}
