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

public class CreateGameController {

    public List<TeamDTO> availableTeams;

    public ComboBox<TeamDTO> teamOneComboBox;
    private List<PlayerInfoDTO> teamOnePlayers = new ArrayList<>();
    public ComboBox<PlayerInfoDTO> teamOneGoalieComboBox;
    public ComboBox<PlayerInfoDTO> teamOneSweeperComboBox;
    public ComboBox<PlayerInfoDTO> teamOneLeftWingerComboBox;
    public ComboBox<PlayerInfoDTO> teamOneRightWingerComboBox;
    public ComboBox<PlayerInfoDTO> teamOneForwardComboBox;

    public ComboBox<TeamDTO> teamTwoComboBox;
    private List<PlayerInfoDTO> teamTwoPlayers = new ArrayList<>();
    public ComboBox<PlayerInfoDTO> teamTwoGoalieComboBox;
    public ComboBox<PlayerInfoDTO> teamTwoSweeperComboBox;
    public ComboBox<PlayerInfoDTO> teamTwoLeftWingerComboBox;
    public ComboBox<PlayerInfoDTO> teamTwoRightWingerComboBox;
    public ComboBox<PlayerInfoDTO> teamTwoForwardComboBox;

    public ComboBox<RefereeInfoDTO> primaryRefereeComboBox;

    public TableView<RefereeInfoDTO> secondaryRefereesTableView;
    public TableColumn<RefereeInfoDTO, String> secondaryRefereesIdColumn;
    public TableColumn<RefereeInfoDTO, String> secondaryRefereesNameColumn;

    public TableView<RefereeInfoDTO> otherRefereesTableView;
    public TableColumn<RefereeInfoDTO, String> otherRefereesIdColumn;
    public TableColumn<RefereeInfoDTO, String> otherRefereesNameColumn;

    public TextField countryField;
    public TextField cityField;
    public TextField streetField;
    public TextField postalCodeField;

    public DatePicker datePicker;
    public TextField timeField;

    @FXML
    private void initialize() {
        FXMLUtils.executeWithErrorHandling(TeamApiController.INSTANCE::getAllTeams)
                .ifPresent(teams -> availableTeams = teams);
        teamOneComboBox.getItems().setAll(availableTeams);
        teamTwoComboBox.getItems().setAll(availableTeams);

        teamOneComboBox.valueProperty().addListener((obs, oldTeam, newTeam) -> {
            updateTeamTwoOptions(newTeam);
            loadTeamOnePlayers(newTeam);
        });

        teamTwoComboBox.valueProperty().addListener((obs, oldTeam, newTeam) -> {
            updateTeamOneOptions(newTeam);
            loadTeamTwoPlayers(newTeam);
        });

        addTeamOnePlayerSelectionListeners();
        addTeamTwoPlayerSelectionListeners();
    }


    private void addTeamOnePlayerSelectionListeners() {
        teamOneGoalieComboBox.valueProperty().addListener((obs, oldVal, newVal) -> refreshTeamOnePlayerChoices());
        teamOneSweeperComboBox.valueProperty().addListener((obs, oldVal, newVal) -> refreshTeamOnePlayerChoices());
        teamOneLeftWingerComboBox.valueProperty().addListener((obs, oldVal, newVal) -> refreshTeamOnePlayerChoices());
        teamOneRightWingerComboBox.valueProperty().addListener((obs, oldVal, newVal) -> refreshTeamOnePlayerChoices());
        teamOneForwardComboBox.valueProperty().addListener((obs, oldVal, newVal) -> refreshTeamOnePlayerChoices());
    }

    private void refreshTeamOnePlayerChoices() {
        List<PlayerInfoDTO> available = new ArrayList<>(teamOnePlayers);

        PlayerInfoDTO goalie = teamOneGoalieComboBox.getValue();
        PlayerInfoDTO sweeper = teamOneSweeperComboBox.getValue();
        PlayerInfoDTO left = teamOneLeftWingerComboBox.getValue();
        PlayerInfoDTO right = teamOneRightWingerComboBox.getValue();
        PlayerInfoDTO forward = teamOneForwardComboBox.getValue();

        available.remove(goalie);
        available.remove(sweeper);
        available.remove(left);
        available.remove(right);
        available.remove(forward);

        updatePlayerComboBox(teamOneGoalieComboBox, goalie, available);
        updatePlayerComboBox(teamOneSweeperComboBox, sweeper, available);
        updatePlayerComboBox(teamOneLeftWingerComboBox, left, available);
        updatePlayerComboBox(teamOneRightWingerComboBox, right, available);
        updatePlayerComboBox(teamOneForwardComboBox, forward, available);
    }

