package com.soccernow.ui.soccernowui.controller.player;

import com.soccernow.ui.soccernowui.util.FXMLUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;

public class PlayerDetailsController {

    @FXML
    private TextField playerNameField;

    @FXML
    private ComboBox<String> positionComboBox;

    @FXML
    public void initialize() {
    }

    @FXML
    private void onSaveGameClick() {
    }

    public void onBackClick(ActionEvent actionEvent) {
        FXMLUtils.switchScene("/com/soccernow/ui/soccernowui/fxml/player/player-list.fxml",
                (Node) actionEvent.getSource());
    }

}
