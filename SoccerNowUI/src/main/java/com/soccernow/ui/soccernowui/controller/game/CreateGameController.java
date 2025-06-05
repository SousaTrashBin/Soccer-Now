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
import java.util.Objects;
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
            if (newTeam != null) {
                teamTwoComboBox.getItems().setAll(filterOutTeam(newTeam));
                teamOnePlayers = new ArrayList<>(newTeam.getPlayers());
                updatePlayerComboBoxes(teamOnePlayerComboBoxes, teamOnePlayers);
                resetComboBoxes(teamOnePlayerComboBoxes);
            } else {
                teamTwoComboBox.getItems().setAll(allTeams);
            }
        });

        teamTwoComboBox.valueProperty().addListener((obs, oldTeam, newTeam) -> {
            if (newTeam != null) {
                teamOneComboBox.getItems().setAll(filterOutTeam(newTeam));
                teamTwoPlayers = new ArrayList<>(newTeam.getPlayers());
                updatePlayerComboBoxes(teamTwoPlayerComboBoxes, teamTwoPlayers);
                resetComboBoxes(teamTwoPlayerComboBoxes);
            } else {
                teamOneComboBox.getItems().setAll(allTeams);
            }
        });

        initializeTableColumns();
    }

    private List<TeamDTO> filterOutTeam(TeamDTO excluded) {
        return allTeams.stream()
                .filter(team -> !team.equals(excluded))
                .collect(Collectors.toList());
    }

    private void updatePlayerComboBoxes(List<ComboBox<PlayerInfoDTO>> comboBoxes, List<PlayerInfoDTO> players) {
        for (ComboBox<PlayerInfoDTO> cb : comboBoxes) {
            cb.getItems().setAll(players);
            setupPlayerSelectionListener(cb, comboBoxes);
        }
    }

    private void resetComboBoxes(List<ComboBox<PlayerInfoDTO>> comboBoxes) {
        for (ComboBox<PlayerInfoDTO> cb : comboBoxes) {
            cb.getSelectionModel().clearSelection();
        }
    }

    private void setupPlayerSelectionListener(ComboBox<PlayerInfoDTO> currentComboBox, List<ComboBox<PlayerInfoDTO>> allBoxes) {
        currentComboBox.valueProperty().addListener((obs, oldPlayer, newPlayer) -> {
            Set<PlayerInfoDTO> selected = allBoxes.stream()
                    .map(ComboBox::getValue)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());

            List<PlayerInfoDTO> fullList = new ArrayList<>(currentComboBox.getItems());
            for (ComboBox<PlayerInfoDTO> cb : allBoxes) {
                PlayerInfoDTO previouslySelected = cb.getValue();
                List<PlayerInfoDTO> available = fullList.stream()
                        .filter(p -> !selected.contains(p) || p.equals(previouslySelected))
                        .collect(Collectors.toList());

                cb.getItems().setAll(available);
                if (previouslySelected != null && !available.contains(previouslySelected)) {
                    cb.setValue(null);
                }
            }
        });
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