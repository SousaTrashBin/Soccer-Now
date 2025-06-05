package com.soccernow.ui.soccernowui.controller.game;

import com.soccernow.ui.soccernowui.dto.TeamDTO;
import com.soccernow.ui.soccernowui.dto.TeamInfoDTO;
import com.soccernow.ui.soccernowui.dto.games.*;
import com.soccernow.ui.soccernowui.dto.user.PlayerInfoDTO;
import com.soccernow.ui.soccernowui.dto.user.RefereeInfoDTO;
import com.soccernow.ui.soccernowui.util.CardEnum;
import com.soccernow.ui.soccernowui.util.FXMLUtils;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class RegisterGameResultsController {

    @FXML
    public TableView<CardInfoDTO> playerCardsTableView;
    @FXML
    public TableColumn<CardInfoDTO,String> playerCardsTypeColumn;
    @FXML
    public TableColumn<CardInfoDTO,String> playerCardsRefereeNameColumn;
    @FXML
    public ComboBox<RefereeInfoDTO> refereeComboBox;
    @FXML
    public ComboBox<CardEnum> cardTypeComboBox;
    @FXML
    public ComboBox<PlayerInfoDTO> playerComboBox;
    @FXML
    public TextField playerGoalsField;
    @FXML
    public ComboBox<GameTeamDTO> teamComboBox;

    private GameDTO gameDTO;
    List<PlayerGameStatsDTO> savedPlayerGameStats = new ArrayList<>();

    @FXML
    public void initialize() {
        playerCardsTypeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCardType().toString()));
        playerCardsTypeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getReferee().toString()));

        cardTypeComboBox.setItems(FXCollections.observableArrayList(CardEnum.values()));

        teamComboBox.valueProperty().addListener((obs, oldTeam, newTeam) -> {
            updatePlayers();
        });
        playerComboBox.valueProperty().addListener((obs, oldPlayer, newPlayer) -> {
            populatePlayerStats();
        });
    }

    private void populatePlayerStats() {
        PlayerInfoDTO selecterPlayer = playerComboBox.getSelectionModel().getSelectedItem();
        if (selecterPlayer != null) {
            return;
        }
        savedPlayerGameStats.stream().filter(playerGameStatsDTO -> playerGameStatsDTO.getPlayer().getId().equals(selecterPlayer.getId()))
                        .findFirst().ifPresentOrElse(playerGameStatsDTO -> {
                    playerGoalsField.setText(playerGameStatsDTO.getScoredGoals().toString());
                    playerCardsTableView.setItems(FXCollections.observableArrayList(playerGameStatsDTO.getReceivedCards()));
                },() -> {
                    playerGoalsField.setText("0");
                    playerCardsTableView.setItems(FXCollections.observableArrayList());
                });
    }

    private void updatePlayers() {
        GameTeamDTO selectedTeam = teamComboBox.getSelectionModel().getSelectedItem();
        List<PlayerInfoDTO> players = (selectedTeam != null) ? selectedTeam.getGamePlayers().stream().map(GamePlayerDTO::getPlayer).toList() : new ArrayList<>();

        playerComboBox.getItems().setAll(players);
        playerComboBox.getSelectionModel().clearSelection();
    }

    @FXML
    private void onSavePlayerStatsClick() {
    }

    @FXML
    private void onSaveGameClick() {
    }

    public void onBackClick(ActionEvent actionEvent) {
        FXMLUtils.switchScene("/com/soccernow/ui/soccernowui/fxml/game/game-list.fxml",
                (Node) actionEvent.getSource());
    }

    public void setGameDTO(GameDTO selectedGame) {
        this.gameDTO = selectedGame;

        List<GameTeamDTO> teams = List.of(
                gameDTO.getGameTeamOne(),
                gameDTO.getGameTeamTwo()
        );
        teamComboBox.setItems(FXCollections.observableArrayList(teams));

        Set<RefereeInfoDTO> secondaryRefs = selectedGame.getSecondaryReferees();
        RefereeInfoDTO primaryRef = selectedGame.getPrimaryReferee();

        List<RefereeInfoDTO> allRefs = new ArrayList<>(secondaryRefs);
        allRefs.add(primaryRef);

        refereeComboBox.setItems(FXCollections.observableArrayList(allRefs));
    }

    public void onAddCardClick(ActionEvent actionEvent) {
        if (cardTypeComboBox.getSelectionModel().getSelectedItem() == null
        || refereeComboBox.getSelectionModel().getSelectedItem() == null
        || playerComboBox.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        CardEnum cardType = cardTypeComboBox.getSelectionModel().getSelectedItem();
        RefereeInfoDTO referee = refereeComboBox.getSelectionModel().getSelectedItem();
        PlayerInfoDTO
                player = playerComboBox.getSelectionModel().getSelectedItem();
        CardInfoDTO newCardDTO = new CardInfoDTO();
        newCardDTO.setCardType(cardType);
        newCardDTO.setReferee(referee);
        newCardDTO.setPlayerPlayer(player);
        playerCardsTableView.getItems().add(newCardDTO);
    }
}
