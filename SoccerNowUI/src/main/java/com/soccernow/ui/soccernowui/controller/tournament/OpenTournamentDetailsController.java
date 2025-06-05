package com.soccernow.ui.soccernowui.controller.tournament;

import com.soccernow.ui.soccernowui.SoccerNowApp;
import com.soccernow.ui.soccernowui.api.PlayerApiController;
import com.soccernow.ui.soccernowui.api.PointTournamentApiController;
import com.soccernow.ui.soccernowui.api.TeamApiController;
import com.soccernow.ui.soccernowui.dto.TeamDTO;
import com.soccernow.ui.soccernowui.dto.TeamInfoDTO;
import com.soccernow.ui.soccernowui.dto.tournament.PointTournamentDTO;
import com.soccernow.ui.soccernowui.dto.tournament.TeamPointsDTO;
import com.soccernow.ui.soccernowui.dto.tournament.TournamentDTO;
import com.soccernow.ui.soccernowui.dto.user.PlayerDTO;
import com.soccernow.ui.soccernowui.dto.user.PlayerInfoDTO;
import com.soccernow.ui.soccernowui.util.ApiUtils;
import com.soccernow.ui.soccernowui.util.ErrorException;
import com.soccernow.ui.soccernowui.util.FXMLUtils;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class OpenTournamentDetailsController {


    @FXML
    public TableView<TeamInfoDTO> participatingTeamsListView;
    @FXML
    public TableColumn<TeamInfoDTO,String> participatingTeamsIdColumn;
    @FXML
    public TableColumn<TeamInfoDTO,String> participatingTeamsNameColumn;

    @FXML
    public TableView<TeamInfoDTO> availableTeamsListView;
    @FXML
    public TableColumn<TeamInfoDTO,String> availableTeamsIdColumn;
    @FXML
    public TableColumn<TeamInfoDTO,String> availableTeamsNameColumn;
    private PointTournamentDTO tournamentDTO;
    public List<FXMLUtils.ConsumerWithExceptions> pendingOperations = new ArrayList<>();

    @FXML
    public void initialize() {
        participatingTeamsIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId().toString()));
        participatingTeamsNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));

        availableTeamsIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId().toString()));
        availableTeamsNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
    }

    @FXML
    private void onRemoveTeamClick() {
        TeamInfoDTO selectedTeam = participatingTeamsListView.getSelectionModel().getSelectedItem();
        if (selectedTeam == null) {
            return;
        }
        pendingOperations.add(() -> PointTournamentApiController.INSTANCE.removeTeamFromTournament(tournamentDTO.getId(), selectedTeam.getId()));
        this.availableTeamsListView.getItems().remove(selectedTeam);
        this.participatingTeamsListView.getItems().add(selectedTeam);
    }

    @FXML
    private void onAddTeamClick() {
        TeamInfoDTO selectedTeam = availableTeamsListView.getSelectionModel().getSelectedItem();
        if (selectedTeam == null) {
            return;
        }
        pendingOperations.add(() -> PointTournamentApiController.INSTANCE.addTeamToTournament(tournamentDTO.getId(), selectedTeam.getId()));
        this.availableTeamsListView.getItems().add(selectedTeam);
        this.participatingTeamsListView.getItems().remove(selectedTeam);
    }

    @FXML
    private void onStartTournamentClick(ActionEvent actionEvent) {
        for (FXMLUtils.ConsumerWithExceptions pendingOperation : pendingOperations) {
            FXMLUtils.executeWithErrorHandling(pendingOperation);
        }
        FXMLUtils.executeWithErrorHandling(() -> PointTournamentApiController.INSTANCE.closeRegistrations(tournamentDTO.getId()))
                .ifPresent(savedDTO -> {
                    System.out.printf(savedDTO.toString());
                    FXMLUtils.showSuccess("Tournament Successfully Started", "Tournament " + savedDTO.getName() + " started successfully!");
                });
        FXMLUtils.switchScene("/com/soccernow/ui/soccernowui/fxml/tournament/tournament-list.fxml",
                (Node) actionEvent.getSource());
    }

    @FXML
    public void onBackClick(ActionEvent actionEvent) {
        FXMLUtils.switchScene("/com/soccernow/ui/soccernowui/fxml/tournament/tournament-list.fxml",
                (Node) actionEvent.getSource());
    }

    public void setTournamentDTO(PointTournamentDTO selectedTournament) {
        this.tournamentDTO = selectedTournament;

        Set<TeamInfoDTO> participatingTeams = tournamentDTO.getTeamPoints().stream().map(TeamPointsDTO::getTeam).collect(Collectors.toSet());
        ObservableList<TeamInfoDTO> participatingTeamsObservable = FXCollections.observableArrayList(participatingTeams);
        participatingTeamsListView.setItems(participatingTeamsObservable);

        try {
            List<TeamDTO> allTeams = TeamApiController.INSTANCE.getAllTeams();
            List<TeamInfoDTO> otherTeams = allTeams.stream()
                    .map(team -> new TeamInfoDTO(team.getId(), team.getName()))
                    .filter(teamInfoDTO -> !participatingTeams.contains(teamInfoDTO))
                    .toList();

            ObservableList<TeamInfoDTO> availableTeamsObservable = FXCollections.observableArrayList(otherTeams);
            availableTeamsListView.setItems(availableTeamsObservable);
        } catch (IOException | ErrorException e) {
            System.err.println("Failed to load teams: " + e.getMessage());
            availableTeamsListView.setItems(FXCollections.observableArrayList());
        }
    }

    public void onSaveTeamChangesClick(ActionEvent actionEvent) {
        for (FXMLUtils.ConsumerWithExceptions pendingOperation : pendingOperations) {
            FXMLUtils.executeWithErrorHandling(pendingOperation);
        }
        FXMLUtils.switchScene("/com/soccernow/ui/soccernowui/fxml/tournament/tournament-list.fxml",
                (Node) actionEvent.getSource());
    }
}
