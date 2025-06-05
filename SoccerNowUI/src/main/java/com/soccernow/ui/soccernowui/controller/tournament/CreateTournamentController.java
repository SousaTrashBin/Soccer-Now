package com.soccernow.ui.soccernowui.controller.tournament;

import com.soccernow.ui.soccernowui.SoccerNowApp;
import com.soccernow.ui.soccernowui.api.PointTournamentApiController;
import com.soccernow.ui.soccernowui.util.FXMLUtils;
import jakarta.validation.Validator;
import jakarta.validation.constraints.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.hibernate.validator.constraints.Length;

public class CreateTournamentController {

    @FXML
    private Button BackButton;
    @FXML
    private TextField tournamentNameField;
    private Validator validator;

    @FXML
    public void initialize() {
        this.validator = SoccerNowApp.getValidatorFactory().getValidator();
    }

    @FXML
    public void onCreateClick(ActionEvent event) {
        record TournamentNameWrapper(
                @Pattern(regexp = "^\\p{L}+( \\p{L}+)*$")
                @Length(max = 100)
                String name
        ) {
        }
        TournamentNameWrapper nameWrapper = new TournamentNameWrapper(tournamentNameField.getText());

        boolean isValid = FXMLUtils.validateAndShowAlert(nameWrapper, validator);
        if (!isValid) {
            return;
        }

        FXMLUtils.executeWithErrorHandling(() -> PointTournamentApiController.INSTANCE.createTournament(nameWrapper.name))
                .ifPresent(savedDTO -> {
                    System.out.printf(savedDTO.toString());
                    FXMLUtils.showSuccess("Point Tournament Successfully Created", "Point Tournament " + savedDTO.getName() + " created successfully!");
                });
    }

    @FXML
    public void onBackClick(ActionEvent actionEvent) {
        FXMLUtils.switchScene("/com/soccernow/ui/soccernowui/fxml/home/home-screen.fxml",
                (Node) actionEvent.getSource());
    }
}
