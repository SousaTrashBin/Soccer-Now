package com.soccernow.ui.soccernowui.controller.game;

import com.soccernow.ui.soccernowui.util.FXMLUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;

public class CreateGameController {

    @FXML
    private ComboBox<String> teamOneComboBox;
;    @FXML
    private ComboBox<String> teamTwoComboBox;
    @FXML
    private ComboBox<String> primaryRefereeComboBox;
    @FXML
    private ListView<String> secondaryRefereesListView;
    @FXML
    private ComboBox<String> addressComboBox;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField timeField;

    @FXML
    private void initialize() {

    }

    @FXML
    public void onCreateGameClick(ActionEvent event) {
        return;
    }

    @FXML
    public void onBackClick(ActionEvent actionEvent) {
        FXMLUtils.switchScene("/com/soccernow/ui/soccernowui/fxml/home/home-screen.fxml",
                (Node) actionEvent.getSource());
    }
}
