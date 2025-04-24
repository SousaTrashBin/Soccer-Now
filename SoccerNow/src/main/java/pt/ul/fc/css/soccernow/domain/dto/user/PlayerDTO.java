package pt.ul.fc.css.soccernow.domain.dto.user;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import org.hibernate.validator.constraints.Length;
import pt.ul.fc.css.soccernow.domain.entities.Team;
import pt.ul.fc.css.soccernow.domain.entities.game.Game;
import pt.ul.fc.css.soccernow.domain.entities.game.PlayerGameStats;
import pt.ul.fc.css.soccernow.domain.entities.user.Player;
import pt.ul.fc.css.soccernow.util.CardEnum;
import pt.ul.fc.css.soccernow.util.FutsalPositionEnum;

import java.io.Serializable;
import java.util.*;

/**
 * DTO for {@link Player}
 */
public class PlayerDTO implements Serializable {
    private UUID id;
    @Pattern(regexp = "^\\p{L}+( \\p{L}+)*$")
    @Length(max = 100)
    private String name;
    private FutsalPositionEnum preferredPosition;
    private Set<TeamInfoDTO> teams = new LinkedHashSet<>();
    private List<PlayerGameStatsDTO> playerGameStats = new ArrayList<>();

    public PlayerDTO() {
    }

    public PlayerDTO(UUID id, String name, FutsalPositionEnum preferredPosition, Set<TeamInfoDTO> teams, List<PlayerGameStatsDTO> playerGameStats) {
        this.id = id;
        this.name = name;
        this.preferredPosition = preferredPosition;
        this.teams = teams;
        this.playerGameStats = playerGameStats;
    }

    public UUID getId() {
        return id;
    }

    public PlayerDTO setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public PlayerDTO setName(String name) {
        this.name = name;
        return this;
    }

    public FutsalPositionEnum getPreferredPosition() {
        return preferredPosition;
    }

    public PlayerDTO setPreferredPosition(FutsalPositionEnum preferredPosition) {
        this.preferredPosition = preferredPosition;
        return this;
    }

    public Set<TeamInfoDTO> getTeams() {
        return teams;
    }

    public PlayerDTO setTeams(Set<TeamInfoDTO> teams) {
        this.teams = teams;
        return this;
    }

    public List<PlayerGameStatsDTO> getPlayerGameStats() {
        return playerGameStats;
    }

    public PlayerDTO setPlayerGameStats(List<PlayerGameStatsDTO> playerGameStats) {
        this.playerGameStats = playerGameStats;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerDTO entity = (PlayerDTO) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.name, entity.name) &&
                Objects.equals(this.preferredPosition, entity.preferredPosition) &&
                Objects.equals(this.teams, entity.teams) &&
                Objects.equals(this.playerGameStats, entity.playerGameStats);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, preferredPosition, teams, playerGameStats);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "name = " + name + ", " +
                "preferredPosition = " + preferredPosition + ", " +
                "teams = " + teams + ", " +
                "playerGameStats = " + playerGameStats + ")";
    }

    /**
     * DTO for {@link Team}
     */
    public static class TeamInfoDTO implements Serializable {
        @NotNull
        private UUID id;
        @Pattern(regexp = "^\\p{L}+( \\p{L}+)*$")
        @Length(max = 100)
        private String name;

        public TeamInfoDTO() {
        }

        public TeamInfoDTO(UUID id, String name) {
            this.id = id;
            this.name = name;
        }

        public UUID getId() {
            return id;
        }

        public TeamInfoDTO setId(UUID id) {
            this.id = id;
            return this;
        }

        public String getName() {
            return name;
        }

        public TeamInfoDTO setName(String name) {
            this.name = name;
            return this;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TeamInfoDTO entity = (TeamInfoDTO) o;
            return Objects.equals(this.id, entity.id) &&
                    Objects.equals(this.name, entity.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, name);
        }

        @Override
        public String toString() {
            return getClass().getSimpleName() + "(" +
                    "id = " + id + ", " +
                    "name = " + name + ")";
        }
    }

    /**
     * DTO for {@link PlayerGameStats}
     */
    public static class PlayerGameStatsDTO implements Serializable {
        @NotNull
        private CardEnum givenCard = CardEnum.NONE;
        @NotNull
        @PositiveOrZero
        private Integer scoredGoals = 0;
        private GameInfoDTO game;

        public PlayerGameStatsDTO() {
        }

        public PlayerGameStatsDTO(CardEnum givenCard, Integer scoredGoals, GameInfoDTO game) {
            this.givenCard = givenCard;
            this.scoredGoals = scoredGoals;
            this.game = game;
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

        public GameInfoDTO getGame() {
            return game;
        }

        public PlayerGameStatsDTO setGame(GameInfoDTO game) {
            this.game = game;
            return this;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PlayerGameStatsDTO entity = (PlayerGameStatsDTO) o;
            return Objects.equals(this.givenCard, entity.givenCard) &&
                    Objects.equals(this.scoredGoals, entity.scoredGoals) &&
                    Objects.equals(this.game, entity.game);
        }

        @Override
        public int hashCode() {
            return Objects.hash(givenCard, scoredGoals, game);
        }

        @Override
        public String toString() {
            return getClass().getSimpleName() + "(" +
                    "givenCard = " + givenCard + ", " +
                    "scoredGoals = " + scoredGoals + ", " +
                    "game = " + game + ")";
        }

        /**
         * DTO for {@link Game}
         */
        public static class GameInfoDTO implements Serializable {
            @NotNull
            private UUID id;

            public GameInfoDTO() {
            }

            public GameInfoDTO(UUID id) {
                this.id = id;
            }

            public UUID getId() {
                return id;
            }

            public GameInfoDTO setId(UUID id) {
                this.id = id;
                return this;
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                GameInfoDTO entity = (GameInfoDTO) o;
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
}
