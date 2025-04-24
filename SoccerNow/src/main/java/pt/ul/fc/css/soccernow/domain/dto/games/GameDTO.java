package pt.ul.fc.css.soccernow.domain.dto.games;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;
import pt.ul.fc.css.soccernow.util.CardEnum;
import pt.ul.fc.css.soccernow.util.FutsalPositionEnum;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

/**
 * DTO for {@link pt.ul.fc.css.soccernow.domain.entities.game.Game}
 */
public class GameDTO implements Serializable {
    private TournamentInfoDTO tournament;
    private UUID id;
    private GameTeamInfoDTO gameTeamOne;
    private GameTeamInfoDTO gameTeamTwo;
    private GameStatsInfoDTO gameStats;
    private RefereeInfoDTO primaryReferee;
    private Set<RefereeInfoDTO> secondaryReferees = new HashSet<>();
    private AddressDTO locatedIn;
    private LocalDateTime happensIn;
    @NotNull
    private Boolean isClosed = false;

    public GameDTO() {
    }

    public GameDTO(
            UUID tournamentId,
            Boolean tournamentIsFinished, UUID id, GameTeamInfoDTO gameTeamOne, GameTeamInfoDTO gameTeamTwo, GameStatsInfoDTO gameStats, RefereeInfoDTO primaryReferee, Set<RefereeInfoDTO> secondaryReferees, AddressDTO locatedIn, LocalDateTime happensIn, Boolean isClosed) {
        this.id = id;
        this.gameTeamOne = gameTeamOne;
        this.gameTeamTwo = gameTeamTwo;
        this.gameStats = gameStats;
        this.primaryReferee = primaryReferee;
        this.secondaryReferees = secondaryReferees;
        this.locatedIn = locatedIn;
        this.happensIn = happensIn;
        this.isClosed = isClosed;
    }

    public UUID getId() {
        return id;
    }

    public GameDTO setId(UUID id) {
        this.id = id;
        return this;
    }

    public GameTeamInfoDTO getGameTeamOne() {
        return gameTeamOne;
    }

    public GameDTO setGameTeamOne(GameTeamInfoDTO gameTeamOne) {
        this.gameTeamOne = gameTeamOne;
        return this;
    }

    public GameTeamInfoDTO getGameTeamTwo() {
        return gameTeamTwo;
    }

    public GameDTO setGameTeamTwo(GameTeamInfoDTO gameTeamTwo) {
        this.gameTeamTwo = gameTeamTwo;
        return this;
    }

    public GameStatsInfoDTO getGameStats() {
        return gameStats;
    }

    public GameDTO setGameStats(GameStatsInfoDTO gameStats) {
        this.gameStats = gameStats;
        return this;
    }

    public RefereeInfoDTO getPrimaryReferee() {
        return primaryReferee;
    }

    public GameDTO setPrimaryReferee(RefereeInfoDTO primaryReferee) {
        this.primaryReferee = primaryReferee;
        return this;
    }

    public Set<RefereeInfoDTO> getSecondaryReferees() {
        return secondaryReferees;
    }

    public GameDTO setSecondaryReferees(Set<RefereeInfoDTO> secondaryReferees) {
        this.secondaryReferees = secondaryReferees;
        return this;
    }

    public AddressDTO getLocatedIn() {
        return locatedIn;
    }

    public GameDTO setLocatedIn(AddressDTO locatedIn) {
        this.locatedIn = locatedIn;
        return this;
    }

    public LocalDateTime getHappensIn() {
        return happensIn;
    }

    public GameDTO setHappensIn(LocalDateTime happensIn) {
        this.happensIn = happensIn;
        return this;
    }

    public Boolean getIsClosed() {
        return isClosed;
    }

