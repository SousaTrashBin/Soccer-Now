package com.soccernow.ui.soccernowui.dto.tournament;

import com.soccernow.ui.soccernowui.util.PlacementEnum;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Objects;

public class PlacementInfoDTO implements Serializable {
    @NotNull
    private PlacementEnum value = PlacementEnum.PENDING;
    private TournamentInfoDTO tournament;

    public PlacementInfoDTO() {
    }

    public PlacementInfoDTO(PlacementEnum value, TournamentInfoDTO tournament) {
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

    public TournamentInfoDTO getTournament() {
        return tournament;
    }

    public PlacementInfoDTO setTournament(TournamentInfoDTO tournament) {
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
}
