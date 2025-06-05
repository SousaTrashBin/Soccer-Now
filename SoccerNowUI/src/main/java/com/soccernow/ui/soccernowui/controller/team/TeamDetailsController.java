package com.soccernow.ui.soccernowui.controller.team;

import com.soccernow.ui.soccernowui.SoccerNowApp;
import com.soccernow.ui.soccernowui.api.PlayerApiController;
import com.soccernow.ui.soccernowui.api.TeamApiController;
import com.soccernow.ui.soccernowui.dto.TeamDTO;
import com.soccernow.ui.soccernowui.dto.user.PlayerDTO;
import com.soccernow.ui.soccernowui.dto.user.PlayerInfoDTO;
import com.soccernow.ui.soccernowui.util.ErrorException;
import com.soccernow.ui.soccernowui.util.FXMLUtils;
import jakarta.validation.Validator;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TeamDetailsController {
    @FXML
    public TextField teamNameField;

    public Validator validator;
    public TeamDTO teamDTO;

    @FXML
    public TableView<PlayerInfoDTO> teamPlayersTableView;
    @FXML
    public TableColumn<PlayerInfoDTO, String> teamPlayersIdColumn;
    @FXML
    public TableColumn<PlayerInfoDTO,String> teamPlayersNameColumn;

    @FXML
    public TableView<PlayerInfoDTO> otherPlayersTableView;
    @FXML
    public TableColumn<PlayerInfoDTO, String> otherPlayersIdColumn;
    @FXML
    public TableColumn<PlayerInfoDTO,String> otherPlayersNameColumn;

    public List<FXMLUtils.ConsumerWithExceptions> pendingOperations = new ArrayList<>();

    @FXML
    public void initialize() {
        teamPlayersIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId().toString()));
        teamPlayersNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));

        otherPlayersIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId().toString()));
        otherPlayersNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));

        this.validator = SoccerNowApp.getValidatorFactory().getValidator();
    }

    @FXML
    private void onAddPlayerClick() {
        PlayerInfoDTO selectedPlayer = otherPlayersTableView.getSelectionModel().getSelectedItem();
        if (selectedPlayer == null) {
            return;
        }
        pendingOperations.add(() -> TeamApiController.INSTANCE.addPlayerToTeam(teamDTO.getId(),selectedPlayer.getId()));
        this.teamPlayersTableView.getItems().add(selectedPlayer);
        this.otherPlayersTableView.getItems().remove(selectedPlayer);
    }

    @FXML
    public void onRemovePlayerClick(ActionEvent actionEvent) {
        PlayerInfoDTO selectedPlayer = teamPlayersTableView.getSelectionModel().getSelectedItem();
        if (selectedPlayer == null) {
            return;
        }
        pendingOperations.add(() -> TeamApiController.INSTANCE.removePlayerFromTeam(teamDTO.getId(),selectedPlayer.getId()));
        this.teamPlayersTableView.getItems().remove(selectedPlayer);
        this.otherPlayersTableView.getItems().add(selectedPlayer);
    }

    @FXML
    public void onSaveClick(ActionEvent actionEvent) {
        TeamDTO updatedDTO = new TeamDTO();
        updatedDTO.setName(teamNameField.getText());

        boolean isValid = FXMLUtils.validateAndShowAlert(updatedDTO, validator);
        if (!isValid) {
            return;
        }

        for (FXMLUtils.ConsumerWithExceptions pendingOperation : pendingOperations) {
            FXMLUtils.executeWithErrorHandling(pendingOperation);
        }

        FXMLUtils.executeWithErrorHandling(() -> TeamApiController.INSTANCE.updateTeamById(teamDTO.getId(),updatedDTO))
                .ifPresent(savedDTO -> {
                    System.out.printf(savedDTO.toString());
                    FXMLUtils.showSuccess("Team Details Successfully Updated", "Team " + savedDTO.getName() + " successfully updated!");
                });

        FXMLUtils.switchScene("/com/soccernow/ui/soccernowui/fxml/team/team-list.fxml",
                (Node) actionEvent.getSource());
    }

    public void setTeamDTO(TeamDTO teamDTO) {
        this.teamDTO = teamDTO;

        this.teamNameField.setText(teamDTO.getName());

        ObservableList<PlayerInfoDTO> teamPlayersObservable = FXCollections.observableArrayList(teamDTO.getPlayers());
        teamPlayersTableView.setItems(teamPlayersObservable);

        try {
            List<PlayerDTO> allPlayers = PlayerApiController.INSTANCE.getAllPlayers();
            List<PlayerInfoDTO> otherTeams = allPlayers.stream()
                    .map(playerDTO -> new PlayerInfoDTO(playerDTO.getId(), playerDTO.getName()))
                    .filter(playerInfoDTO -> !teamDTO.getPlayers().contains(playerInfoDTO))
                    .toList();

            ObservableList<PlayerInfoDTO> otherTeamsObservable = FXCollections.observableArrayList(otherTeams);
            otherPlayersTableView.setItems(otherTeamsObservable);
        } catch (IOException | ErrorException e) {
            System.err.println("Failed to load teams: " + e.getMessage());
            otherPlayersTableView.setItems(FXCollections.observableArrayList());
        }
    }

    @FXML
    public void onBackClick(ActionEvent actionEvent) {
        FXMLUtils.switchScene("/com/soccernow/ui/soccernowui/fxml/team/team-list.fxml",
                (Node) actionEvent.getSource());
    }

}
