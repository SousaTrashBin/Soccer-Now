package com.soccernow.ui.soccernowui.controller.tournament;

import com.soccernow.ui.soccernowui.util.FXMLUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;

public class CreateTournament {

    @FXML
    public void onAddGameClick(ActionEvent event) {
        return;
    }

    @FXML
    public void onRemoveGameClick(ActionEvent event) {
        return;
    }

    @FXML
    public void onCancelClick(ActionEvent event) {
        return;
    }

    @FXML
    public void onCreateClick(ActionEvent event) {
        return;
    }

    @FXML
    public void onBackClick(ActionEvent actionEvent) {
        FXMLUtils.switchScene("/com/soccernow/ui/soccernowui/fxml/home/home-screen.fxml",
                (Node) actionEvent.getSource());
    }
}
