package com.soccernow.ui.soccernowui.controller.player;

import com.soccernow.ui.soccernowui.SoccerNowApp;
import com.soccernow.ui.soccernowui.api.PlayerApiController;
import com.soccernow.ui.soccernowui.dto.user.PlayerDTO;
import com.soccernow.ui.soccernowui.util.FXMLUtils;
import com.soccernow.ui.soccernowui.util.FutsalPositionEnum;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.Set;

public class RegisterPlayerController {
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

        boolean isValid = FXMLUtils.validateAndShowAlert(playerDTO, validator);
        if (!isValid) {
            System.err.println("Validation failed for: " + playerDTO);
            return;
        }

        PlayerDTO savedDTO;
        try {
            savedDTO = PlayerApiController.INSTANCE.registerPlayer(playerDTO);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.printf(playerDTO.toString());

        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
        successAlert.setTitle("Success");
        successAlert.setHeaderText("Player Successfully Registered");
        successAlert.setContentText("Player " + savedDTO.getName() + " registered successfully!");
        successAlert.showAndWait();
    }

    public void onBackClick(ActionEvent actionEvent) {
        FXMLUtils.switchScene("/com/soccernow/ui/soccernowui/fxml/home/home-screen.fxml",
                (Node) actionEvent.getSource());
    }
}
