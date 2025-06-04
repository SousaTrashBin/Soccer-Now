package com.soccernow.ui.soccernowui;

import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;

public class SoccerNowApp extends Application {
    private static ValidatorFactory validatorFactory;

    @Override
    public void start(Stage stage) throws IOException {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        FXMLLoader fxmlLoader = new FXMLLoader(SoccerNowApp.class.getResource("fxml/home/home-screen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 300, 400);

        scene.getRoot().setStyle("-fx-background-color: white;");
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());

        stage.setTitle("Soccer Now");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() {
        if (validatorFactory != null) {
            validatorFactory.close();
        }
    }

    public static ValidatorFactory getValidatorFactory() {
        return validatorFactory;
    }

    public static void main(String[] args) {
        launch();
    }
}
