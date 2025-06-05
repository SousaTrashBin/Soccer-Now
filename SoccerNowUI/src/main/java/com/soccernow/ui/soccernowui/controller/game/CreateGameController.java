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
    private List<TeamDTO> availableTeams = new ArrayList<>();

    @FXML private ComboBox<TeamDTO> teamOneComboBox;
    @FXML private ComboBox<PlayerInfoDTO> teamOneGoalieComboBox;
    @FXML private ComboBox<PlayerInfoDTO> teamOneSweeperComboBox;
    @FXML private ComboBox<PlayerInfoDTO> teamOneLeftWingerComboBox;
    @FXML private ComboBox<PlayerInfoDTO> teamOneRightWingerComboBox;
    @FXML private ComboBox<PlayerInfoDTO> teamOneForwardComboBox;
    private List<ComboBox<PlayerInfoDTO>> teamOnePlayerComboBoxes;
    private List<PlayerInfoDTO> teamOnePlayers = new ArrayList<>();
    private List<PlayerInfoDTO> availableTeamOnePlayers = new ArrayList<>();

    @FXML private ComboBox<TeamDTO> teamTwoComboBox;
    @FXML private ComboBox<PlayerInfoDTO> teamTwoGoalieComboBox;
    @FXML private ComboBox<PlayerInfoDTO> teamTwoSweeperComboBox;
    @FXML private ComboBox<PlayerInfoDTO> teamTwoLeftWingerComboBox;
    @FXML private ComboBox<PlayerInfoDTO> teamTwoRightWingerComboBox;
    @FXML private ComboBox<PlayerInfoDTO> teamTwoForwardComboBox;
    private List<ComboBox<PlayerInfoDTO>> teamTwoPlayerComboBoxes;
    private List<PlayerInfoDTO> teamTwoPlayers = new ArrayList<>();
    private List<PlayerInfoDTO> availableTeamTwoPlayers = new ArrayList<>();

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
                    this.allTeams = new ArrayList<>(teams);
                    this.availableTeams = new ArrayList<>(teams);
                    updateTeamComboBoxes();
                });

        teamOneComboBox.valueProperty().addListener((obs, oldTeam, newTeam) -> {
            handleTeamOneSelection(oldTeam, newTeam);
        });

        teamTwoComboBox.valueProperty().addListener((obs, oldTeam, newTeam) -> {
            handleTeamTwoSelection(oldTeam, newTeam);
        });

        setupPlayerSelectionListeners(teamOnePlayerComboBoxes, availableTeamOnePlayers);

        setupPlayerSelectionListeners(teamTwoPlayerComboBoxes, availableTeamTwoPlayers);

        initializeTableColumns();
    }

    private void handleTeamOneSelection(TeamDTO oldTeam, TeamDTO newTeam) {
        clearPlayerSelections(teamOnePlayerComboBoxes);

        if (oldTeam != null) {
            availableTeams.add(oldTeam);
        }

        if (newTeam != null) {
            availableTeams.remove(newTeam);
            updateTeamPlayers(newTeam, teamOnePlayers, availableTeamOnePlayers, teamOnePlayerComboBoxes);
        } else {
            teamOnePlayers.clear();
            availableTeamOnePlayers.clear();
            updatePlayerComboBoxes(teamOnePlayerComboBoxes, availableTeamOnePlayers);
        }

        updateTeamTwoComboBox();
    }

    private void handleTeamTwoSelection(TeamDTO oldTeam, TeamDTO newTeam) {
        clearPlayerSelections(teamTwoPlayerComboBoxes);

        if (oldTeam != null) {
            availableTeams.add(oldTeam);
        }

        if (newTeam != null) {
            availableTeams.remove(newTeam);
            updateTeamPlayers(newTeam, teamTwoPlayers, availableTeamTwoPlayers, teamTwoPlayerComboBoxes);
        } else {
            teamTwoPlayers.clear();
            availableTeamTwoPlayers.clear();
            updatePlayerComboBoxes(teamTwoPlayerComboBoxes, availableTeamTwoPlayers);
        }

        updateTeamOneComboBox();
    }

    private void updateTeamPlayers(TeamDTO selectedTeam, List<PlayerInfoDTO> teamPlayers,
                                   List<PlayerInfoDTO> availableTeamPlayers,
                                   List<ComboBox<PlayerInfoDTO>> playerComboBoxes) {
        teamPlayers.clear();
        availableTeamPlayers.clear();

        List<PlayerInfoDTO> playersFromTeam = getPlayersForTeam(selectedTeam);
        teamPlayers.addAll(playersFromTeam);
        availableTeamPlayers.addAll(playersFromTeam);

        updatePlayerComboBoxes(playerComboBoxes, availableTeamPlayers);
    }

    private void setupPlayerSelectionListeners(List<ComboBox<PlayerInfoDTO>> playerComboBoxes,
                                               List<PlayerInfoDTO> availablePlayers) {
        for (ComboBox<PlayerInfoDTO> comboBox : playerComboBoxes) {
            comboBox.valueProperty().addListener((obs, oldPlayer, newPlayer) -> {
                handlePlayerSelection(oldPlayer, newPlayer, playerComboBoxes, availablePlayers);
            });
        }
    }

    private void handlePlayerSelection(PlayerInfoDTO oldPlayer, PlayerInfoDTO newPlayer,
                                       List<ComboBox<PlayerInfoDTO>> playerComboBoxes,
                                       List<PlayerInfoDTO> availablePlayers) {
        if (oldPlayer != null) {
            availablePlayers.add(oldPlayer);
        }

        if (newPlayer != null) {
            availablePlayers.remove(newPlayer);
        }

        updatePlayerComboBoxes(playerComboBoxes, availablePlayers);
    }

    private void updateTeamComboBoxes() {
        updateTeamOneComboBox();
        updateTeamTwoComboBox();
    }

    private void updateTeamOneComboBox() {
        TeamDTO currentSelection = teamOneComboBox.getValue();
        List<TeamDTO> teamsForComboBox = new ArrayList<>(availableTeams);

        if (currentSelection != null) {
            teamsForComboBox.add(currentSelection);
        }

        teamOneComboBox.getItems().setAll(teamsForComboBox);
        teamOneComboBox.setValue(currentSelection);
    }

    private void updateTeamTwoComboBox() {
        TeamDTO currentSelection = teamTwoComboBox.getValue();
        List<TeamDTO> teamsForComboBox = new ArrayList<>(availableTeams);

        if (currentSelection != null) {
            teamsForComboBox.add(currentSelection);
        }

        teamTwoComboBox.getItems().setAll(teamsForComboBox);
        teamTwoComboBox.setValue(currentSelection);
    }

    private void updatePlayerComboBoxes(List<ComboBox<PlayerInfoDTO>> playerComboBoxes,
                                        List<PlayerInfoDTO> availablePlayers) {
        for (ComboBox<PlayerInfoDTO> comboBox : playerComboBoxes) {
            PlayerInfoDTO currentSelection = comboBox.getValue();
            List<PlayerInfoDTO> playersForComboBox = new ArrayList<>(availablePlayers);

            if (currentSelection != null) {
                playersForComboBox.add(currentSelection);
            }

            comboBox.getItems().setAll(playersForComboBox);
            comboBox.setValue(currentSelection);
        }
    }

    private void clearPlayerSelections(List<ComboBox<PlayerInfoDTO>> playerComboBoxes) {
        for (ComboBox<PlayerInfoDTO> comboBox : playerComboBoxes) {
            comboBox.setValue(null);
        }
    }

    private List<PlayerInfoDTO> getPlayersForTeam(TeamDTO team) {
        return team.getPlayers().stream().toList();
    }
    @FXML
    public void onCreateGameClick(ActionEvent event) {

    }

    @FXML
    public void onBackClick(ActionEvent actionEvent) {
        FXMLUtils.switchScene("/com/soccernow/ui/soccernowui/fxml/home/home-screen.fxml",
                (Node) actionEvent.getSource());
    }

    @FXML
    public void onRemoveRefereeClick(ActionEvent actionEvent) {

    }

    @FXML
    public void onAddRefereeClick(ActionEvent actionEvent) {

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

}