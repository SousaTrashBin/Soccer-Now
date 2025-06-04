package com.soccernow.ui.soccernowui.util;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Set;
import java.util.function.Function;

public class FXMLUtils {

    public static void switchScene(String fxmlPath, Node eventSource) {
        try {
            FXMLLoader loader = new FXMLLoader(FXMLUtils.class.getResource(fxmlPath));
            Parent root = loader.load();

            Stage stage = (Stage) eventSource.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static <T> boolean validateAndShowAlert(T objectToValidate, Validator validator) {
        Set<ConstraintViolation<T>> violations = validator.validate(objectToValidate);

        if (!violations.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder("Please correct the following errors:\n");
            for (ConstraintViolation<T> violation : violations) {
                errorMessage.append("- ").append(violation.getMessage()).append("\n");
            }

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Validation Error");
            alert.setHeaderText("Invalid Input");
            alert.setContentText(errorMessage.toString());
            alert.showAndWait();
            return false;
        }
        return true;
    }
}
