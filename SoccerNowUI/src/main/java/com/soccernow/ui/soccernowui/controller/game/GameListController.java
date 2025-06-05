package com.soccernow.ui.soccernowui.controller.game;

import com.soccernow.ui.soccernowui.api.GameApiController;
import com.soccernow.ui.soccernowui.api.PlayerApiController;
import com.soccernow.ui.soccernowui.controller.player.PlayerDetailsController;
import com.soccernow.ui.soccernowui.dto.games.GameDTO;
import com.soccernow.ui.soccernowui.dto.user.PlayerDTO;
import com.soccernow.ui.soccernowui.util.ErrorException;
import com.soccernow.ui.soccernowui.util.FXMLUtils;
import com.soccernow.ui.soccernowui.util.FutsalPositionEnum;
import com.soccernow.ui.soccernowui.util.GameStatusEnum;
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
import java.util.List;


public class GameListController {

    @FXML
    public TableView<GameDTO> gameTableView;
    @FXML
    public TableColumn<GameDTO,String> idColumn;
    @FXML
    public TableColumn<GameDTO,String> teamOneColumn;
    @FXML
    public TableColumn<GameDTO,String> teamTwoColumn;
    @FXML
    public TableColumn<GameDTO,String> statusColumn;

    public void initialize() {
        idColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId().toString()));
        teamOneColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGameTeamOne().getTeam().getId().toString()));
        teamTwoColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGameTeamTwo().getTeam().getId().toString()));
        statusColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus().toString()));

        loadGames();
    }

    @FXML
    private void onAddClick(ActionEvent event) {
        FXMLUtils.switchScene(
                "/com/soccernow/ui/soccernowui/fxml/game/create-game.fxml",
                (Node) event.getSource()
        );
    }

    @FXML
    private void onEditClick(ActionEvent event) {
        GameDTO selectedGame = gameTableView.getSelectionModel().getSelectedItem();
        if (selectedGame == null || selectedGame.getStatus() != GameStatusEnum.OPENED) {
            return;
        }
        FXMLUtils.switchScene(
                "/com/soccernow/ui/soccernowui/fxml/game/register-game-results.fxml",
                (Node) event.getSource(),
                controller -> {
                    if (controller instanceof RegisterGameResultsController registerGameResultsController) {
                        //registerGameResultsController.setGameDTO(selectedGame);
                    }
                });
    }

    @FXML
    private void onRefreshClick(ActionEvent event) {}

    public void loadGames(){
        try {
            List<GameDTO> games = GameApiController.INSTANCE.getAllGames();
            ObservableList<GameDTO> gamesObservable = FXCollections.observableArrayList(games);
            gameTableView.setItems(gamesObservable);
        } catch (IOException | ErrorException e) {
            System.err.println("Failed to load games: " + e.getMessage());
            gameTableView.setItems(FXCollections.observableArrayList());
        }
    }

    @FXML
    public void onBackClick(ActionEvent actionEvent) {
        FXMLUtils.switchScene("/com/soccernow/ui/soccernowui/fxml/home/home-screen.fxml",
                (Node) actionEvent.getSource());
    }
}
