package com.soccernow.ui.soccernowui.controller.tournament;

import com.soccernow.ui.soccernowui.dto.games.GameDTO;
import com.soccernow.ui.soccernowui.util.FXMLUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;

public class TournamentInProgressDetailsController {

    @FXML
    private TableView<GameDTO> tournamentGamesTableView;

    @FXML
    private TableView<GameDTO> otherGamesTableView;

    @FXML
    public void initialize() {
    }

    @FXML
    private void onCancelGameClick() {
    }

    @FXML
    private void onRemoveGameClick() {
    }

    @FXML
    private void onAddGameClick() {
    }

    @FXML
    private void onEndTournamentClick() {
    }

    @FXML
    public void onBackClick(ActionEvent actionEvent) {
        FXMLUtils.switchScene("/com/soccernow/ui/soccernowui/fxml/tournament/tournament-list.fxml",
                (Node) actionEvent.getSource());
    }

}
