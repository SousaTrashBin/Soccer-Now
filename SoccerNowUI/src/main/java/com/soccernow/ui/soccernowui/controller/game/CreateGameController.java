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
            updateTeamOnePlayers();
        });

        teamTwoComboBox.valueProperty().addListener((obs, oldTeam, newTeam) -> {
            updateTeamTwoPlayers();
        });

        initializeTableColumns();
    }

    private void updateTeamOnePlayers() {
        TeamDTO selectedTeam = teamOneComboBox.getSelectionModel().getSelectedItem();
        List<PlayerInfoDTO> players = (selectedTeam != null) ? selectedTeam.getPlayers().stream().toList() : new ArrayList<>();

        for (ComboBox<PlayerInfoDTO> playerComboBox : teamOnePlayerComboBoxes) {
            playerComboBox.getItems().setAll(players);
            playerComboBox.getSelectionModel().clearSelection();
        }
    }

    private void updateTeamTwoPlayers() {
        TeamDTO selectedTeam = teamTwoComboBox.getSelectionModel().getSelectedItem();
        List<PlayerInfoDTO> players = (selectedTeam != null) ? selectedTeam.getPlayers().stream().toList() : new ArrayList<>();

        for (ComboBox<PlayerInfoDTO> playerComboBox : teamTwoPlayerComboBoxes) {
            playerComboBox.getItems().setAll(players);
            playerComboBox.getSelectionModel().clearSelection();
        }
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