package com.soccernow.ui.soccernowui.controller.referee;

import com.soccernow.ui.soccernowui.util.FXMLUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;

import java.util.Objects;

public class RegisterRefereeController {

    @FXML
    private TextField name;

    @FXML
    private Button registerButton;

    @FXML
    private ComboBox<String> hasCertificateComboBox;

    @FXML
    public void initialize() {
        hasCertificateComboBox.getItems().add(null);
        hasCertificateComboBox.getItems().addAll("Yes", "No");
        hasCertificateComboBox.setValue("No");
    }

    public boolean getCertificateBoolean() {
        String selected = hasCertificateComboBox.getValue();
        return Objects.equals(selected, "Yes");
    }

    @FXML
    public void onRegisterClick(ActionEvent actionEvent) {
        return;
    }

    @FXML
    public void onBackClick(ActionEvent actionEvent) {
        FXMLUtils.switchScene("/com/soccernow/ui/soccernowui/fxml/home/home-screen.fxml",
                (Node) actionEvent.getSource());
    }
}
