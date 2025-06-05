package com.soccernow.ui.soccernowui.controller.tournament;

import com.soccernow.ui.soccernowui.api.GameApiController;
import com.soccernow.ui.soccernowui.api.PointTournamentApiController;
import com.soccernow.ui.soccernowui.dto.games.GameDTO;
import com.soccernow.ui.soccernowui.dto.games.GameInfoDTO;
import com.soccernow.ui.soccernowui.dto.tournament.TournamentDTO;
import com.soccernow.ui.soccernowui.util.ErrorException;
import com.soccernow.ui.soccernowui.util.FXMLUtils;
import com.soccernow.ui.soccernowui.util.GameStatusEnum;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TournamentInProgressDetailsController {

    @FXML
    public TableColumn<GameInfoDTO, String> tournamentGamesIdColumn;
    @FXML
    public TableColumn<GameInfoDTO, String> tournamentGamesStatusColumn;
    @FXML
    public TableColumn<GameInfoDTO, String> otherGamesIdColumn;
    @FXML
    public TableColumn<GameInfoDTO, String> otherGamesStatusColumn;
    public List<FXMLUtils.ConsumerWithExceptions> pendingOperations = new ArrayList<>();
    @FXML
    private TableView<GameInfoDTO> tournamentGamesTableView;
    @FXML
    private TableView<GameInfoDTO> otherGamesTableView;
    private TournamentDTO tournamentDTO;

    @FXML
    public void initialize() {
        tournamentGamesIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId().toString()));
        tournamentGamesStatusColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus().toString()));

        otherGamesIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId().toString()));
        otherGamesStatusColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus().toString()));
    }

    @FXML
    private void onCancelGameClick() {
        GameInfoDTO selectedGame = tournamentGamesTableView.getSelectionModel().getSelectedItem();
        if (selectedGame == null || selectedGame.getStatus() == GameStatusEnum.CLOSED || selectedGame.getStatus() == GameStatusEnum.CANCELLED) {
            return;
        }
        pendingOperations.add(() -> GameApiController.INSTANCE.cancelGameById(selectedGame.getId()));
    }

    @FXML
    private void onRemoveGameClick() {
        GameInfoDTO selectedGame = tournamentGamesTableView.getSelectionModel().getSelectedItem();
        if (selectedGame == null || selectedGame.getStatus() == GameStatusEnum.CLOSED || selectedGame.getStatus() == GameStatusEnum.CANCELLED) {
            return;
        }
        pendingOperations.add(() -> PointTournamentApiController.INSTANCE.removeGameFromTournament(tournamentDTO.getId(), selectedGame.getId()));
        this.tournamentGamesTableView.getItems().remove(selectedGame);
        this.otherGamesTableView.getItems().add(selectedGame);
    }

    @FXML
    private void onAddGameClick() {
        GameInfoDTO selectedGame = otherGamesTableView.getSelectionModel().getSelectedItem();
        if (selectedGame == null || selectedGame.getStatus() == GameStatusEnum.CLOSED || selectedGame.getStatus() == GameStatusEnum.CANCELLED) {
            return;
        }
        pendingOperations.add(() -> PointTournamentApiController.INSTANCE.addGameToTournament(tournamentDTO.getId(), selectedGame.getId()));
        this.otherGamesTableView.getItems().remove(selectedGame);
        this.tournamentGamesTableView.getItems().add(selectedGame);
    }

    @FXML
    public void onSaveClick(ActionEvent actionEvent) {
        for (FXMLUtils.ConsumerWithExceptions pendingOperation : pendingOperations) {
            FXMLUtils.executeWithErrorHandling(pendingOperation);
        }

        FXMLUtils.switchScene("/com/soccernow/ui/soccernowui/fxml/tournament/tournament-list.fxml",
                (Node) actionEvent.getSource());
    }

    @FXML
    private void onEndTournamentClick(ActionEvent actionEvent) {
        for (FXMLUtils.ConsumerWithExceptions pendingOperation : pendingOperations) {
            FXMLUtils.executeWithErrorHandling(pendingOperation);
        }

        FXMLUtils.executeWithErrorHandling(() -> PointTournamentApiController.INSTANCE.endTournament(tournamentDTO.getId()))
                .ifPresent(savedDTO -> {
                    System.out.printf(savedDTO.toString());
                    FXMLUtils.showSuccess("Tournament Successfully Ended", "Tournament " + savedDTO.getName() + " ended successfully!");
                    FXMLUtils.switchScene("/com/soccernow/ui/soccernowui/fxml/tournament/tournament-list.fxml",
                            (Node) actionEvent.getSource());
                });
    }

    @FXML
    public void onBackClick(ActionEvent actionEvent) {
        FXMLUtils.switchScene("/com/soccernow/ui/soccernowui/fxml/tournament/tournament-list.fxml",
                (Node) actionEvent.getSource());
    }

    public void setTournamentDTO(TournamentDTO selectedTournament) {
        this.tournamentDTO = selectedTournament;

        ObservableList<GameInfoDTO> tournamentGamesObservable = FXCollections.observableArrayList(tournamentDTO.getGames());
        tournamentGamesTableView.setItems(tournamentGamesObservable);

        try {
            List<GameDTO> allGames = GameApiController.INSTANCE.getAllGames();
            List<GameInfoDTO> otherGames = allGames.stream()
                    .map(gameDTO -> new GameInfoDTO(gameDTO.getId(), gameDTO.getLocatedIn(), gameDTO.getHappensIn(), gameDTO.getStatus(), gameDTO.getTournament()))
                    .filter(gameInfoDTO -> !tournamentDTO.getGames().contains(gameInfoDTO))
                    .toList();

            ObservableList<GameInfoDTO> otherGamesObservable = FXCollections.observableArrayList(otherGames);
            otherGamesTableView.setItems(otherGamesObservable);
        } catch (IOException | ErrorException e) {
            System.err.println("Failed to load teams: " + e.getMessage());
            otherGamesTableView.setItems(FXCollections.observableArrayList());
        }
    }
}
