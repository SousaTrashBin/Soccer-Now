package com.soccernow.ui.soccernowui.controller.tournament;

import com.soccernow.ui.soccernowui.util.FXMLUtils;
import com.soccernow.ui.soccernowui.util.TournamentStatusEnum;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;

public class CreateTournamentController {

    @FXML
    private Button BackButton;

    @FXML
    private TextField tournamentNameField;

    @FXML
    private ComboBox<TournamentStatusEnum> statusComboBox;
    @FXML
    private ListView<String> gameListView;

    private final ObservableList<String> games = FXCollections.observableArrayList();

    @FXML
    public void initialize() {

        statusComboBox.getItems().add(null);
        statusComboBox.getItems().addAll(TournamentStatusEnum.values());
        statusComboBox.setValue(TournamentStatusEnum.OPEN);

        gameListView.setItems(games);
    }


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
