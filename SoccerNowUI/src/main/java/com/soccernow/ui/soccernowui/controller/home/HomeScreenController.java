package com.soccernow.ui.soccernowui.controller.home;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeScreenController {

    private void navigate(ActionEvent event, String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onViewPlayerClick(ActionEvent event) {
        navigate(event, "/com/soccernow/ui/soccernowui/fxml/player/player-list.fxml");
    }

    @FXML
    private void onViewRefereeClick(ActionEvent event) {
        navigate(event, "/com/soccernow/ui/soccernowui/fxml/referee/referee-list.fxml");
    }

    @FXML
    private void onViewTeamClick(ActionEvent event) {
        navigate(event, "/com/soccernow/ui/soccernowui/fxml/team/team-list.fxml");
    }

    @FXML
    private void onViewGameClick(ActionEvent event) {
        navigate(event, "/com/soccernow/ui/soccernowui/fxml/game/game-list.fxml");
    }

    @FXML
    private void onViewTournamentClick(ActionEvent event) {
        navigate(event, "/com/soccernow/ui/soccernowui/fxml/tournament/tournament-list.fxml");
    }
}
