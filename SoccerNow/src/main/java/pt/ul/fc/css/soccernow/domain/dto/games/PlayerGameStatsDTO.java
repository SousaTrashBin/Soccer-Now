package pt.ul.fc.css.soccernow.domain.dto.games;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import pt.ul.fc.css.soccernow.util.CardEnum;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * DTO for {@link pt.ul.fc.css.soccernow.domain.entities.game.PlayerGameStats}
 */
public class PlayerGameStatsDTO implements Serializable {
    @NotNull
    private CardEnum givenCard = CardEnum.NONE;
    @NotNull
    @PositiveOrZero
    private Integer scoredGoals = 0;
    @NotNull
    private PlayerGameStatsDTO.PlayerInfoDTO player;

    public PlayerGameStatsDTO() {
    }

    public PlayerGameStatsDTO(CardEnum givenCard, Integer scoredGoals, PlayerInfoDTO player) {
        this.givenCard = givenCard;
        this.scoredGoals = scoredGoals;
        this.player = player;
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

    public PlayerInfoDTO getPlayer() {
        return player;
    }

    public PlayerGameStatsDTO setPlayer(PlayerInfoDTO player) {
        this.player = player;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerGameStatsDTO entity = (PlayerGameStatsDTO) o;
        return Objects.equals(this.givenCard, entity.givenCard) &&
                Objects.equals(this.scoredGoals, entity.scoredGoals) &&
                Objects.equals(this.player, entity.player);
    }

    @Override
    public int hashCode() {
        return Objects.hash(givenCard, scoredGoals, player);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "givenCard = " + givenCard + ", " +
                "scoredGoals = " + scoredGoals + ", " +
                "player = " + player + ")";
    }

    /**
     * DTO for {@link pt.ul.fc.css.soccernow.domain.entities.user.Player}
     */
    public static class PlayerInfoDTO implements Serializable {
        @NotNull
        private UUID id;

        public PlayerInfoDTO() {
        }

        public PlayerInfoDTO(UUID id) {
            this.id = id;
        }

        public UUID getId() {
            return id;
        }

        public PlayerInfoDTO setId(UUID id) {
            this.id = id;
            return this;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PlayerInfoDTO entity = (PlayerInfoDTO) o;
            return Objects.equals(this.id, entity.id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }

        @Override
        public String toString() {
            return getClass().getSimpleName() + "(" +
                    "id = " + id + ")";
        }
    }
}
