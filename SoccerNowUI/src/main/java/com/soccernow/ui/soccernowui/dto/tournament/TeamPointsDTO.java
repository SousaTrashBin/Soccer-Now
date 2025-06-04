package com.soccernow.ui.soccernowui.dto.tournament;

import jakarta.validation.constraints.NotNull;
import com.soccernow.ui.soccernowui.dto.TeamInfoDTO;

import java.io.Serializable;
import java.util.Objects;

public class TeamPointsDTO implements Serializable {
    @NotNull
    private Integer currentPoints = 0;
    @NotNull
    private TeamInfoDTO team;

    public TeamPointsDTO() {
    }

    public TeamPointsDTO(Integer currentPoints, TeamInfoDTO team) {
        this.currentPoints = currentPoints;
        this.team = team;
    }

    public Integer getCurrentPoints() {
        return currentPoints;
    }

    public TeamPointsDTO setCurrentPoints(Integer currentPoints) {
        this.currentPoints = currentPoints;
        return this;
    }

    public TeamInfoDTO getTeam() {
        return team;
    }

    public TeamPointsDTO setTeam(TeamInfoDTO team) {
        this.team = team;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeamPointsDTO entity = (TeamPointsDTO) o;
        return Objects.equals(this.currentPoints, entity.currentPoints) &&
                Objects.equals(this.team, entity.team);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currentPoints, team);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "currentPoints = " + currentPoints + ", " +
                "team = " + team + ")";
    }
}
