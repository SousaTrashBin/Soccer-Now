package com.soccernow.ui.soccernowui.controller.tournament;

import com.soccernow.ui.soccernowui.util.FXMLUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;

public class TournamentListController {
    @FXML
    private void onAddClick(ActionEvent event) {
        FXMLUtils.switchScene(
                "/com/soccernow/ui/soccernowui/fxml/game/create-tournament.fxml",
                (Node) event.getSource()
        );
    }

    @FXML
    private void onEditClick(ActionEvent event) {}

    @FXML
    private void onRefreshClick(ActionEvent event) {}

    @FXML
    private void onDeleteClick(ActionEvent event) {}

    @FXML
    public void onBackClick(ActionEvent actionEvent) {
        FXMLUtils.switchScene("/com/soccernow/ui/soccernowui/fxml/home/home-screen.fxml",
                (Node) actionEvent.getSource());
    }
}
