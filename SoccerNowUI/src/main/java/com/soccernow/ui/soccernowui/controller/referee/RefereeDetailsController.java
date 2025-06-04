package com.soccernow.ui.soccernowui.controller.referee;

import com.soccernow.ui.soccernowui.util.FXMLUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class RefereeDetailsController {

    @FXML
    private TextField refereeNameField;

    @FXML
    private ComboBox<String> certificateComboBox;

    @FXML
    public void initialize() {
    }

    @FXML
    private void onSaveClick() {
    }

    public void onBackClick(ActionEvent actionEvent) {
        FXMLUtils.switchScene("/com/soccernow/ui/soccernowui/fxml/player/player-list.fxml",
                (Node) actionEvent.getSource());
    }
}
