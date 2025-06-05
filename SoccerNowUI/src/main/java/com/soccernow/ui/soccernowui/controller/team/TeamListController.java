package com.soccernow.ui.soccernowui.controller.team;

import com.soccernow.ui.soccernowui.api.RefereeApiController;
import com.soccernow.ui.soccernowui.api.TeamApiController;
import com.soccernow.ui.soccernowui.controller.referee.RefereeDetailsController;
import com.soccernow.ui.soccernowui.dto.TeamDTO;
import com.soccernow.ui.soccernowui.dto.user.PlayerDTO;
import com.soccernow.ui.soccernowui.dto.user.RefereeDTO;
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
import java.util.List;
import java.util.Optional;

public class TeamListController {

    @FXML
    private TableColumn<TeamDTO, String> idColumn;
    @FXML
    private TableColumn<TeamDTO, String> teamNameColumn;
    @FXML
    private TableView<TeamDTO> teamTableView;

    public void initialize() {
        idColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId().toString()));
        teamNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));

        loadTeams();
    }

    @FXML
    private void onAddClick(ActionEvent event) {
        FXMLUtils.switchScene(
                "/com/soccernow/ui/soccernowui/fxml/team/register-team.fxml",
                (Node) event.getSource()
        );
    }

    private void loadTeams() {
        try {
            List<TeamDTO> teams = TeamApiController.INSTANCE.getAllTeams();
            ObservableList<TeamDTO> teamsObservable = FXCollections.observableArrayList(teams);
            teamTableView.setItems(teamsObservable);
        } catch (IOException | ErrorException e) {
            System.err.println("Failed to load teams: " + e.getMessage());
            teamTableView.setItems(FXCollections.observableArrayList());
        }
    }

    @FXML
    private void onEditClick(ActionEvent event) {
        TeamDTO selectedTeam = teamTableView.getSelectionModel().getSelectedItem();
        if (selectedTeam == null) {
            return;
        }
        FXMLUtils.switchScene(
                "/com/soccernow/ui/soccernowui/fxml/team/team-details.fxml",
                (Node) event.getSource(),
                controller -> {
                    if (controller instanceof TeamDetailsController teamDetailsController) {
                        teamDetailsController.setTeamDTO(selectedTeam);
                    }
                });
    }

    @FXML
    private void onRefreshClick(ActionEvent event) {loadTeams();}

    @FXML
    private void onDeleteClick(ActionEvent event) {
        TeamDTO selectedTeam = teamTableView.getSelectionModel().getSelectedItem();
        if (selectedTeam == null) {
            return;
        }

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirm Delete");
        confirmAlert.setHeaderText("Delete Team");
        confirmAlert.setContentText("Are you sure you want to delete " + selectedTeam.getName() + "?");

        Optional<ButtonType> result = confirmAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            FXMLUtils.executeWithErrorHandling(() -> {
                TeamApiController.INSTANCE.deleteTeamById(selectedTeam.getId());
                loadTeams();
            });
        }
    }

    @FXML
    public void onBackClick(ActionEvent actionEvent) {
        FXMLUtils.switchScene("/com/soccernow/ui/soccernowui/fxml/home/home-screen.fxml",
                (Node) actionEvent.getSource());
    }
}
