package com.soccernow.ui.soccernowui.controller.referee;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

import java.util.Objects;

public class RegisterReferee {

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
        return;
    }
}
