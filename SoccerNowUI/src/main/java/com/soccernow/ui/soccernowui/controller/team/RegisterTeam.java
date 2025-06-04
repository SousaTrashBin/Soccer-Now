package com.soccernow.ui.soccernowui.controller.team;

import com.soccernow.ui.soccernowui.util.FXMLUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;

public class RegisterTeam {

    @FXML
    private TextField teamNameField;

    @FXML
    private ListView<String> playerListView;

    private final ObservableList<String> players = FXCollections.observableArrayList();


    @FXML
    public void onAddPlayerClick(ActionEvent actionEvent) {
        return;
    }

    @FXML
    public void onRemovePlayerClick(ActionEvent actionEvent) {
        return;
    }

    @FXML
    public void onCancelClick(ActionEvent actionEvent) {
        return;
    }

    @FXML
    public void onRegisterClick(ActionEvent actionEvent) {
        return;
    }

    @FXML
    public void onBackClick(ActionEvent actionEvent) {
        FXMLUtils.switchScene("/com/soccernow/ui/soccernowui/fxml/home/home-screen.fxml",
                (Node) actionEvent.getSource());
    }
}
