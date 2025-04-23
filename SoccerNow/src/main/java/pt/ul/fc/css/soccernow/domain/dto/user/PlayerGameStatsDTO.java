package pt.ul.fc.css.soccernow.domain.dto.user;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import pt.ul.fc.css.soccernow.util.CardEnum;

import java.io.Serializable;
import java.util.Objects;

/**
 * DTO for {@link pt.ul.fc.css.soccernow.domain.entities.game.PlayerGameStats}
 */
public class PlayerGameStatsDTO implements Serializable {
    @NotNull
    private CardEnum givenCard = CardEnum.NONE;
    @NotNull
    @PositiveOrZero
    private Integer scoredGoals = 0;

    public PlayerGameStatsDTO() {
    }

    public PlayerGameStatsDTO(CardEnum givenCard, Integer scoredGoals) {
        this.givenCard = givenCard;
        this.scoredGoals = scoredGoals;
    }

    public CardEnum getGivenCard() {
        return givenCard;
    }

    public PlayerGameStatsDTO setGivenCard(CardEnum givenCard) {
        this.givenCard = givenCard;
        return this;
    }

    public Integer getScoredGoals() {
        return scoredGoals;
    }

    public PlayerGameStatsDTO setScoredGoals(Integer scoredGoals) {
        this.scoredGoals = scoredGoals;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerGameStatsDTO entity = (PlayerGameStatsDTO) o;
        return Objects.equals(this.givenCard, entity.givenCard) &&
                Objects.equals(this.scoredGoals, entity.scoredGoals);
    }

    @Override
    public int hashCode() {
        return Objects.hash(givenCard, scoredGoals);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "givenCard = " + givenCard + ", " +
                "scoredGoals = " + scoredGoals + ")";
    }
}
