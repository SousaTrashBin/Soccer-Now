package com.soccernow.ui.soccernowui.dto;

import com.soccernow.ui.soccernowui.util.FutsalPositionEnum;
import com.soccernow.ui.soccernowui.util.PlacementEnum;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class TeamDTO implements Serializable {
    @NotNull
    private Set<GameInfoDTO> games = new LinkedHashSet<>();
    private UUID id;
    private Set<PlayerInfoDTO> players = new LinkedHashSet<>();
    private Set<PlacementInfoDTO> placements = new LinkedHashSet<>();
    @Pattern(regexp = "^\\p{L}+( \\p{L}+)*$")
    @Length(max = 100)
    private String name;

    public TeamDTO() {
    }

    public TeamDTO(
            Set<GameInfoDTO> games, UUID id, Set<PlayerInfoDTO> players, Set<PlacementInfoDTO> placements, String name) {
        this.id = id;
        this.players = players;
        this.placements = placements;
        this.name = name;
        this.games = games;
    }

    public UUID getId() {
        return id;
    }

    public TeamDTO setId(UUID id) {
        this.id = id;
        return this;
    }

    public Set<PlayerInfoDTO> getPlayers() {
        return players;
    }

    public TeamDTO setPlayers(Set<PlayerInfoDTO> players) {
        this.players = players;
        return this;
    }

    public Set<PlacementInfoDTO> getPlacements() {
        return placements;
    }

    public TeamDTO setPlacements(Set<PlacementInfoDTO> placements) {
        this.placements = placements;
        return this;
    }

    public String getName() {
        return name;
    }

    public TeamDTO setName(String name) {
        this.name = name;
        return this;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeamDTO entity = (TeamDTO) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.players, entity.players) &&
                Objects.equals(this.placements, entity.placements) &&
                Objects.equals(this.name, entity.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, players, placements, name, games);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "players = " + players + ", " +
                "placements = " + placements + ", " +
                "name = " + name + ", " +
                "games = " + games + ")";
    }

    public Set<GameInfoDTO> getGames() {
        return games;
    }

    public TeamDTO setGames(Set<GameInfoDTO> games) {
        this.games = games;
        return this;
    }

    public static class PlayerInfoDTO implements Serializable {
        private UUID id;
        @Pattern(regexp = "^\\p{L}+( \\p{L}+)*$")
        @Length(max = 100)
        private String name;
        private FutsalPositionEnum preferredPosition;

        public PlayerInfoDTO() {
        }

        public PlayerInfoDTO(UUID id, String name, FutsalPositionEnum preferredPosition) {
            this.id = id;
            this.name = name;
            this.preferredPosition = preferredPosition;
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

        public FutsalPositionEnum getPreferredPosition() {
            return preferredPosition;
        }

        public PlayerInfoDTO setPreferredPosition(FutsalPositionEnum preferredPosition) {
            this.preferredPosition = preferredPosition;
            return this;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PlayerInfoDTO entity = (PlayerInfoDTO) o;
            return Objects.equals(this.id, entity.id) &&
                    Objects.equals(this.name, entity.name) &&
                    Objects.equals(this.preferredPosition, entity.preferredPosition);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, name, preferredPosition);
        }

        @Override
        public String toString() {
            return getClass().getSimpleName() + "(" +
                    "id = " + id + ", " +
                    "name = " + name + ", " +
                    "preferredPosition = " + preferredPosition + ")";
        }
    }

    public static class PlacementInfoDTO implements Serializable {
        @NotNull
        private PlacementEnum value = PlacementEnum.PENDING;
        private TournamentDTO tournament;

        public PlacementInfoDTO() {
        }

        public PlacementInfoDTO(PlacementEnum value, TournamentDTO tournament) {
            this.value = value;
            this.tournament = tournament;
        }

        public PlacementEnum getValue() {
            return value;
        }

        public PlacementInfoDTO setValue(PlacementEnum value) {
            this.value = value;
            return this;
        }

        public TournamentDTO getTournament() {
            return tournament;
        }

        public PlacementInfoDTO setTournament(TournamentDTO tournament) {
            this.tournament = tournament;
            return this;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PlacementInfoDTO entity = (PlacementInfoDTO) o;
            return Objects.equals(this.value, entity.value) &&
                    Objects.equals(this.tournament, entity.tournament);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value, tournament);
        }

        @Override
        public String toString() {
            return getClass().getSimpleName() + "(" +
                    "value = " + value + ", " +
                    "tournament = " + tournament + ")";
        }

        public static class TournamentDTO implements Serializable {
            @NotNull
            private UUID id;

            public TournamentDTO() {
            }

            public TournamentDTO(UUID id) {
                this.id = id;
            }

            public UUID getId() {
                return id;
            }

            public TournamentDTO setId(UUID id) {
                this.id = id;
                return this;
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                TournamentDTO entity = (TournamentDTO) o;
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

    public static class GameInfoDTO implements Serializable {
        private UUID id;
        private LocalDateTime happensIn;
        private Boolean isClosed = false;

        public GameInfoDTO() {
        }

        public GameInfoDTO(LocalDateTime deletedAt, UUID id, LocalDateTime happensIn, Boolean isClosed) {
            this.id = id;
            this.happensIn = happensIn;
            this.isClosed = isClosed;
        }

        public UUID getId() {
            return id;
        }

        public void setId(UUID id) {
            this.id = id;
        }

        public LocalDateTime getHappensIn() {
            return happensIn;
        }

        public void setHappensIn(LocalDateTime happensIn) {
            this.happensIn = happensIn;
        }

        public Boolean getIsClosed() {
            return isClosed;
        }

        public void setIsClosed(Boolean isClosed) {
            this.isClosed = isClosed;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GameInfoDTO entity = (GameInfoDTO) o;
            return Objects.equals(this.id, entity.id) &&
                    Objects.equals(this.happensIn, entity.happensIn) &&
                    Objects.equals(this.isClosed, entity.isClosed);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, happensIn, isClosed);
        }

        @Override
        public String toString() {
            return getClass().getSimpleName() + "(" +
                    "id = " + id + ", " +
                    "happensIn = " + happensIn + ", " +
                    "isClosed = " + isClosed + ")";
        }
    }
}
