package com.soccernow.ui.soccernowui.controller.game;

import com.soccernow.ui.soccernowui.util.FXMLUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.VBox;


public class GameListController {

    @FXML
    private VBox createGameVBox;

    @FXML
    private void onAddClick(ActionEvent event) {
        FXMLUtils.switchScene(
                "/com/soccernow/ui/soccernowui/fxml/game/create-game.fxml",
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