    public GameDTO setIsClosed(Boolean isClosed) {
        this.isClosed = isClosed;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameDTO entity = (GameDTO) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.gameTeamOne, entity.gameTeamOne) &&
                Objects.equals(this.gameTeamTwo, entity.gameTeamTwo) &&
                Objects.equals(this.gameStats, entity.gameStats) &&
                Objects.equals(this.primaryReferee, entity.primaryReferee) &&
                Objects.equals(this.secondaryReferees, entity.secondaryReferees) &&
                Objects.equals(this.locatedIn, entity.locatedIn) &&
                Objects.equals(this.happensIn, entity.happensIn) &&
                Objects.equals(this.isClosed, entity.isClosed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, gameTeamOne, gameTeamTwo, gameStats, primaryReferee, secondaryReferees, locatedIn, happensIn, isClosed);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "gameTeamOne = " + gameTeamOne + ", " +
                "gameTeamTwo = " + gameTeamTwo + ", " +
                "gameStats = " + gameStats + ", " +
                "primaryReferee = " + primaryReferee + ", " +
                "secondaryReferees = " + secondaryReferees + ", " +
                "locatedIn = " + locatedIn + ", " +
                "happensIn = " + happensIn + ", " +
                "isClosed = " + isClosed + ", )";
    }

    public TournamentInfoDTO getTournament() {
        return tournament;
    }

    public GameDTO setTournament(TournamentInfoDTO tournament) {
        this.tournament = tournament;
        return this;
    }

    /**
     * DTO for {@link pt.ul.fc.css.soccernow.domain.entities.game.GameTeam}
     */
    public static class GameTeamInfoDTO implements Serializable {
        private Set<GamePlayerInfoDTO> gamePlayers = new HashSet<>();
        private TeamInfoDTO team;

        public GameTeamInfoDTO() {
        }

        public GameTeamInfoDTO(Set<GamePlayerInfoDTO> gamePlayers, TeamInfoDTO team) {
            this.gamePlayers = gamePlayers;
            this.team = team;
        }

        public Set<GamePlayerInfoDTO> getGamePlayers() {
            return gamePlayers;
        }

        public GameTeamInfoDTO setGamePlayers(Set<GamePlayerInfoDTO> gamePlayers) {
            this.gamePlayers = gamePlayers;
            return this;
        }

        public TeamInfoDTO getTeam() {
            return team;
        }

        public GameTeamInfoDTO setTeam(TeamInfoDTO team) {
            this.team = team;
            return this;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GameTeamInfoDTO entity = (GameTeamInfoDTO) o;
            return Objects.equals(this.gamePlayers, entity.gamePlayers) &&
                    Objects.equals(this.team, entity.team);
        }

        @Override
        public int hashCode() {
            return Objects.hash(gamePlayers, team);
        }

        @Override
        public String toString() {
            return getClass().getSimpleName() + "(" +
                    "gamePlayers = " + gamePlayers + ", " +
                    "team = " + team + ")";
        }

        /**
         * DTO for {@link pt.ul.fc.css.soccernow.domain.entities.tournament.GamePlayer}
         */
        public static class GamePlayerInfoDTO implements Serializable {
            private FutsalPositionEnum playedInPosition;
            private PlayerInfoDTO player;

            public GamePlayerInfoDTO() {
            }

            public GamePlayerInfoDTO(FutsalPositionEnum playedInPosition, PlayerInfoDTO player) {
                this.playedInPosition = playedInPosition;
                this.player = player;
            }

            public FutsalPositionEnum getPlayedInPosition() {
                return playedInPosition;
            }

            public GamePlayerInfoDTO setPlayedInPosition(FutsalPositionEnum playedInPosition) {
                this.playedInPosition = playedInPosition;
                return this;
            }

            public PlayerInfoDTO getPlayer() {
                return player;
            }

            public GamePlayerInfoDTO setPlayer(PlayerInfoDTO player) {
                this.player = player;
                return this;
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                GamePlayerInfoDTO entity = (GamePlayerInfoDTO) o;
                return Objects.equals(this.playedInPosition, entity.playedInPosition) &&
                        Objects.equals(this.player, entity.player);
            }

            @Override
            public int hashCode() {
                return Objects.hash(playedInPosition, player);
            }

            @Override
            public String toString() {
                return getClass().getSimpleName() + "(" +
                        "playedInPosition = " + playedInPosition + ", " +
                        "player = " + player + ")";
            }

            /**
             * DTO for {@link pt.ul.fc.css.soccernow.domain.entities.user.Player}
             */
            public static class PlayerInfoDTO implements Serializable {
                private UUID id;
                @Pattern(regexp = "^\\p{L}+( \\p{L}+)*$")
                @Length(max = 100)
                private String name;

                public PlayerInfoDTO() {
                }

                public PlayerInfoDTO(UUID id, String name) {
                    this.id = id;
                    this.name = name;
                }

                public UUID getId() {
                    return id;
                }

                public PlayerInfoDTO setId(UUID id) {
                    this.id = id;
                    return this;
                }

                public String getName() {
                    return name;
                }

                public PlayerInfoDTO setName(String name) {
                    this.name = name;
                    return this;
                }

                @Override
                public boolean equals(Object o) {
                    if (this == o) return true;
                    if (o == null || getClass() != o.getClass()) return false;
                    PlayerInfoDTO entity = (PlayerInfoDTO) o;
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
        }

        /**
         * DTO for {@link pt.ul.fc.css.soccernow.domain.entities.Team}
         */
        public static class TeamInfoDTO implements Serializable {
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
    }

    /**
     * DTO for {@link pt.ul.fc.css.soccernow.domain.entities.game.GameStats}
     */
    public static class GameStatsInfoDTO implements Serializable {
        private Integer teamOneGoals = 0;
        private Integer teamTwoGoals = 0;
        @Size(min = 10, max = 10)
        private Set<PlayerGameStatsInfoDTO> playerGameStats = new LinkedHashSet<>();

        public GameStatsInfoDTO() {
        }

        public GameStatsInfoDTO(Integer teamOneGoals, Integer teamTwoGoals, Set<PlayerGameStatsInfoDTO> playerGameStats) {
            this.teamOneGoals = teamOneGoals;
            this.teamTwoGoals = teamTwoGoals;
            this.playerGameStats = playerGameStats;
        }

        public Integer getTeamOneGoals() {
            return teamOneGoals;
        }

        public GameStatsInfoDTO setTeamOneGoals(Integer teamOneGoals) {
            this.teamOneGoals = teamOneGoals;
            return this;
        }

        public Integer getTeamTwoGoals() {
            return teamTwoGoals;
        }

        public GameStatsInfoDTO setTeamTwoGoals(Integer teamTwoGoals) {
            this.teamTwoGoals = teamTwoGoals;
            return this;
        }

        public Set<PlayerGameStatsInfoDTO> getPlayerGameStats() {
            return playerGameStats;
        }

        public GameStatsInfoDTO setPlayerGameStats(Set<PlayerGameStatsInfoDTO> playerGameStats) {
            this.playerGameStats = playerGameStats;
            return this;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GameStatsInfoDTO entity = (GameStatsInfoDTO) o;
            return Objects.equals(this.teamOneGoals, entity.teamOneGoals) &&
                    Objects.equals(this.teamTwoGoals, entity.teamTwoGoals) &&
                    Objects.equals(this.playerGameStats, entity.playerGameStats);
        }

        @Override
        public int hashCode() {
            return Objects.hash(teamOneGoals, teamTwoGoals, playerGameStats);
        }

        @Override
        public String toString() {
            return getClass().getSimpleName() + "(" +
                    "teamOneGoals = " + teamOneGoals + ", " +
                    "teamTwoGoals = " + teamTwoGoals + ", " +
                    "playerGameStats = " + playerGameStats + ")";
        }

        /**
         * DTO for {@link pt.ul.fc.css.soccernow.domain.entities.game.PlayerGameStats}
         */
        public static class PlayerGameStatsInfoDTO implements Serializable {
            @NotNull
            private CardEnum givenCard = CardEnum.NONE;
            @PositiveOrZero
            private Integer scoredGoals = 0;
            private PlayerInfoDTO player;

            public PlayerGameStatsInfoDTO() {
            }

            public PlayerGameStatsInfoDTO(CardEnum givenCard, Integer scoredGoals, PlayerInfoDTO player) {
                this.givenCard = givenCard;
                this.scoredGoals = scoredGoals;
                this.player = player;
            }

            public CardEnum getGivenCard() {
                return givenCard;
            }

            public PlayerGameStatsInfoDTO setGivenCard(CardEnum givenCard) {
                this.givenCard = givenCard;
                return this;
            }

            public Integer getScoredGoals() {
                return scoredGoals;
            }

            public PlayerGameStatsInfoDTO setScoredGoals(Integer scoredGoals) {
                this.scoredGoals = scoredGoals;
                return this;
            }

            public PlayerInfoDTO getPlayer() {
                return player;
            }

            public PlayerGameStatsInfoDTO setPlayer(PlayerInfoDTO player) {
                this.player = player;
                return this;
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                PlayerGameStatsInfoDTO entity = (PlayerGameStatsInfoDTO) o;
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
                private UUID id;
                @Pattern(regexp = "^\\p{L}+( \\p{L}+)*$")
                @Length(max = 100)
                private String name;

                public PlayerInfoDTO() {
                }

                public PlayerInfoDTO(UUID id, String name) {
                    this.id = id;
                    this.name = name;
                }

                public UUID getId() {
                    return id;
                }

                public PlayerInfoDTO setId(UUID id) {
                    this.id = id;
                    return this;
                }

                public String getName() {
                    return name;
                }

                public PlayerInfoDTO setName(String name) {
                    this.name = name;
                    return this;
                }

                @Override
                public boolean equals(Object o) {
                    if (this == o) return true;
                    if (o == null || getClass() != o.getClass()) return false;
                    PlayerInfoDTO entity = (PlayerInfoDTO) o;
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
        }
    }

    /**
     * DTO for {@link pt.ul.fc.css.soccernow.domain.entities.user.Referee}
     */
    public static class RefereeInfoDTO implements Serializable {
        private UUID id;
        @Pattern(regexp = "^\\p{L}+( \\p{L}+)*$")
        @Length(max = 100)
        private String name;
        private Boolean hasCertificate = false;

        public RefereeInfoDTO() {
        }

        public RefereeInfoDTO(UUID id, String name, Boolean hasCertificate) {
            this.id = id;
            this.name = name;
            this.hasCertificate = hasCertificate;
        }

        public UUID getId() {
            return id;
        }

        public RefereeInfoDTO setId(UUID id) {
            this.id = id;
            return this;
        }

        public String getName() {
            return name;
        }

        public RefereeInfoDTO setName(String name) {
            this.name = name;
            return this;
        }

        public Boolean getHasCertificate() {
            return hasCertificate;
        }

        public RefereeInfoDTO setHasCertificate(Boolean hasCertificate) {
            this.hasCertificate = hasCertificate;
            return this;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            RefereeInfoDTO entity = (RefereeInfoDTO) o;
            return Objects.equals(this.id, entity.id) &&
                    Objects.equals(this.name, entity.name) &&
                    Objects.equals(this.hasCertificate, entity.hasCertificate);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, name, hasCertificate);
        }

        @Override
        public String toString() {
            return getClass().getSimpleName() + "(" +
                    "id = " + id + ", " +
                    "name = " + name + ", " +
                    "hasCertificate = " + hasCertificate + ")";
        }
    }


    /**
     * DTO for {@link pt.ul.fc.css.soccernow.domain.entities.Address}
     */
    public static class AddressDTO implements Serializable {
        private String country;
        private String city;
        private String street;
        private String postalCode;

        public AddressDTO() {
        }

        public AddressDTO(String country, String city, String street, String postalCode) {
            this.country = country;
            this.city = city;
            this.street = street;
            this.postalCode = postalCode;
        }

        public String getCountry() {
            return country;
        }

        public AddressDTO setCountry(String country) {
            this.country = country;
            return this;
        }

        public String getCity() {
            return city;
        }

        public AddressDTO setCity(String city) {
            this.city = city;
            return this;
        }

        public String getStreet() {
            return street;
        }

        public AddressDTO setStreet(String street) {
            this.street = street;
            return this;
        }

        public String getPostalCode() {
            return postalCode;
        }

        public AddressDTO setPostalCode(String postalCode) {
            this.postalCode = postalCode;
            return this;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            AddressDTO entity = (AddressDTO) o;
            return Objects.equals(this.country, entity.country) &&
                    Objects.equals(this.city, entity.city) &&
                    Objects.equals(this.street, entity.street) &&
                    Objects.equals(this.postalCode, entity.postalCode);
        }

        @Override
        public int hashCode() {
            return Objects.hash(country, city, street, postalCode);
        }

        @Override
        public String toString() {
            return getClass().getSimpleName() + "(" +
                    "country = " + country + ", " +
                    "city = " + city + ", " +
                    "street = " + street + ", " +
                    "postalCode = " + postalCode + ")";
        }
    }

    /**
     * DTO for {@link pt.ul.fc.css.soccernow.domain.entities.tournament.Tournament}
     */
    public static class TournamentInfoDTO implements Serializable {
        private UUID id;
        private Boolean isFinished = false;

        public TournamentInfoDTO() {
        }

        public TournamentInfoDTO(UUID id, Boolean isFinished) {
            this.id = id;
            this.isFinished = isFinished;
        }

        public UUID getId() {
            return id;
        }

        public void setId(UUID id) {
            this.id = id;
        }

        public Boolean getIsFinished() {
            return isFinished;
        }

        public void setIsFinished(Boolean isFinished) {
            this.isFinished = isFinished;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TournamentInfoDTO entity = (TournamentInfoDTO) o;
            return Objects.equals(this.id, entity.id) &&
                    Objects.equals(this.isFinished, entity.isFinished);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, isFinished);
        }

        @Override
        public String toString() {
            return getClass().getSimpleName() + "(" +
                    "id = " + id + ", " +
                    "isFinished = " + isFinished + ")";
        }
    }
}
