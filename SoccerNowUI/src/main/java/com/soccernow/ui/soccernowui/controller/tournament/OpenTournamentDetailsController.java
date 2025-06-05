package com.soccernow.ui.soccernowui.controller.tournament;

import com.soccernow.ui.soccernowui.dto.TeamDTO;
import com.soccernow.ui.soccernowui.util.FXMLUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;

public class OpenTournamentDetailsController {

    @FXML
    private ListView<TeamDTO> participatingTeamsListView;

    @FXML
    private ListView<TeamDTO> availableTeamsListView;

    @FXML
    public void initialize() {
    }

    @FXML
    private void onRemoveTeamClick() {
    }

    @FXML
    private void onAddTeamClick() {
    }

    @FXML
    private void onStartTournamentClick() {
    }

    @FXML
    public void onBackClick(ActionEvent actionEvent) {
        FXMLUtils.switchScene("/com/soccernow/ui/soccernowui/fxml/tournament/tournament-list.fxml",
                (Node) actionEvent.getSource());
    }

}
