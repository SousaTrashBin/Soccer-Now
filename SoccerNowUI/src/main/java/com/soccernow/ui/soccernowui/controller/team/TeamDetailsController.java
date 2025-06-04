package com.soccernow.ui.soccernowui.controller.team;

import com.soccernow.ui.soccernowui.dto.user.PlayerDTO;
import com.soccernow.ui.soccernowui.util.FXMLUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;

public class TeamDetailsController {

    @FXML
    private TextField teamNameField;

    @FXML
    private ListView<PlayerDTO> playersOnTeamListView;

    @FXML
    private ListView<PlayerDTO> playersNotOnTeamListView;

    @FXML
    public void initialize() {
    }

    @FXML
    private void onAddPlayerClick() {
    }

    @FXML
    private void onRemoveClick() {
    }

    public void onBackClick(ActionEvent actionEvent) {
        FXMLUtils.switchScene("/com/soccernow/ui/soccernowui/fxml/team/team-list.fxml",
                (Node) actionEvent.getSource());
    }
}
