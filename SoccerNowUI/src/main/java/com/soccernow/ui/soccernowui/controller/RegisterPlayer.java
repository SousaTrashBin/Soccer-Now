package com.soccernow.ui.soccernowui.controller;

import com.soccernow.ui.soccernowui.SoccerNowApp;
import com.soccernow.ui.soccernowui.api.PlayerApiController;
import com.soccernow.ui.soccernowui.dto.user.PlayerDTO;
import com.soccernow.ui.soccernowui.util.FutsalPositionEnum;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.util.Set;

public class RegisterPlayer {
    @FXML
    public TextField name;
    @FXML
    public ComboBox<FutsalPositionEnum> positionComboBox;
    private Validator validator;

    @FXML
    public void initialize() {
        positionComboBox.getItems().add(null);
        positionComboBox.getItems().addAll(FutsalPositionEnum.values());
        positionComboBox.setValue(null);

        this.validator = SoccerNowApp.getValidatorFactory().getValidator();
    }

    @FXML
    public void onRegisterClick(ActionEvent actionEvent) {
        PlayerDTO playerDTO = new PlayerDTO();
        playerDTO.setName(name.getText());
        playerDTO.setPreferredPosition(positionComboBox.getValue());

        Set<ConstraintViolation<PlayerDTO>> violations = validator.validate(playerDTO);

        if (!violations.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder("Please correct the following errors:\n");
            for (ConstraintViolation<PlayerDTO> violation : violations) {
                errorMessage.append("- ").append(violation.getMessage()).append("\n");
            }

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Validation Error");
            alert.setHeaderText("Invalid Player Data");
            alert.setContentText(errorMessage.toString());
            alert.showAndWait();
            System.err.println(errorMessage);
            return;
        }

        System.out.printf(playerDTO.toString());

        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
        successAlert.setTitle("Success");
        successAlert.setHeaderText("Player Registered");
        successAlert.setContentText("Player " + playerDTO.getName() + " registered successfully!");
        successAlert.showAndWait();
    }
}
