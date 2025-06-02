package com.soccernow.ui.soccernowui.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;
import pt.ul.fc.css.soccernow.domain.dto.games.CardInfoDTO;
import pt.ul.fc.css.soccernow.domain.dto.games.GameInfoDTO;

import java.io.Serializable;
import java.util.*;

/**
 * DTO for {@link pt.ul.fc.css.soccernow.domain.entities.user.Referee}
 */
public class RefereeDTO implements Serializable {
    private Set<GameInfoDTO> secondaryRefereeGames = new HashSet<>();
    private UUID id;
    @Schema(example = "Sofia Reia")
    @Pattern(regexp = "^\\p{L}+( \\p{L}+)*$")
    @Length(max = 100)
    private String name;
    @NotNull
    private Boolean hasCertificate = false;
    private Set<GameInfoDTO> primaryRefereeGames = new HashSet<>();
    private Set<CardInfoDTO> issuedCards = new LinkedHashSet<>();

    public RefereeDTO() {
    }

    public RefereeDTO(
            Set<GameInfoDTO> secondaryRefereeGames, UUID id, String name, Boolean hasCertificate, Set<GameInfoDTO> primaryRefereeGames, Set<CardInfoDTO> issuedCards) {
        this.id = id;
        this.name = name;
        this.hasCertificate = hasCertificate;
        this.primaryRefereeGames = primaryRefereeGames;
        this.issuedCards = issuedCards;
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

    public Set<CardInfoDTO> getIssuedCards() {
        return issuedCards;
    }

    public RefereeDTO setIssuedCards(Set<CardInfoDTO> issuedCards) {
        this.issuedCards = issuedCards;
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
                Objects.equals(this.issuedCards, entity.issuedCards);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, hasCertificate, primaryRefereeGames, issuedCards);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "name = " + name + ", " +
                "hasCertificate = " + hasCertificate + ", " +
                "primaryRefereeGames = " + primaryRefereeGames + ", " +
                "issuedCards = " + issuedCards + ")";
    }

    public Set<GameInfoDTO> getSecondaryRefereeGames() {
        return secondaryRefereeGames;
    }

    public RefereeDTO setSecondaryRefereeGames(Set<GameInfoDTO> secondaryRefereeGames) {
        this.secondaryRefereeGames = secondaryRefereeGames;
        return this;
    }
}