    private void loadTeamOnePlayers(TeamDTO selectedTeam) {
        if (selectedTeam == null) return;

        teamOnePlayers = new ArrayList<>(selectedTeam.getPlayers());

        teamOneGoalieComboBox.getItems().setAll(teamOnePlayers);
        teamOneSweeperComboBox.getItems().setAll(teamOnePlayers);
        teamOneLeftWingerComboBox.getItems().setAll(teamOnePlayers);
        teamOneRightWingerComboBox.getItems().setAll(teamOnePlayers);
        teamOneForwardComboBox.getItems().setAll(teamOnePlayers);
    }


    private void addTeamTwoPlayerSelectionListeners() {
        teamTwoGoalieComboBox.valueProperty().addListener((obs, oldVal, newVal) -> refreshTeamTwoPlayerChoices());
        teamTwoSweeperComboBox.valueProperty().addListener((obs, oldVal, newVal) -> refreshTeamTwoPlayerChoices());
        teamTwoLeftWingerComboBox.valueProperty().addListener((obs, oldVal, newVal) -> refreshTeamTwoPlayerChoices());
        teamTwoRightWingerComboBox.valueProperty().addListener((obs, oldVal, newVal) -> refreshTeamTwoPlayerChoices());
        teamTwoForwardComboBox.valueProperty().addListener((obs, oldVal, newVal) -> refreshTeamTwoPlayerChoices());
    }

    private void refreshTeamTwoPlayerChoices() {
        List<PlayerInfoDTO> available = new ArrayList<>(teamTwoPlayers);

        PlayerInfoDTO goalie = teamTwoGoalieComboBox.getValue();
        PlayerInfoDTO sweeper = teamTwoSweeperComboBox.getValue();
        PlayerInfoDTO left = teamTwoLeftWingerComboBox.getValue();
        PlayerInfoDTO right = teamTwoRightWingerComboBox.getValue();
        PlayerInfoDTO forward = teamTwoForwardComboBox.getValue();

        available.remove(goalie);
        available.remove(sweeper);
        available.remove(left);
        available.remove(right);
        available.remove(forward);

        updatePlayerComboBox(teamTwoGoalieComboBox, goalie, available);
        updatePlayerComboBox(teamTwoSweeperComboBox, sweeper, available);
        updatePlayerComboBox(teamTwoLeftWingerComboBox, left, available);
        updatePlayerComboBox(teamTwoRightWingerComboBox, right, available);
        updatePlayerComboBox(teamTwoForwardComboBox, forward, available);
    }

    private void loadTeamTwoPlayers(TeamDTO selectedTeam) {
        if (selectedTeam == null) return;

        teamTwoPlayers = new ArrayList<>(selectedTeam.getPlayers());

        teamTwoGoalieComboBox.getItems().setAll(teamTwoPlayers);
        teamTwoSweeperComboBox.getItems().setAll(teamTwoPlayers);
        teamTwoLeftWingerComboBox.getItems().setAll(teamTwoPlayers);
        teamTwoRightWingerComboBox.getItems().setAll(teamTwoPlayers);
        teamTwoForwardComboBox.getItems().setAll(teamTwoPlayers);
    }

    private void updatePlayerComboBox(ComboBox<PlayerInfoDTO> comboBox, PlayerInfoDTO currentSelection, List<PlayerInfoDTO> others) {
        List<PlayerInfoDTO> allOptions = new ArrayList<>(others);
        if (currentSelection != null) {
            allOptions.add(currentSelection);
        }
        comboBox.getItems().setAll(allOptions);
        comboBox.setValue(currentSelection);
    }

    private void updateTeamOneOptions(TeamDTO selectedTeamTwo) {
        List<TeamDTO> filtered = new ArrayList<>(availableTeams);
        filtered.remove(selectedTeamTwo);

        TeamDTO currentSelection = teamOneComboBox.getValue();
        teamOneComboBox.getItems().setAll(filtered);
        if (filtered.contains(currentSelection)) {
            teamOneComboBox.setValue(currentSelection);
        } else {
            teamOneComboBox.setValue(null);
        }
    }

    private void updateTeamTwoOptions(TeamDTO selectedTeamOne) {
        List<TeamDTO> filtered = new ArrayList<>(availableTeams);
        filtered.remove(selectedTeamOne);

        TeamDTO currentSelection = teamTwoComboBox.getValue();
        teamTwoComboBox.getItems().setAll(filtered);
        if (filtered.contains(currentSelection)) {
            teamTwoComboBox.setValue(currentSelection);
        } else {
            teamTwoComboBox.setValue(null);
        }
    }

    @FXML
    public void onCreateGameClick(ActionEvent event) {
        return;
    }

    @FXML
    public void onBackClick(ActionEvent actionEvent) {
        FXMLUtils.switchScene("/com/soccernow/ui/soccernowui/fxml/home/home-screen.fxml",
                (Node) actionEvent.getSource());
    }

    public void onRemoveRefereeClick(ActionEvent actionEvent) {
        return;
    }

    public void onAddRefereeClick(ActionEvent actionEvent) {
        return;
    }
}
