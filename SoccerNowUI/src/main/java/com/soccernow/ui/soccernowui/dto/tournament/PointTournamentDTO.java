package com.soccernow.ui.soccernowui.dto.tournament;

import java.util.List;

public class PointTournamentDTO extends TournamentDTO {
    private List<TeamPointsDTO> teamPoints;

    public List<TeamPointsDTO> getTeamPoints() {
        return teamPoints;
    }

    public void setTeamPoints(List<TeamPointsDTO> teamPoints) {
        this.teamPoints = teamPoints;
    }
}
