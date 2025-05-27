package com.soccernow.ui.soccernowui.dto.user;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class RefereeDTO implements Serializable {
    private UUID id;
    @Pattern(regexp = "^\\p{L}+( \\p{L}+)*$")
    @Length(max = 100)
    private String name;
    @NotNull
    private Boolean hasCertificate = false;
    private Set<GameInfoDTO> primaryRefereeGames = new HashSet<>();
    private Set<GameInfoDTO> secondaryRefereeGames = new HashSet<>();

    public RefereeDTO() {
    }

    public RefereeDTO(UUID id, String name, Boolean hasCertificate, Set<GameInfoDTO> primaryRefereeGames, Set<GameInfoDTO> secondaryRefereeGames) {
        this.id = id;
        this.name = name;
        this.hasCertificate = hasCertificate;
        this.primaryRefereeGames = primaryRefereeGames;
        this.secondaryRefereeGames = secondaryRefereeGames;
    }

    public UUID getId() {
        return id;
    }

    public RefereeDTO setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public RefereeDTO setName(String name) {
        this.name = name;
        return this;
    }

    public Boolean getHasCertificate() {
        return hasCertificate;
    }

    public RefereeDTO setHasCertificate(Boolean hasCertificate) {
        this.hasCertificate = hasCertificate;
        return this;
    }

    public Set<GameInfoDTO> getPrimaryRefereeGames() {
        return primaryRefereeGames;
    }

    public RefereeDTO setPrimaryRefereeGames(Set<GameInfoDTO> primaryRefereeGames) {
        this.primaryRefereeGames = primaryRefereeGames;
        return this;
    }

    public Set<GameInfoDTO> getSecondaryRefereeGames() {
        return secondaryRefereeGames;
    }

    public RefereeDTO setSecondaryRefereeGames(Set<GameInfoDTO> secondaryRefereeGames) {
        this.secondaryRefereeGames = secondaryRefereeGames;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RefereeDTO entity = (RefereeDTO) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.name, entity.name) &&
                Objects.equals(this.hasCertificate, entity.hasCertificate) &&
                Objects.equals(this.primaryRefereeGames, entity.primaryRefereeGames) &&
                Objects.equals(this.secondaryRefereeGames, entity.secondaryRefereeGames);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, hasCertificate, primaryRefereeGames, secondaryRefereeGames);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "name = " + name + ", " +
                "hasCertificate = " + hasCertificate + ", " +
                "primaryRefereeGames = " + primaryRefereeGames + ", " +
                "secondaryRefereeGames = " + secondaryRefereeGames + ")";
    }

    public static class GameInfoDTO implements Serializable {
        private UUID id;
        private LocalDateTime happensIn;
        private Boolean isClosed = false;

        public GameInfoDTO() {
        }

        public GameInfoDTO(UUID id, LocalDateTime happensIn, Boolean isClosed) {
            this.id = id;
            this.happensIn = happensIn;
            this.isClosed = isClosed;
        }

        public UUID getId() {
            return id;
        }

        public GameInfoDTO setId(UUID id) {
            this.id = id;
            return this;
        }

        public LocalDateTime getHappensIn() {
            return happensIn;
        }

        public GameInfoDTO setHappensIn(LocalDateTime happensIn) {
            this.happensIn = happensIn;
            return this;
        }

        public Boolean getIsClosed() {
            return isClosed;
        }

        public GameInfoDTO setIsClosed(Boolean isClosed) {
            this.isClosed = isClosed;
            return this;
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
