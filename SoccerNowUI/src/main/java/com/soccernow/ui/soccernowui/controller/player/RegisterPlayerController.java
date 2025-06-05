package com.soccernow.ui.soccernowui.controller.player;

import com.soccernow.ui.soccernowui.SoccerNowApp;
import com.soccernow.ui.soccernowui.api.PlayerApiController;
import com.soccernow.ui.soccernowui.dto.user.PlayerDTO;
import com.soccernow.ui.soccernowui.util.FXMLUtils;
import com.soccernow.ui.soccernowui.util.FutsalPositionEnum;
import jakarta.validation.Validator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

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
            return;
        }

        FXMLUtils.executeWithErrorHandling(() -> PlayerApiController.INSTANCE.registerPlayer(playerDTO))
                .ifPresent(savedDTO -> {
                    System.out.printf(savedDTO.toString());
                    FXMLUtils.showSuccess("Player Successfully Registered", "Player " + savedDTO.getName() + " registered successfully!");
                });
    }

    public void onBackClick(ActionEvent actionEvent) {
        FXMLUtils.switchScene("/com/soccernow/ui/soccernowui/fxml/home/home-screen.fxml",
                (Node) actionEvent.getSource());
    }
}
