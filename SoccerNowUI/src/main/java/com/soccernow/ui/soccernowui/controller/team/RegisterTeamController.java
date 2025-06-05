package com.soccernow.ui.soccernowui.controller.team;

import com.soccernow.ui.soccernowui.SoccerNowApp;
import com.soccernow.ui.soccernowui.api.TeamApiController;
import com.soccernow.ui.soccernowui.dto.TeamDTO;
import com.soccernow.ui.soccernowui.util.FXMLUtils;
import jakarta.validation.Validator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;

public class RegisterTeamController {

    @FXML
    private TextField teamNameField;
    private Validator validator;

    @FXML
    private void initialize() {
        this.validator = SoccerNowApp.getValidatorFactory().getValidator();
    }

    @FXML
    public void onRegisterClick(ActionEvent actionEvent) {
        TeamDTO teamDTO = new TeamDTO();
        teamDTO.setName(teamNameField.getText());

        boolean isValid = FXMLUtils.validateAndShowAlert(teamDTO, validator);
        if (!isValid) {
            return;
        }

        FXMLUtils.executeWithErrorHandling(() -> TeamApiController.INSTANCE.registerTeam(teamDTO))
                .ifPresent(savedDTO -> {
                    System.out.printf(savedDTO.toString());
                    FXMLUtils.showSuccess("Team Successfully Registered", "Team " + savedDTO.getName() + " registered successfully!");
                    FXMLUtils.switchScene("/com/soccernow/ui/soccernowui/fxml/team/team-list.fxml",
                            (Node) actionEvent.getSource());
                });
    }

    @FXML
    public void onBackClick(ActionEvent actionEvent) {
        FXMLUtils.switchScene("/com/soccernow/ui/soccernowui/fxml/home/home-screen.fxml",
                (Node) actionEvent.getSource());
    }
}
