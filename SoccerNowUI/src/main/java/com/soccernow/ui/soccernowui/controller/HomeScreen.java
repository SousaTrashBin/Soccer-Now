package com.soccernow.ui.soccernowui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class HomeScreen {

    @FXML private VBox createOptions;
    @FXML private VBox visualizeOptions;

    @FXML
    private void onCreateClick() {
        boolean isVisible = createOptions.isVisible();
        createOptions.setVisible(!isVisible);
        createOptions.setManaged(!isVisible);

        visualizeOptions.setVisible(false);
        visualizeOptions.setManaged(false);
    }

    @FXML
    private void onVisualizeClick() {
        boolean isVisible = visualizeOptions.isVisible();
        visualizeOptions.setVisible(!isVisible);
        visualizeOptions.setManaged(!isVisible);

        createOptions.setVisible(false);
        createOptions.setManaged(false);
    }
}
