package com.soccernow.ui.soccernowui.dto;

import com.soccernow.ui.soccernowui.dto.games.GameInfoDTO;
import com.soccernow.ui.soccernowui.dto.tournament.PlacementInfoDTO;
import com.soccernow.ui.soccernowui.dto.user.PlayerInfoDTO;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class TeamDTO implements Serializable {
    private UUID id;
    private Set<PlayerInfoDTO> players = new LinkedHashSet<>();
    private Set<PlacementInfoDTO> placements = new LinkedHashSet<>();
    @Pattern(regexp = "^\\p{L}+( \\p{L}+)*$")
    @Length(max = 100)
    private String name;
    private Set<GameInfoDTO> games = new LinkedHashSet<>();

    public TeamDTO() {
    }

    public TeamDTO(UUID id, Set<PlayerInfoDTO> players, Set<PlacementInfoDTO> placements, String name, Set<GameInfoDTO> games) {
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

    public Set<GameInfoDTO> getGames() {
        return games;
    }

    public TeamDTO setGames(Set<GameInfoDTO> games) {
        this.games = games;
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
                Objects.equals(this.name, entity.name) &&
                Objects.equals(this.games, entity.games);
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
}
