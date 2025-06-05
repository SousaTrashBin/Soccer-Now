package com.soccernow.ui.soccernowui.controller.referee;

import com.soccernow.ui.soccernowui.SoccerNowApp;
import com.soccernow.ui.soccernowui.api.PlayerApiController;
import com.soccernow.ui.soccernowui.api.RefereeApiController;
import com.soccernow.ui.soccernowui.dto.user.PlayerDTO;
import com.soccernow.ui.soccernowui.dto.user.RefereeDTO;
import com.soccernow.ui.soccernowui.util.FXMLUtils;
import jakarta.validation.Validator;
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
    private Validator validator;

    @FXML
    public void initialize() {
        hasCertificateComboBox.getItems().addAll("Yes", "No");
        hasCertificateComboBox.setValue("No");

        this.validator = SoccerNowApp.getValidatorFactory().getValidator();
    }

    public boolean getCertificateBoolean() {
        String selected = hasCertificateComboBox.getValue();
        return Objects.equals(selected, "Yes");
    }

    @FXML
    public void onRegisterClick(ActionEvent actionEvent) {
        RefereeDTO refereeDTO = new RefereeDTO();
        refereeDTO.setName(name.getText());
        refereeDTO.setHasCertificate(getCertificateBoolean());

        boolean isValid = FXMLUtils.validateAndShowAlert(refereeDTO, validator);
        if (!isValid) {
            return;
        }

        FXMLUtils.executeWithErrorHandling(() -> RefereeApiController.INSTANCE.registerReferee(refereeDTO))
                .ifPresent(savedDTO -> {
                    System.out.printf(savedDTO.toString());
                    FXMLUtils.showSuccess("Referee Successfully Registered", "Referee " + savedDTO.getName() + " registered successfully!");
                });
    }

    @FXML
    public void onBackClick(ActionEvent actionEvent) {
        FXMLUtils.switchScene("/com/soccernow/ui/soccernowui/fxml/home/home-screen.fxml",
                (Node) actionEvent.getSource());
    }
}
