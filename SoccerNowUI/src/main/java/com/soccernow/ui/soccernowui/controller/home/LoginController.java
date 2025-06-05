package com.soccernow.ui.soccernowui.controller.home;

import com.soccernow.ui.soccernowui.SoccerNowApp;
import com.soccernow.ui.soccernowui.api.CredentialsApiController;
import com.soccernow.ui.soccernowui.util.FXMLUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import jakarta.validation.Validator;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    public TextField usernameField;
    public PasswordField passwordField;
    Validator validator;

    public void initialize() {
        validator = SoccerNowApp.getValidatorFactory().getValidator();
    }

    public void onLoginClick(ActionEvent actionEvent) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        CredentialsApiController.LoginRequest loginRequest = new CredentialsApiController.LoginRequest(username, password);
        boolean isValid = FXMLUtils.validateAndShowAlert(loginRequest, validator);
        if (!isValid) {
            return;
        }
        FXMLUtils.executeWithErrorHandling(() -> {
            CredentialsApiController.INSTANCE.login(loginRequest);
            navigateToHomeScreen(actionEvent);
        });
    }

    private void navigateToHomeScreen(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/soccernow/ui/soccernowui/fxml/home/home-screen.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
