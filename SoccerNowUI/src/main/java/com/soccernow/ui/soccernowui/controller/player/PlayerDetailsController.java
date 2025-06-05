package com.soccernow.ui.soccernowui.controller.player;

import com.soccernow.ui.soccernowui.api.TeamApiController;
import com.soccernow.ui.soccernowui.api.PlayerApiController;
import com.soccernow.ui.soccernowui.dto.TeamInfoDTO;
import com.soccernow.ui.soccernowui.dto.user.PlayerDTO;
import com.soccernow.ui.soccernowui.util.FXMLUtils;
import com.soccernow.ui.soccernowui.util.FutsalPositionEnum;
import jakarta.validation.Validator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;

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
    public TableColumn<TeamInfoDTO,String> playerTeamsNameColumn;

    @FXML
    public TableView<TeamInfoDTO> otherTeamsTableView;
    @FXML
    public TableColumn<TeamInfoDTO, String> otherTeamsIdColumn;
    @FXML
    public TableColumn<TeamInfoDTO,String> otherTeamsNameColumn;

    public List<FXMLUtils.ConsumerWithExceptions> pendingOperations = new ArrayList<>();

    @FXML
    private void onSaveClick() {
        PlayerDTO updatedDTO = new PlayerDTO();
        updatedDTO.setName(playerNameField.getText());
        updatedDTO.setPreferredPosition(positionComboBox.getValue());

        boolean isValid = FXMLUtils.validateAndShowAlert(updatedDTO, validator);
        if (!isValid) {
            return;
        }

        FXMLUtils.executeWithErrorHandling(() -> PlayerApiController.INSTANCE.updatePlayerById(playerDTO.getId(),updatedDTO))
                .ifPresent(savedDTO -> {
                    System.out.printf(savedDTO.toString());
                    FXMLUtils.showSuccess("Player Successfully Updated", "Player " + savedDTO.getName() + " successfully updated!");
                });

        for (FXMLUtils.ConsumerWithExceptions pendingOperation : pendingOperations) {
            FXMLUtils.executeWithErrorHandling(pendingOperation);
        }
    }

    public void onBackClick(ActionEvent actionEvent) {
        FXMLUtils.switchScene("/com/soccernow/ui/soccernowui/fxml/player/player-list.fxml",
                (Node) actionEvent.getSource());
    }

    public void setPlayerDTO(PlayerDTO selectedPlayer) {
        this.playerDTO = selectedPlayer;
        this.playerNameField.setText(selectedPlayer.getName());
        positionComboBox.setValue(this.playerDTO.getPreferredPosition());
    }

    public void onRemoveTeamClick(ActionEvent actionEvent) {
        TeamInfoDTO selectedTeam = playerTeamsTableView.getSelectionModel().getSelectedItem();
        if (selectedTeam == null) {
            return;
        }
        pendingOperations.add(() -> TeamApiController.INSTANCE.removePlayerFromTeam(selectedTeam.getId(),playerDTO.getId()));
        this.playerTeamsTableView.getItems().remove(selectedTeam);
        this.otherTeamsTableView.getItems().add(selectedTeam);
    }

    public void onAddTeamClick(ActionEvent actionEvent) {
        TeamInfoDTO selectedTeam = otherTeamsTableView.getSelectionModel().getSelectedItem();
        if (selectedTeam == null) {
            return;
        }
        pendingOperations.add(() -> TeamApiController.INSTANCE.addPlayerToTeam(selectedTeam.getId(),playerDTO.getId()));
        this.playerTeamsTableView.getItems().add(selectedTeam);
        this.otherTeamsTableView.getItems().remove(selectedTeam);
    }
}
