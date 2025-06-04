package com.soccernow.ui.soccernowui.controller.player;

import com.soccernow.ui.soccernowui.api.PlayerApiController;
import com.soccernow.ui.soccernowui.dto.user.PlayerDTO;
import com.soccernow.ui.soccernowui.util.ErrorException;
import com.soccernow.ui.soccernowui.util.FXMLUtils;
import com.soccernow.ui.soccernowui.util.FutsalPositionEnum;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.soccernow.ui.soccernowui.util.FXMLUtils.executeWithErrorHandling;


public class PlayerListController {

    @FXML
    private TableColumn<PlayerDTO, String> idColumn;
    @FXML
    private TableColumn<PlayerDTO, String> nameColumn;
    @FXML
    private TableColumn<PlayerDTO, String> positionColumn;
    @FXML
    private TableView<PlayerDTO> playerTableView;

    public void initialize() {
        idColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId().toString()));
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        positionColumn.setCellValueFactory(cellData -> {
            FutsalPositionEnum pos = cellData.getValue().getPreferredPosition();
            String posName = pos != null ? pos.name() : "None";
            return new SimpleStringProperty(posName);
        });

        loadPlayers();
    }

    private void loadPlayers() {
        try {
            List<PlayerDTO> players = PlayerApiController.INSTANCE.getAllPlayers();
            ObservableList<PlayerDTO> playersObservable = FXCollections.observableArrayList(players);
            playerTableView.setItems(playersObservable);
        } catch (IOException | ErrorException e) {
            System.err.println("Failed to load players: " + e.getMessage());
            playerTableView.setItems(FXCollections.observableArrayList());
        }
    }

    @FXML
    private void onAddClick(ActionEvent event) {
        FXMLUtils.switchScene(
                "/com/soccernow/ui/soccernowui/fxml/player/register-player.fxml",
                (Node) event.getSource()
        );
    }

    @FXML
    private void onEditClick(ActionEvent event) {}

    @FXML
    private void onRefreshClick(ActionEvent event) {
        loadPlayers();
    }

    @FXML
    private void onDeleteClick(ActionEvent event) {
        PlayerDTO selectedPlayer = playerTableView.getSelectionModel().getSelectedItem();
        if (selectedPlayer == null) {
            return;
        }

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirm Delete");
        confirmAlert.setHeaderText("Delete Player");
        confirmAlert.setContentText("Are you sure you want to delete " + selectedPlayer.getName() + "?");

        Optional<ButtonType> result = confirmAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            FXMLUtils.executeWithErrorHandling(() -> {
                PlayerApiController.INSTANCE.deletePlayerById(selectedPlayer.getId());
                loadPlayers();
            });
        }
    }

    @FXML
    public void onBackClick(ActionEvent actionEvent) {
        FXMLUtils.switchScene("/com/soccernow/ui/soccernowui/fxml/home/home-screen.fxml",
                (Node) actionEvent.getSource());
    }
}