package com.soccernow.ui.soccernowui.controller.tournament;

import com.soccernow.ui.soccernowui.api.PlayerApiController;
import com.soccernow.ui.soccernowui.dto.TeamInfoDTO;
import com.soccernow.ui.soccernowui.dto.games.GameDTO;
import com.soccernow.ui.soccernowui.dto.tournament.TournamentDTO;
import com.soccernow.ui.soccernowui.dto.user.PlayerDTO;
import com.soccernow.ui.soccernowui.dto.user.PlayerInfoDTO;
import com.soccernow.ui.soccernowui.util.ErrorException;
import com.soccernow.ui.soccernowui.util.FXMLUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.List;

public class TournamentInProgressDetailsController {

    @FXML
    private TableView<GameDTO> tournamentGamesTableView;

    @FXML
    private TableView<GameDTO> otherGamesTableView;
    private TournamentDTO tournamentDTO;

    @FXML
    public void initialize() {
    }

    @FXML
    private void onCancelGameClick() {
    }

    @FXML
    private void onRemoveGameClick() {
    }

    @FXML
    private void onAddGameClick() {
    }

    @FXML
    private void onEndTournamentClick() {
    }

    @FXML
    public void onBackClick(ActionEvent actionEvent) {
        FXMLUtils.switchScene("/com/soccernow/ui/soccernowui/fxml/tournament/tournament-list.fxml",
                (Node) actionEvent.getSource());
    }

    public void setTournamentDTO(TournamentDTO selectedTournament) {
//        this.tournamentDTO = selectedTournament;
//
//        ObservableList<TeamInfoDTO> teamPlayersObservable = FXCollections.observableArrayList(teamDTO.getPlayers());
//        teamPlayersTableView.setItems(teamPlayersObservable);
//
//        try {
//            List<PlayerDTO> allPlayers = PlayerApiController.INSTANCE.getAllPlayers();
//            List<PlayerInfoDTO> otherTeams = allPlayers.stream()
//                    .map(playerDTO -> new PlayerInfoDTO(playerDTO.getId(), playerDTO.getName(),playerDTO.getPreferredPosition()))
//                    .filter(playerInfoDTO -> !teamDTO.getPlayers().contains(playerInfoDTO))
//                    .toList();
//
//            ObservableList<PlayerInfoDTO> otherTeamsObservable = FXCollections.observableArrayList(otherTeams);
//            otherPlayersTableView.setItems(otherTeamsObservable);
//        } catch (IOException | ErrorException e) {
//            System.err.println("Failed to load teams: " + e.getMessage());
//            otherPlayersTableView.setItems(FXCollections.observableArrayList());
//        }
    }
}
