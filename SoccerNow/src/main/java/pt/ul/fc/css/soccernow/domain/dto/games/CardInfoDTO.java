package pt.ul.fc.css.soccernow.domain.dto.games;

import pt.ul.fc.css.soccernow.domain.dto.user.PlayerInfoDTO;
import pt.ul.fc.css.soccernow.domain.dto.user.RefereeInfoDTO;
import pt.ul.fc.css.soccernow.domain.entities.game.Card;
import pt.ul.fc.css.soccernow.util.CardEnum;

import java.io.Serializable;
import java.util.Objects;

/**
 * DTO for {@link Card}
 */
public class CardInfoDTO implements Serializable {
    private CardEnum cardType = null;
    private Integer playerScoredGoals = 0;
    private PlayerInfoDTO playerPlayer = null;
    private RefereeInfoDTO referee = null;

    public CardInfoDTO(){}

    public CardInfoDTO(CardEnum cardType, Integer playerScoredGoals, PlayerInfoDTO playerPlayer, RefereeInfoDTO referee) {
        this.cardType = cardType;
        this.playerScoredGoals = playerScoredGoals;
        this.playerPlayer = playerPlayer;
        this.referee = referee;
    }

    public CardInfoDTO(CardEnum cardEnum, RefereeInfoDTO refereeInfoDTO) {
        this.cardType = cardEnum;
        this.referee = refereeInfoDTO;
    }

    public void setCardType(CardEnum cardType) {
        this.cardType = cardType;
    }

    public void setPlayerScoredGoals(Integer playerScoredGoals) {
        this.playerScoredGoals = playerScoredGoals;
    }

    public void setPlayerPlayer(PlayerInfoDTO playerPlayer) {
        this.playerPlayer = playerPlayer;
    }

    public void setReferee(RefereeInfoDTO referee) {
        this.referee = referee;
    }

    public CardEnum getCardType() {
        return cardType;
    }

    public Integer getPlayerScoredGoals() {
        return playerScoredGoals;
    }

    public PlayerInfoDTO getPlayerPlayer() {
        return playerPlayer;
    }

    public RefereeInfoDTO getReferee() {
        return referee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CardInfoDTO entity = (CardInfoDTO) o;
        return Objects.equals(this.cardType, entity.cardType) &&
                Objects.equals(this.playerScoredGoals, entity.playerScoredGoals) &&
                Objects.equals(this.playerPlayer, entity.playerPlayer) &&
                Objects.equals(this.referee, entity.referee);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardType, playerScoredGoals, playerPlayer, referee);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "cardType = " + cardType + ", " +
                "playerScoredGoals = " + playerScoredGoals + ", " +
                "playerPlayer = " + playerPlayer + ", " +
                "referee = " + referee + ")";
    }
}