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

    private List<TeamDTO> availableTeams = new ArrayList<>();
    private boolean updatingTeamSelections = false;
    private boolean updatingPlayerSelections = false;

    @FXML
    private ComboBox<TeamDTO> teamOneComboBox;
    private List<PlayerInfoDTO> teamOnePlayers = new ArrayList<>();
    @FXML
    private ComboBox<PlayerInfoDTO> teamOneGoalieComboBox;
    @FXML
    private ComboBox<PlayerInfoDTO> teamOneSweeperComboBox;
    @FXML
    private ComboBox<PlayerInfoDTO> teamOneLeftWingerComboBox;
    @FXML
    private ComboBox<PlayerInfoDTO> teamOneRightWingerComboBox;
    @FXML
    private ComboBox<PlayerInfoDTO> teamOneForwardComboBox;

    @FXML
    private ComboBox<TeamDTO> teamTwoComboBox;
    private List<PlayerInfoDTO> teamTwoPlayers = new ArrayList<>();
    @FXML
    private ComboBox<PlayerInfoDTO> teamTwoGoalieComboBox;
    @FXML
    private ComboBox<PlayerInfoDTO> teamTwoSweeperComboBox;
    @FXML
    private ComboBox<PlayerInfoDTO> teamTwoLeftWingerComboBox;
    @FXML
    private ComboBox<PlayerInfoDTO> teamTwoRightWingerComboBox;
    @FXML
    private ComboBox<PlayerInfoDTO> teamTwoForwardComboBox;

    @FXML
    private ComboBox<RefereeInfoDTO> primaryRefereeComboBox;

    @FXML
    private TableView<RefereeInfoDTO> secondaryRefereesTableView;
    @FXML
    private TableColumn<RefereeInfoDTO, String> secondaryRefereesIdColumn;
    @FXML
    private TableColumn<RefereeInfoDTO, String> secondaryRefereesNameColumn;

    @FXML
    private TableView<RefereeInfoDTO> otherRefereesTableView;
    @FXML
    private TableColumn<RefereeInfoDTO, String> otherRefereesIdColumn;
    @FXML
    private TableColumn<RefereeInfoDTO, String> otherRefereesNameColumn;

    @FXML
    private TextField countryField;
    @FXML
    private TextField cityField;
    @FXML
    private TextField streetField;
    @FXML
    private TextField postalCodeField;

    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField timeField;

    @FXML
    private void initialize() {
        availableTeams = new ArrayList<>();

        FXMLUtils.executeWithErrorHandling(TeamApiController.INSTANCE::getAllTeams)
                .ifPresent(teams -> {
                    availableTeams = teams;
                    teamOneComboBox.getItems().setAll(availableTeams);
                    teamTwoComboBox.getItems().setAll(availableTeams);
                });

        teamOneComboBox.valueProperty().addListener((obs, oldTeam, newTeam) -> {
            if (!updatingTeamSelections) {
                updatingTeamSelections = true;
                try {
                    updateTeamTwoOptions(newTeam);
                    loadTeamOnePlayers(newTeam);
                } finally {
                    updatingTeamSelections = false;
                }
            }
        });

        teamTwoComboBox.valueProperty().addListener((obs, oldTeam, newTeam) -> {
            if (!updatingTeamSelections) {
                updatingTeamSelections = true;
                try {
                    updateTeamOneOptions(newTeam);
                    loadTeamTwoPlayers(newTeam);
                } finally {
                    updatingTeamSelections = false;
                }
            }
        });

        addTeamOnePlayerSelectionListeners();
        addTeamTwoPlayerSelectionListeners();

        initializeTableColumns();
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

    private void addTeamOnePlayerSelectionListeners() {
        teamOneGoalieComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (!updatingPlayerSelections) {
                updatingPlayerSelections = true;
                try {
                    refreshTeamOnePlayerChoices();
                } finally {
                    updatingPlayerSelections = false;
                }
            }
        });
        teamOneSweeperComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (!updatingPlayerSelections) {
                updatingPlayerSelections = true;
                try {
                    refreshTeamOnePlayerChoices();
                } finally {
                    updatingPlayerSelections = false;
                }
            }
        });
        teamOneLeftWingerComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (!updatingPlayerSelections) {
                updatingPlayerSelections = true;
                try {
                    refreshTeamOnePlayerChoices();
                } finally {
                    updatingPlayerSelections = false;
                }
            }
        });
        teamOneRightWingerComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (!updatingPlayerSelections) {
                updatingPlayerSelections = true;
                try {
                    refreshTeamOnePlayerChoices();
                } finally {
                    updatingPlayerSelections = false;
                }
            }
        });
        teamOneForwardComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (!updatingPlayerSelections) {
                updatingPlayerSelections = true;
                try {
                    refreshTeamOnePlayerChoices();
                } finally {
                    updatingPlayerSelections = false;
                }
            }
        });
    }

    private void refreshTeamOnePlayerChoices() {
        if (teamOnePlayers.isEmpty()) return;

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
        if (selectedTeam == null) {
            teamOnePlayers.clear();
            clearTeamOnePlayerComboBoxes();
            return;
        }

        teamOnePlayers = new ArrayList<>(selectedTeam.getPlayers());

        teamOneGoalieComboBox.getItems().setAll(teamOnePlayers);
        teamOneSweeperComboBox.getItems().setAll(teamOnePlayers);
        teamOneLeftWingerComboBox.getItems().setAll(teamOnePlayers);
        teamOneRightWingerComboBox.getItems().setAll(teamOnePlayers);
        teamOneForwardComboBox.getItems().setAll(teamOnePlayers);

        clearTeamOnePlayerSelections();
    }

    private void clearTeamOnePlayerComboBoxes() {
        teamOneGoalieComboBox.getItems().clear();
        teamOneSweeperComboBox.getItems().clear();
        teamOneLeftWingerComboBox.getItems().clear();
        teamOneRightWingerComboBox.getItems().clear();
        teamOneForwardComboBox.getItems().clear();
    }

    private void clearTeamOnePlayerSelections() {
        teamOneGoalieComboBox.setValue(null);
        teamOneSweeperComboBox.setValue(null);
        teamOneLeftWingerComboBox.setValue(null);
        teamOneRightWingerComboBox.setValue(null);
        teamOneForwardComboBox.setValue(null);
    }

    private void addTeamTwoPlayerSelectionListeners() {
        teamTwoGoalieComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (!updatingPlayerSelections) {
                updatingPlayerSelections = true;
                try {
                    refreshTeamTwoPlayerChoices();
                } finally {
                    updatingPlayerSelections = false;
                }
            }
        });
        teamTwoSweeperComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (!updatingPlayerSelections) {
                updatingPlayerSelections = true;
                try {
                    refreshTeamTwoPlayerChoices();
                } finally {
                    updatingPlayerSelections = false;
                }
            }
        });
        teamTwoLeftWingerComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (!updatingPlayerSelections) {
                updatingPlayerSelections = true;
                try {
                    refreshTeamTwoPlayerChoices();
                } finally {
                    updatingPlayerSelections = false;
                }
            }
        });
        teamTwoRightWingerComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (!updatingPlayerSelections) {
                updatingPlayerSelections = true;
                try {
                    refreshTeamTwoPlayerChoices();
                } finally {
                    updatingPlayerSelections = false;
                }
            }
        });
        teamTwoForwardComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (!updatingPlayerSelections) {
                updatingPlayerSelections = true;
                try {
                    refreshTeamTwoPlayerChoices();
                } finally {
                    updatingPlayerSelections = false;
                }
            }
        });
    }

    private void refreshTeamTwoPlayerChoices() {
        if (teamTwoPlayers.isEmpty()) return;

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
        if (selectedTeam == null) {
            teamTwoPlayers.clear();
            clearTeamTwoPlayerComboBoxes();
            return;
        }

        teamTwoPlayers = new ArrayList<>(selectedTeam.getPlayers());

        teamTwoGoalieComboBox.getItems().setAll(teamTwoPlayers);
        teamTwoSweeperComboBox.getItems().setAll(teamTwoPlayers);
        teamTwoLeftWingerComboBox.getItems().setAll(teamTwoPlayers);
        teamTwoRightWingerComboBox.getItems().setAll(teamTwoPlayers);
        teamTwoForwardComboBox.getItems().setAll(teamTwoPlayers);

        clearTeamTwoPlayerSelections();
    }

    private void clearTeamTwoPlayerComboBoxes() {
        teamTwoGoalieComboBox.getItems().clear();
        teamTwoSweeperComboBox.getItems().clear();
        teamTwoLeftWingerComboBox.getItems().clear();
        teamTwoRightWingerComboBox.getItems().clear();
        teamTwoForwardComboBox.getItems().clear();
    }

    private void clearTeamTwoPlayerSelections() {
        teamTwoGoalieComboBox.setValue(null);
        teamTwoSweeperComboBox.setValue(null);
        teamTwoLeftWingerComboBox.setValue(null);
        teamTwoRightWingerComboBox.setValue(null);
        teamTwoForwardComboBox.setValue(null);
    }

    private void updatePlayerComboBox(ComboBox<PlayerInfoDTO> comboBox, PlayerInfoDTO currentSelection, List<PlayerInfoDTO> others) {
        if (updatingPlayerSelections) return;

        List<PlayerInfoDTO> allOptions = new ArrayList<>(others);
        if (currentSelection != null) {
            allOptions.add(currentSelection);
        }

        updatingPlayerSelections = true;
        try {
            comboBox.getItems().setAll(allOptions);
            comboBox.setValue(currentSelection);
        } finally {
            updatingPlayerSelections = false;
        }
    }

    private void updateTeamOneOptions(TeamDTO selectedTeamTwo) {
        if (updatingTeamSelections) return;

        List<TeamDTO> filtered = new ArrayList<>(availableTeams);
        filtered.remove(selectedTeamTwo);

        TeamDTO currentSelection = teamOneComboBox.getValue();

        updatingTeamSelections = true;
        try {
            teamOneComboBox.getItems().setAll(filtered);
            if (filtered.contains(currentSelection) && currentSelection != selectedTeamTwo) {
                teamOneComboBox.setValue(currentSelection);
            } else if (currentSelection == selectedTeamTwo) {
                teamOneComboBox.setValue(null);
            }
        } finally {
            updatingTeamSelections = false;
        }
    }

    private void updateTeamTwoOptions(TeamDTO selectedTeamOne) {
        if (updatingTeamSelections) return;

        List<TeamDTO> filtered = new ArrayList<>(availableTeams);
        filtered.remove(selectedTeamOne);

        TeamDTO currentSelection = teamTwoComboBox.getValue();

        updatingTeamSelections = true;
        try {
            teamTwoComboBox.getItems().setAll(filtered);
            if (filtered.contains(currentSelection) && currentSelection != selectedTeamOne) {
                teamTwoComboBox.setValue(currentSelection);
            } else if (currentSelection == selectedTeamOne) {
                teamTwoComboBox.setValue(null);
            }
        } finally {
            updatingTeamSelections = false;
        }
    }

    @FXML
    public void onCreateGameClick(ActionEvent event) {
        if (!validateGameForm()) {
            return;
        }

        System.out.println("Create game functionality not yet implemented");
    }

    private boolean validateGameForm() {
        if (teamOneComboBox.getValue() == null) {
            showAlert("Please select Team One");
            return false;
        }

        if (teamTwoComboBox.getValue() == null) {
            showAlert("Please select Team Two");
            return false;
        }

        if (datePicker.getValue() == null) {
            showAlert("Please select a date");
            return false;
        }

        if (timeField.getText() == null || timeField.getText().trim().isEmpty()) {
            showAlert("Please enter a time");
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
            showAlert("Please select a referee to remove");
        }
    }

    @FXML
    public void onAddRefereeClick(ActionEvent actionEvent) {
        RefereeInfoDTO selectedReferee = otherRefereesTableView.getSelectionModel().getSelectedItem();
        if (selectedReferee != null) {
            otherRefereesTableView.getItems().remove(selectedReferee);
            secondaryRefereesTableView.getItems().add(selectedReferee);
        } else {
            showAlert("Please select a referee to add");
        }
    }
}