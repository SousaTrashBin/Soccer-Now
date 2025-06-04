package com.soccernow.ui.soccernowui.controller.game;

import com.soccernow.ui.soccernowui.dto.user.PlayerInfoDTO;
import com.soccernow.ui.soccernowui.dto.user.RefereeInfoDTO;
import com.soccernow.ui.soccernowui.util.CardEnum;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class RegisterGameResultsController {

    @FXML
    private TextField teamOneGoalsField;
    @FXML
    private TextField teamTwoGoalsField;
    @FXML
    private ComboBox<String> teamComboBox;
    @FXML
    private ComboBox<PlayerInfoDTO> playerComboBox;
    @FXML
    private TextField playerGoalsField;
    @FXML
    private ComboBox<CardEnum> cardTypeComboBox;
    @FXML
    private ComboBox<RefereeInfoDTO> refereeComboBox;
    @FXML
    private ListView<String> playerCardsListView;

    @FXML
    public void initialize() {
    }

    @FXML
    private void onSavePlayerStatsClick() {
    }

    @FXML
    private void onSaveGameClick() {
    }

}
