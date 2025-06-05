package com.soccernow.ui.soccernowui.controller.referee;

import com.soccernow.ui.soccernowui.SoccerNowApp;
import com.soccernow.ui.soccernowui.api.RefereeApiController;
import com.soccernow.ui.soccernowui.dto.user.RefereeDTO;
import com.soccernow.ui.soccernowui.util.FXMLUtils;
import jakarta.validation.Validator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.util.Objects;

public class RefereeDetailsController {

    @FXML
    private TextField refereeNameField;

    @FXML
    private ComboBox<String> certificateComboBox;

    RefereeDTO refereeDTO;
    private Validator validator;

    @FXML
    public void initialize() {
        certificateComboBox.getItems().addAll("Yes", "No");
        certificateComboBox.setValue("No");

        this.validator = SoccerNowApp.getValidatorFactory().getValidator();
    }

    public void setRefereeDTO(RefereeDTO refereeDTO) {
        this.refereeDTO = refereeDTO;
        populateFields();
    }

    public boolean getCertificateBoolean() {
        String selected = certificateComboBox.getValue();
        return Objects.equals(selected, "Yes");
    }

    private void populateFields() {
        if (refereeDTO != null) {
            refereeNameField.setText(refereeDTO.getName());
            certificateComboBox.setValue(refereeDTO.getHasCertificate() ? "Yes" : "No");
        }
    }

    @FXML
    private void onSaveClick() {
        RefereeDTO updatedDTO = new RefereeDTO();
        updatedDTO.setName(refereeNameField.getText());
        updatedDTO.setHasCertificate(getCertificateBoolean());

        boolean isValid = FXMLUtils.validateAndShowAlert(refereeDTO, validator);
        if (!isValid) {
            return;
        }

        FXMLUtils.executeWithErrorHandling(() -> RefereeApiController.INSTANCE.updateRefereeById(refereeDTO.getId(),updatedDTO))
                .ifPresent(savedDTO -> {
                    System.out.printf(savedDTO.toString());
                    FXMLUtils.showSuccess("Referee Successfully Updated", "Referee " + savedDTO.getName() + " registered updated!");
                });
    }

    public void onBackClick(ActionEvent actionEvent) {
        FXMLUtils.switchScene("/com/soccernow/ui/soccernowui/fxml/player/player-list.fxml",
                (Node) actionEvent.getSource());
    }
}
