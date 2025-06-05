package com.soccernow.ui.soccernowui.controller.tournament;

import com.soccernow.ui.soccernowui.api.PointTournamentApiController;
import com.soccernow.ui.soccernowui.api.TeamApiController;
import com.soccernow.ui.soccernowui.controller.team.TeamDetailsController;
import com.soccernow.ui.soccernowui.dto.TeamDTO;
import com.soccernow.ui.soccernowui.dto.tournament.PointTournamentDTO;
import com.soccernow.ui.soccernowui.dto.tournament.TournamentDTO;
import com.soccernow.ui.soccernowui.util.ErrorException;
import com.soccernow.ui.soccernowui.util.FXMLUtils;
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

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class TournamentListController {
    @FXML
    public TableView<PointTournamentDTO> tournamentTableView;
    @FXML
    public TableColumn<PointTournamentDTO,String> idColumn;
    @FXML
    public TableColumn<PointTournamentDTO,String> nameColumn;
    @FXML
    public TableColumn<PointTournamentDTO,String> statusColumn;

    public void initialize() {
        idColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId().toString()));
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        statusColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus().toString()));

        loadTournaments();
    }

    @FXML
    private void onAddClick(ActionEvent event) {
        FXMLUtils.switchScene(
                "/com/soccernow/ui/soccernowui/fxml/tournament/create-tournament.fxml",
                (Node) event.getSource()
        );
    }

    private void loadTournaments() {
        try {
            List<PointTournamentDTO> tournaments = PointTournamentApiController.INSTANCE.getAllTournaments();
            ObservableList<PointTournamentDTO> teamsObservable = FXCollections.observableArrayList(tournaments);
            tournamentTableView.setItems(teamsObservable);
        } catch (IOException | ErrorException e) {
            System.err.println("Failed to load tournaments: " + e.getMessage());
            tournamentTableView.setItems(FXCollections.observableArrayList());
        }
    }

    @FXML
    private void onEditClick(ActionEvent event) {
        PointTournamentDTO selectedTournament = tournamentTableView.getSelectionModel().getSelectedItem();
        if (selectedTournament == null) {
            return;
        }
        switch (selectedTournament.getStatus()) {
            case OPEN -> FXMLUtils.switchScene(
                    "/com/soccernow/ui/soccernowui/fxml/tournament/open-tournament-details.fxml",
                    (Node) event.getSource(),
                    controller -> {
                        if (controller instanceof OpenTournamentDetailsController openTournamentDetailsController) {
                            openTournamentDetailsController.setTournamentDTO(selectedTournament);
                        }
                    });
            case IN_PROGRESS -> FXMLUtils.switchScene(
                    "/com/soccernow/ui/soccernowui/fxml/tournament/tournament-in-progress-details.fxml",
                    (Node) event.getSource(),
                    controller -> {
                        if (controller instanceof TournamentInProgressDetailsController tournamentInProgressDetailsController) {
                            tournamentInProgressDetailsController.setTournamentDTO(selectedTournament);
                        }
                    });
        }
    }

    @FXML
    private void onRefreshClick(ActionEvent event) {loadTournaments();}

    @FXML
    private void onDeleteClick(ActionEvent event) {
        PointTournamentDTO selectedTournament = tournamentTableView.getSelectionModel().getSelectedItem();
        if (selectedTournament == null) {
            return;
        }

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirm Delete");
        confirmAlert.setHeaderText("Delete Tournament");
        confirmAlert.setContentText("Are you sure you want to delete " + selectedTournament.getName() + "?");

        Optional<ButtonType> result = confirmAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            FXMLUtils.executeWithErrorHandling(() -> {
                PointTournamentApiController.INSTANCE.deleteTournament(selectedTournament.getId());
                loadTournaments();
            });
        }
    }

    @FXML
    public void onBackClick(ActionEvent actionEvent) {
        FXMLUtils.switchScene("/com/soccernow/ui/soccernowui/fxml/home/home-screen.fxml",
                (Node) actionEvent.getSource());
    }
}
