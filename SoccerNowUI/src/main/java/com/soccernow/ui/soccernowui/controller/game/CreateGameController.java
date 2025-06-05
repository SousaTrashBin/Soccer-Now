package com.soccernow.ui.soccernowui.controller.game;

import com.soccernow.ui.soccernowui.api.TeamApiController;
import com.soccernow.ui.soccernowui.dto.TeamDTO;
import com.soccernow.ui.soccernowui.dto.user.PlayerInfoDTO;
import com.soccernow.ui.soccernowui.dto.user.RefereeInfoDTO;
import com.soccernow.ui.soccernowui.util.FXMLUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CreateGameController {

    private List<TeamDTO> allTeams = new ArrayList<>();

    @FXML private ComboBox<TeamDTO> teamOneComboBox;
    @FXML private ComboBox<PlayerInfoDTO> teamOneGoalieComboBox;
    @FXML private ComboBox<PlayerInfoDTO> teamOneSweeperComboBox;
    @FXML private ComboBox<PlayerInfoDTO> teamOneLeftWingerComboBox;
    @FXML private ComboBox<PlayerInfoDTO> teamOneRightWingerComboBox;
    @FXML private ComboBox<PlayerInfoDTO> teamOneForwardComboBox;
    private List<ComboBox<PlayerInfoDTO>> teamOnePlayerComboBoxes;
    private List<PlayerInfoDTO> teamOnePlayers = new ArrayList<>();

    @FXML private ComboBox<TeamDTO> teamTwoComboBox;
    @FXML private ComboBox<PlayerInfoDTO> teamTwoGoalieComboBox;
    @FXML private ComboBox<PlayerInfoDTO> teamTwoSweeperComboBox;
    @FXML private ComboBox<PlayerInfoDTO> teamTwoLeftWingerComboBox;
    @FXML private ComboBox<PlayerInfoDTO> teamTwoRightWingerComboBox;
    @FXML private ComboBox<PlayerInfoDTO> teamTwoForwardComboBox;
    private List<ComboBox<PlayerInfoDTO>> teamTwoPlayerComboBoxes;
    private List<PlayerInfoDTO> teamTwoPlayers = new ArrayList<>();

    @FXML private ComboBox<RefereeInfoDTO> primaryRefereeComboBox;
    @FXML private TableView<RefereeInfoDTO> secondaryRefereesTableView;
    @FXML private TableColumn<RefereeInfoDTO, String> secondaryRefereesIdColumn;
    @FXML private TableColumn<RefereeInfoDTO, String> secondaryRefereesNameColumn;
    @FXML private TableView<RefereeInfoDTO> otherRefereesTableView;
    @FXML private TableColumn<RefereeInfoDTO, String> otherRefereesIdColumn;
    @FXML private TableColumn<RefereeInfoDTO, String> otherRefereesNameColumn;

    @FXML private TextField countryField;
    @FXML private TextField cityField;
    @FXML private TextField streetField;
    @FXML private TextField postalCodeField;
    @FXML private DatePicker datePicker;
    @FXML private TextField timeField;

    @FXML
    private void initialize() {
        teamOnePlayerComboBoxes = List.of(teamOneGoalieComboBox, teamOneSweeperComboBox, teamOneLeftWingerComboBox, teamOneRightWingerComboBox, teamOneForwardComboBox);
        teamTwoPlayerComboBoxes = List.of(teamTwoGoalieComboBox, teamTwoSweeperComboBox, teamTwoLeftWingerComboBox, teamTwoRightWingerComboBox, teamTwoForwardComboBox);

        FXMLUtils.executeWithErrorHandling(TeamApiController.INSTANCE::getAllTeams)
                .ifPresent(teams -> {
                    this.allTeams = teams;
                    teamOneComboBox.getItems().setAll(allTeams);
                    teamTwoComboBox.getItems().setAll(allTeams);
                });

        teamOneComboBox.valueProperty().addListener((obs, oldTeam, newTeam) -> {
            updateOpponentTeamOptions(newTeam, teamTwoComboBox);
            loadPlayersForTeamOne(newTeam);
        });

        teamTwoComboBox.valueProperty().addListener((obs, oldTeam, newTeam) -> {
            updateOpponentTeamOptions(newTeam, teamOneComboBox);
            loadPlayersForTeamTwo(newTeam);
        });

        addPlayerSelectionListeners(teamOnePlayerComboBoxes, this::updatePlayerOptionsForTeamOne);
        addPlayerSelectionListeners(teamTwoPlayerComboBoxes, this::updatePlayerOptionsForTeamTwo);

        initializeTableColumns();
    }

    private void updateOpponentTeamOptions(TeamDTO selectedTeam, ComboBox<TeamDTO> opponentComboBox) {
        TeamDTO opponentCurrentSelection = opponentComboBox.getValue();
        List<TeamDTO> opponentOptions = new ArrayList<>(allTeams);
        opponentOptions.remove(selectedTeam);

        opponentComboBox.getItems().setAll(opponentOptions);

        if (opponentOptions.contains(opponentCurrentSelection)) {
            opponentComboBox.setValue(opponentCurrentSelection);
        } else {
            opponentComboBox.setValue(null);
        }
    }

    private void loadPlayersForTeamOne(TeamDTO selectedTeam) {
        clearAndResetPlayerUI(teamOnePlayerComboBoxes, teamOnePlayers);
        if (selectedTeam != null) {
            this.teamOnePlayers.addAll(selectedTeam.getPlayers());
            updatePlayerOptionsForTeamOne();
        }
    }

    private void updatePlayerOptionsForTeamOne() {
        updatePlayerOptionsForTeam(teamOnePlayers, teamOnePlayerComboBoxes);
    }

    private void loadPlayersForTeamTwo(TeamDTO selectedTeam) {
        clearAndResetPlayerUI(teamTwoPlayerComboBoxes, teamTwoPlayers);
        if (selectedTeam != null) {
            this.teamTwoPlayers.addAll(selectedTeam.getPlayers());
            updatePlayerOptionsForTeamTwo();
        }
    }

    private void updatePlayerOptionsForTeamTwo() {
        updatePlayerOptionsForTeam(teamTwoPlayers, teamTwoPlayerComboBoxes);
    }

    private void updatePlayerOptionsForTeam(List<PlayerInfoDTO> allPlayersForTeam, List<ComboBox<PlayerInfoDTO>> playerComboBoxes) {
        Set<PlayerInfoDTO> selectedPlayers = playerComboBoxes.stream()
                .map(ComboBox::getValue)
                .filter(java.util.Objects::nonNull)
                .collect(Collectors.toSet());

        for (ComboBox<PlayerInfoDTO> comboBox : playerComboBoxes) {
            PlayerInfoDTO currentSelection = comboBox.getValue();
            List<PlayerInfoDTO> availableOptions = new ArrayList<>(allPlayersForTeam);

            availableOptions.removeAll(selectedPlayers);

            if (currentSelection != null && !availableOptions.contains(currentSelection)) {
                availableOptions.add(currentSelection);
            }

            comboBox.getItems().setAll(availableOptions);
            comboBox.setValue(currentSelection);
        }
    }

    private void addPlayerSelectionListeners(List<ComboBox<PlayerInfoDTO>> comboBoxes, Runnable updateAction) {
        comboBoxes.forEach(cb -> cb.valueProperty().addListener((obs, oldVal, newVal) -> updateAction.run()));
    }

    private void clearAndResetPlayerUI(List<ComboBox<PlayerInfoDTO>> playerComboBoxes, List<PlayerInfoDTO> playerList) {
        playerList.clear();
        playerComboBoxes.forEach(cb -> {
            cb.getItems().clear();
            cb.setValue(null);
        });
    }

    @FXML
    public void onCreateGameClick(ActionEvent event) {
        if (!validateGameForm()) {
            return;
        }
        System.out.println("Create game functionality not yet implemented");
    }

    @FXML
    public void onBackClick(ActionEvent actionEvent) {
        FXMLUtils.switchScene("/com/soccernow/ui/soccernowui/fxml/home/home-screen.fxml",
                (Node) actionEvent.getSource());
    }

    @FXML
    public void onRemoveRefereeClick(ActionEvent actionEvent) {
        RefereeInfoDTO selectedReferee = secondaryRefereesTableView.getSelectionModel().getSelectedItem();
        if (selectedReferee != null) {
            secondaryRefereesTableView.getItems().remove(selectedReferee);
            otherRefereesTableView.getItems().add(selectedReferee);
        } else {
            showAlert("Please select a referee to remove.");
        }
    }

    @FXML
    public void onAddRefereeClick(ActionEvent actionEvent) {
        RefereeInfoDTO selectedReferee = otherRefereesTableView.getSelectionModel().getSelectedItem();
        if (selectedReferee != null) {
            otherRefereesTableView.getItems().remove(selectedReferee);
            secondaryRefereesTableView.getItems().add(selectedReferee);
        } else {
            showAlert("Please select a referee to add.");
        }
    }

    private void initializeTableColumns() {
        secondaryRefereesIdColumn.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getId().toString()));
        secondaryRefereesNameColumn.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getName()));

        otherRefereesIdColumn.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getId().toString()));
        otherRefereesNameColumn.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getName()));
    }

    private boolean validateGameForm() {
        if (teamOneComboBox.getValue() == null) {
            showAlert("Please select Team One.");
            return false;
        }
        if (teamTwoComboBox.getValue() == null) {
            showAlert("Please select Team Two.");
            return false;
        }
        if (datePicker.getValue() == null) {
            showAlert("Please select a date.");
            return false;
        }
        if (timeField.getText() == null || timeField.getText().trim().isEmpty()) {
            showAlert("Please enter a time.");
            return false;
        }
        return true;
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Validation Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}