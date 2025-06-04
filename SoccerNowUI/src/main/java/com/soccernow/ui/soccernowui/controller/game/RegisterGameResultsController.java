package com.soccernow.ui.soccernowui.controller.game;

import com.soccernow.ui.soccernowui.dto.user.PlayerInfoDTO;
import com.soccernow.ui.soccernowui.dto.user.RefereeInfoDTO;
import com.soccernow.ui.soccernowui.util.CardEnum;
import com.soccernow.ui.soccernowui.util.FXMLUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
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

    public void onBackClick(ActionEvent actionEvent) {
        FXMLUtils.switchScene("/com/soccernow/ui/soccernowui/fxml/game/game-list.fxml",
                (Node) actionEvent.getSource());
    }
}
