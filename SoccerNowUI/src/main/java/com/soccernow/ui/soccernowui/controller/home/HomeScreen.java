package com.soccernow.ui.soccernowui.controller.home;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeScreen {

    @FXML private VBox createOptions;
    @FXML private VBox viewOptions;

    @FXML
    private void onCreateClick() {
        boolean isVisible = createOptions.isVisible();
        createOptions.setVisible(!isVisible);
        createOptions.setManaged(!isVisible);

        viewOptions.setVisible(false);
        viewOptions.setManaged(false);
    }

    @FXML
    private void onViewClick() {
        boolean isVisible = viewOptions.isVisible();
        viewOptions.setVisible(!isVisible);
        viewOptions.setManaged(!isVisible);

        createOptions.setVisible(false);
        createOptions.setManaged(false);
    }

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
    private void onRegisterPlayerClick(ActionEvent event) {
        navigate(event, "/com/soccernow/ui/soccernowui/fxml/player/register-player.fxml");
    }

    @FXML
    private void onRegisterRefereeClick(ActionEvent event) {
        navigate(event, "/com/soccernow/ui/soccernowui/fxml/referee/register-referee.fxml");
    }

    @FXML
    private void onRegisterTeamClick(ActionEvent event) {
        navigate(event, "/com/soccernow/ui/soccernowui/fxml/team/register-team.fxml");
    }

    @FXML
    private void onCreateGameClick(ActionEvent event) {
        navigate(event, "/com/soccernow/ui/soccernowui/fxml/game/create-game.fxml");
    }

    @FXML
    private void onCreateTournamentClick(ActionEvent event) {
        navigate(event, "/com/soccernow/ui/soccernowui/fxml/tournament/create-tournament.fxml");
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
        navigate(event, "/com/soccernow/ui/soccernowui/fxml/tournament/tournament-details-list.fxml");
    }
}
