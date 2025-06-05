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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CreateGameController {

    private List<TeamDTO> allTeams = new ArrayList<>();
    private boolean updatingTeamSelections = false;
    private boolean updatingPlayerSelections = false;

    @FXML private ComboBox<TeamDTO> teamOneComboBox;
    @FXML private ComboBox<PlayerInfoDTO> teamOneGoalieComboBox;
    @FXML private ComboBox<PlayerInfoDTO> teamOneSweeperComboBox;
    @FXML private ComboBox<PlayerInfoDTO> teamOneLeftWingerComboBox;
    @FXML private ComboBox<PlayerInfoDTO> teamOneRightWingerComboBox;
    @FXML private ComboBox<PlayerInfoDTO> teamOneForwardComboBox;

    @FXML private ComboBox<TeamDTO> teamTwoComboBox;
    @FXML private ComboBox<PlayerInfoDTO> teamTwoGoalieComboBox;
    @FXML private ComboBox<PlayerInfoDTO> teamTwoSweeperComboBox;
    @FXML private ComboBox<PlayerInfoDTO> teamTwoLeftWingerComboBox;
    @FXML private ComboBox<PlayerInfoDTO> teamTwoRightWingerComboBox;
    @FXML private ComboBox<PlayerInfoDTO> teamTwoForwardComboBox;

    @FXML private ComboBox<RefereeInfoDTO> primaryRefereeComboBox;
    @FXML private TableView<RefereeInfoDTO> secondaryRefereesTableView;
    @FXML private TableView<RefereeInfoDTO> otherRefereesTableView;
    @FXML private TableColumn<RefereeInfoDTO, String> secondaryRefereesIdColumn;
    @FXML private TableColumn<RefereeInfoDTO, String> secondaryRefereesNameColumn;
    @FXML private TableColumn<RefereeInfoDTO, String> otherRefereesIdColumn;
    @FXML private TableColumn<RefereeInfoDTO, String> otherRefereesNameColumn;

    @FXML private TextField countryField, cityField, streetField, postalCodeField;
    @FXML private DatePicker datePicker;
    @FXML private TextField timeField;

    @FXML
    private void initialize() {
        allTeams = FXMLUtils.executeWithErrorHandling(TeamApiController.INSTANCE::getAllTeams).orElse(new ArrayList<>());

        teamOneComboBox.getItems().setAll(allTeams);
        teamTwoComboBox.getItems().setAll(allTeams);

        teamOneComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (!updatingTeamSelections) {
                updatingTeamSelections = true;
                try {
                    updateAvailableTeams();
                    loadTeamOnePlayers(newVal);
                } finally {
                    updatingTeamSelections = false;
                }
            }
        });

        teamTwoComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (!updatingTeamSelections) {
                updatingTeamSelections = true;
                try {
                    updateAvailableTeams();
                    loadTeamTwoPlayers(newVal);
                } finally {
                    updatingTeamSelections = false;
                }
            }
        });

        addPlayerListeners(teamOneGoalieComboBox, teamOneSweeperComboBox, teamOneLeftWingerComboBox, teamOneRightWingerComboBox, teamOneForwardComboBox, true);
        addPlayerListeners(teamTwoGoalieComboBox, teamTwoSweeperComboBox, teamTwoLeftWingerComboBox, teamTwoRightWingerComboBox, teamTwoForwardComboBox, false);

        initializeTableColumns();
    }

    private void addPlayerListeners(
            ComboBox<PlayerInfoDTO> teamOneGoalieComboBox,
            ComboBox<PlayerInfoDTO> teamOneSweeperComboBox,
            ComboBox<PlayerInfoDTO> teamOneLeftWingerComboBox,
            ComboBox<PlayerInfoDTO> teamOneRightWingerComboBox,
            ComboBox<PlayerInfoDTO> teamOneForwardComboBox,
            boolean isTeamOne
    ) {
        ComboBox<PlayerInfoDTO>[] boxes = new ComboBox[]{
                teamOneGoalieComboBox,
                teamOneSweeperComboBox,
                teamOneLeftWingerComboBox,
                teamOneRightWingerComboBox,
                teamOneForwardComboBox
        };

        for (ComboBox<PlayerInfoDTO> box : boxes) {
            box.valueProperty().addListener((obs, oldVal, newVal) -> {
                if (!updatingPlayerSelections) {
                    updatingPlayerSelections = true;
                    try {
                        refreshPlayerChoices(isTeamOne);
                        System.out.println("Sousa: Player role updated in " + (isTeamOne ? "Team One" : "Team Two"));
                    } finally {
                        updatingPlayerSelections = false;
                    }
                }
            });
        }
    }

    private void updateAvailableTeams() {
        List<TeamDTO> filtered = new ArrayList<>(allTeams);
        if (teamOneComboBox.getValue() != null) filtered.remove(teamOneComboBox.getValue());
        teamTwoComboBox.getItems().setAll(filtered);

        filtered = new ArrayList<>(allTeams);
        if (teamTwoComboBox.getValue() != null) filtered.remove(teamTwoComboBox.getValue());
        teamOneComboBox.getItems().setAll(filtered);
    }

    private void loadTeamOnePlayers(TeamDTO team) {
        Set<PlayerInfoDTO> players = team != null ? team.getPlayers() : new HashSet<>();
        teamOneGoalieComboBox.getItems().setAll(players);
        teamOneSweeperComboBox.getItems().setAll(players);
        teamOneLeftWingerComboBox.getItems().setAll(players);
        teamOneRightWingerComboBox.getItems().setAll(players);
        teamOneForwardComboBox.getItems().setAll(players);
    }

    private void loadTeamTwoPlayers(TeamDTO team) {
        Set<PlayerInfoDTO> players = team != null ? team.getPlayers() : new HashSet<>();
        teamTwoGoalieComboBox.getItems().setAll(players);
        teamTwoSweeperComboBox.getItems().setAll(players);
        teamTwoLeftWingerComboBox.getItems().setAll(players);
        teamTwoRightWingerComboBox.getItems().setAll(players);
        teamTwoForwardComboBox.getItems().setAll(players);
    }

    private void refreshPlayerChoices(boolean isTeamOne) {
        ComboBox<PlayerInfoDTO>[] boxes = isTeamOne
                ? new ComboBox[]{teamOneGoalieComboBox, teamOneSweeperComboBox, teamOneLeftWingerComboBox, teamOneRightWingerComboBox, teamOneForwardComboBox}
                : new ComboBox[]{teamTwoGoalieComboBox, teamTwoSweeperComboBox, teamTwoLeftWingerComboBox, teamTwoRightWingerComboBox, teamTwoForwardComboBox};

        List<PlayerInfoDTO> all = new ArrayList<>(boxes[0].getItems());
        List<PlayerInfoDTO> selected = new ArrayList<>();
        for (ComboBox<PlayerInfoDTO> box : boxes) {
            if (box.getValue() != null) selected.add(box.getValue());
        }

        for (ComboBox<PlayerInfoDTO> box : boxes) {
            PlayerInfoDTO current = box.getValue();
            List<PlayerInfoDTO> available = all.stream().filter(p -> !selected.contains(p) || p.equals(current)).collect(Collectors.toList());
            box.getItems().setAll(available);
            box.setValue(current);
        }
    }

    private void initializeTableColumns() {
        secondaryRefereesIdColumn.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getId().toString()));
        secondaryRefereesNameColumn.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getName()));
        otherRefereesIdColumn.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getId().toString()));
        otherRefereesNameColumn.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getName()));
    }

    @FXML
    public void onCreateGameClick(ActionEvent event) {
        if (!validateGameForm()) return;
        System.out.println("Game creation logic goes here");
    }

    private boolean validateGameForm() {
        if (teamOneComboBox.getValue() == null) return showError("Select Team One");
        if (teamTwoComboBox.getValue() == null) return showError("Select Team Two");
        if (datePicker.getValue() == null) return showError("Select a date");
        if (timeField.getText().trim().isEmpty()) return showError("Enter a time");
        return true;
    }

    private boolean showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Validation Error");
        alert.setContentText(msg);
        alert.showAndWait();
        return false;
    }

    @FXML
    public void onBackClick(ActionEvent event) {
        FXMLUtils.switchScene("/com/soccernow/ui/soccernowui/fxml/home/home-screen.fxml", (Node) event.getSource());
    }

    @FXML
    public void onRemoveRefereeClick(ActionEvent event) {
        RefereeInfoDTO selected = secondaryRefereesTableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            secondaryRefereesTableView.getItems().remove(selected);
            otherRefereesTableView.getItems().add(selected);
        } else {
            showError("Select a referee to remove");
        }
    }

    @FXML
    public void onAddRefereeClick(ActionEvent event) {
        RefereeInfoDTO selected = otherRefereesTableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            otherRefereesTableView.getItems().remove(selected);
            secondaryRefereesTableView.getItems().add(selected);
        } else {
            showError("Select a referee to add");
        }
    }
}