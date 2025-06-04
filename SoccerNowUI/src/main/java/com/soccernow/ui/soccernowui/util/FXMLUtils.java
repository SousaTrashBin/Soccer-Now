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
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

public class FXMLUtils {

    public static void switchScene(String fxmlPath, Node sourceNode) {
        switchScene(fxmlPath, sourceNode, controller -> {});
    }
    public static void switchScene(String fxmlPath, Node sourceNode, Consumer<Object> controllerInitializer) {
        try {
            FXMLLoader loader = new FXMLLoader(FXMLUtils.class.getResource(fxmlPath));
            Parent root = loader.load();

            Object controller = loader.getController();
            if (controllerInitializer != null) {
                controllerInitializer.accept(controller);
            }

            Stage stage = (Stage) sourceNode.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            showError("Scene Load Error", "Could not load the scene: " + e.getMessage());
        }
    }

    public static void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static <T> boolean validateAndShowAlert(T objectToValidate, Validator validator) {
        Set<ConstraintViolation<T>> violations = validator.validate(objectToValidate);

        if (!violations.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder("Please correct the following errors:\n");
            for (ConstraintViolation<T> violation : violations) {
                errorMessage.append("- ").append(violation.getMessage()).append("\n");
            }

            showError("Validation Error", errorMessage.toString());
            System.err.println("Validation failed for: " + objectToValidate);
            return false;
        }
        return true;
    }

    public static <T> Optional<T> executeWithErrorHandling(SupplierWithExceptions<T> supplier) {
        try {
            return Optional.of(supplier.get());
        } catch (IOException e) {
            showError("Connection Error", "Unable to connect to the server. Please check your internet connection and try again.");
        } catch (ErrorException e) {
            String message = e.getErrorMessage();
            int statusCode = e.getStatusCode();
            showError("Server Error (" + statusCode + ")", message);
        } catch (Exception e) {
            showError("Unexpected Error", e.getMessage());
        }
        return Optional.empty();
    }

    @FunctionalInterface
    public interface SupplierWithExceptions<T> {
        T get() throws Exception;
    }

    public static void showSuccess(String header, String content) {
        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
        successAlert.setTitle("Success");
        successAlert.setHeaderText(header);
        successAlert.setContentText(content);
        successAlert.showAndWait();
    }
}
