package com.soccernow.ui.soccernowui.controller.player;

import com.soccernow.ui.soccernowui.SoccerNowApp;
import com.soccernow.ui.soccernowui.api.PlayerApiController;
import com.soccernow.ui.soccernowui.api.TeamApiController;
import com.soccernow.ui.soccernowui.dto.TeamDTO;
import com.soccernow.ui.soccernowui.dto.TeamInfoDTO;
import com.soccernow.ui.soccernowui.dto.user.PlayerDTO;
import com.soccernow.ui.soccernowui.util.ErrorException;
import com.soccernow.ui.soccernowui.util.FXMLUtils;
import com.soccernow.ui.soccernowui.util.FutsalPositionEnum;
import jakarta.validation.Validator;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlayerDetailsController {
    @FXML
    public TextField playerNameField;

    @FXML
    public ComboBox<FutsalPositionEnum> positionComboBox;
    public Validator validator;
    public PlayerDTO playerDTO;

    @FXML
    public TableView<TeamInfoDTO> playerTeamsTableView;
    @FXML
    public TableColumn<TeamInfoDTO, String> playerTeamsIdColumn;
    @FXML
    public TableColumn<TeamInfoDTO, String> playerTeamsNameColumn;

    @FXML
    public TableView<TeamInfoDTO> otherTeamsTableView;
    @FXML
    public TableColumn<TeamInfoDTO, String> otherTeamsIdColumn;
    @FXML
    public TableColumn<TeamInfoDTO, String> otherTeamsNameColumn;

    public List<FXMLUtils.ConsumerWithExceptions> pendingOperations = new ArrayList<>();

    public void initialize() {
        positionComboBox.getItems().add(null);
        positionComboBox.getItems().addAll(FutsalPositionEnum.values());

        playerTeamsIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId().toString()));
        playerTeamsNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));

        otherTeamsIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId().toString()));
        otherTeamsNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));

        this.validator = SoccerNowApp.getValidatorFactory().getValidator();
    }

    public void onSaveClick(ActionEvent actionEvent) {
        PlayerDTO updatedDTO = new PlayerDTO();
        updatedDTO.setName(playerNameField.getText());
        updatedDTO.setPreferredPosition(positionComboBox.getValue());

        boolean isValid = FXMLUtils.validateAndShowAlert(updatedDTO, validator);
        if (!isValid) {
            return;
        }

        for (FXMLUtils.ConsumerWithExceptions pendingOperation : pendingOperations) {
            FXMLUtils.executeWithErrorHandling(pendingOperation);
        }

        FXMLUtils.executeWithErrorHandling(() -> PlayerApiController.INSTANCE.updatePlayerById(playerDTO.getId(), updatedDTO))
                .ifPresent(savedDTO -> {
                    System.out.printf(savedDTO.toString());
                    FXMLUtils.showSuccess("Player Details Successfully Updated", "Player " + savedDTO.getName() + " successfully updated!");
                    FXMLUtils.switchScene("/com/soccernow/ui/soccernowui/fxml/player/player-list.fxml",
                            (Node) actionEvent.getSource());
                });
    }

    public void onBackClick(ActionEvent actionEvent) {
        FXMLUtils.switchScene("/com/soccernow/ui/soccernowui/fxml/player/player-list.fxml",
                (Node) actionEvent.getSource());
    }

    public void setPlayerDTO(PlayerDTO playerDTO) {
        this.playerDTO = playerDTO;

        this.playerNameField.setText(playerDTO.getName());
        this.positionComboBox.setValue(playerDTO.getPreferredPosition());

        ObservableList<TeamInfoDTO> playerTeamsObservable = FXCollections.observableArrayList(playerDTO.getTeams());
        playerTeamsTableView.setItems(playerTeamsObservable);

        try {
            List<TeamDTO> allTeams = TeamApiController.INSTANCE.getAllTeams();
            List<TeamInfoDTO> otherTeams = allTeams.stream()
                    .map(team -> new TeamInfoDTO(team.getId(), team.getName()))
                    .filter(teamInfoDTO -> !playerDTO.getTeams().contains(teamInfoDTO))
                    .toList();

            ObservableList<TeamInfoDTO> otherTeamsObservable = FXCollections.observableArrayList(otherTeams);
            otherTeamsTableView.setItems(otherTeamsObservable);
        } catch (IOException | ErrorException e) {
            System.err.println("Failed to load teams: " + e.getMessage());
            otherTeamsTableView.setItems(FXCollections.observableArrayList());
        }
    }

    public void onRemoveTeamClick(ActionEvent actionEvent) {
        TeamInfoDTO selectedTeam = playerTeamsTableView.getSelectionModel().getSelectedItem();
        if (selectedTeam == null) {
            return;
        }
        pendingOperations.add(() -> TeamApiController.INSTANCE.removePlayerFromTeam(selectedTeam.getId(), playerDTO.getId()));
        this.playerTeamsTableView.getItems().remove(selectedTeam);
        this.otherTeamsTableView.getItems().add(selectedTeam);
    }

    public void onAddTeamClick(ActionEvent actionEvent) {
        TeamInfoDTO selectedTeam = otherTeamsTableView.getSelectionModel().getSelectedItem();
        if (selectedTeam == null) {
            return;
        }
        pendingOperations.add(() -> TeamApiController.INSTANCE.addPlayerToTeam(selectedTeam.getId(), playerDTO.getId()));
        this.playerTeamsTableView.getItems().add(selectedTeam);
        this.otherTeamsTableView.getItems().remove(selectedTeam);
    }
}
