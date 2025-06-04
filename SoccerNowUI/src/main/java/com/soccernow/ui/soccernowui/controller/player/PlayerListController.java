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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class PlayerListController {

    @FXML
    private VBox createPlayerVBox;

    @FXML
    private TableColumn<PlayerDTO, String> nameColumn;

    @FXML
    private TableColumn<PlayerDTO, String> positionColumn;

    @FXML
    private TableView<PlayerDTO> playerTableView;

    public void initialize() {
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));

        positionColumn.setCellValueFactory(cellData -> {
            FutsalPositionEnum pos = cellData.getValue().getPreferredPosition();
            String posName = pos != null ? pos.name() : "None";
            return new SimpleStringProperty(posName);
        });

        List<PlayerDTO> players;
        try {
            players = PlayerApiController.INSTANCE.getAllPlayers();
        } catch (IOException | ErrorException e) {
            players = new ArrayList<>();
        }
        ObservableList<PlayerDTO> playersObservable = FXCollections.observableArrayList(players);
        playerTableView.setItems(playersObservable);
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
    private void onRefreshClick(ActionEvent event) {}

    @FXML
    private void onDeleteClick(ActionEvent event) {}

    @FXML
    public void onBackClick(ActionEvent actionEvent) {
        FXMLUtils.switchScene("/com/soccernow/ui/soccernowui/fxml/home/home-screen.fxml",
                (Node) actionEvent.getSource());
    }
}