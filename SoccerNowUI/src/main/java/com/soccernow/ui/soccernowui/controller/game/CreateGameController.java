package com.soccernow.ui.soccernowui.controller.game;

import com.soccernow.ui.soccernowui.api.TeamApiController;
import com.soccernow.ui.soccernowui.dto.TeamDTO;
import com.soccernow.ui.soccernowui.dto.user.PlayerInfoDTO;
import com.soccernow.ui.soccernowui.dto.user.RefereeInfoDTO;
import com.soccernow.ui.soccernowui.util.FXMLUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CreateGameController {

    private List<TeamDTO> allTeams;
    private ObservableList<TeamDTO> availableTeamsForTeamOne;
    private ObservableList<TeamDTO> availableTeamsForTeamTwo;

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
    private boolean updatingTeamTwoComboBox;
    private boolean updatingTeamOneComboBox;

    @FXML
    private void initialize() {
        teamOnePlayerComboBoxes = List.of(teamOneGoalieComboBox, teamOneSweeperComboBox, teamOneLeftWingerComboBox, teamOneRightWingerComboBox, teamOneForwardComboBox);
        teamTwoPlayerComboBoxes = List.of(teamTwoGoalieComboBox, teamTwoSweeperComboBox, teamTwoLeftWingerComboBox, teamTwoRightWingerComboBox, teamTwoForwardComboBox);

        availableTeamsForTeamOne = FXCollections.observableArrayList();
        availableTeamsForTeamTwo = FXCollections.observableArrayList();

        teamOneComboBox.setItems(availableTeamsForTeamOne);
        teamTwoComboBox.setItems(availableTeamsForTeamTwo);

        FXMLUtils.executeWithErrorHandling(TeamApiController.INSTANCE::getAllTeams)
                .ifPresent(teams -> {
                    this.allTeams = teams;
                    availableTeamsForTeamOne.setAll(new ArrayList<>(allTeams));
                    availableTeamsForTeamTwo.setAll(new ArrayList<>(allTeams));
                });

        teamOneComboBox.valueProperty().addListener((obs, oldTeam, newTeam) -> {
            if (!updatingTeamOneComboBox) {
                updateAvailableTeamsForTeamTwo();
            }
        });

        teamTwoComboBox.valueProperty().addListener((obs, oldTeam, newTeam) -> {
            if (!updatingTeamTwoComboBox) {
                updateAvailableTeamsForTeamOne();
            }
        });

        initializeTableColumns();
    }

    private void updateAvailableTeamsForTeamOne() {
        updatingTeamOneComboBox = true;

        List<TeamDTO> tempAvailableTeams = new ArrayList<>(allTeams);
        TeamDTO selectedTeamTwo = teamTwoComboBox.getSelectionModel().getSelectedItem();

        if (selectedTeamTwo != null) {
            tempAvailableTeams.remove(selectedTeamTwo);
        }
        availableTeamsForTeamOne.setAll(tempAvailableTeams);

        updatingTeamOneComboBox = false;
    }

    private void updateAvailableTeamsForTeamTwo() {
        updatingTeamTwoComboBox = true;
        List<TeamDTO> tempAvailableTeams = new ArrayList<>(allTeams);
        TeamDTO selectedTeamOne = teamOneComboBox.getSelectionModel().getSelectedItem();

        if (selectedTeamOne != null) {
            tempAvailableTeams.remove(selectedTeamOne);
        }
        availableTeamsForTeamTwo.setAll(tempAvailableTeams);

        updatingTeamTwoComboBox = false;
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