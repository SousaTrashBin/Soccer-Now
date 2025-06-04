package com.soccernow.ui.soccernowui.controller.referee;

import com.soccernow.ui.soccernowui.api.PlayerApiController;
import com.soccernow.ui.soccernowui.api.RefereeApiController;
import com.soccernow.ui.soccernowui.dto.user.PlayerDTO;
import com.soccernow.ui.soccernowui.dto.user.RefereeDTO;
import com.soccernow.ui.soccernowui.util.ErrorException;
import com.soccernow.ui.soccernowui.util.FXMLUtils;
import com.soccernow.ui.soccernowui.util.FutsalPositionEnum;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class RefereeListController {
    @FXML
    private TableColumn<RefereeDTO, String> idColumn;
    @FXML
    private TableColumn<RefereeDTO, String> nameColumn;
    @FXML
    private TableColumn<RefereeDTO, String> hasCertificateColumn;
    @FXML
    private TableView<RefereeDTO> refereeTableView;

    public void initialize() {
        idColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId().toString()));
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        hasCertificateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getHasCertificate() ? "Yes" : "No"));

        loadReferees();
    }

    @FXML
    private void onAddClick(ActionEvent event) {
        FXMLUtils.switchScene(
                "/com/soccernow/ui/soccernowui/fxml/referee/register-referee.fxml",
                (Node) event.getSource()
        );
    }

    private void loadReferees() {
        try {
            List<RefereeDTO> referees = RefereeApiController.INSTANCE.getAllReferees();
            ObservableList<RefereeDTO> refereesObservable = FXCollections.observableArrayList(referees);
            refereeTableView.setItems(refereesObservable);
        } catch (IOException | ErrorException e) {
            System.err.println("Failed to load referees: " + e.getMessage());
            refereeTableView.setItems(FXCollections.observableArrayList());
        }
    }

    @FXML
    private void onEditClick(ActionEvent event) {}

    @FXML
    private void onRefreshClick(ActionEvent event) {
        loadReferees();
    }

    @FXML
    private void onDeleteClick(ActionEvent event) {
        RefereeDTO selectedReferee = refereeTableView.getSelectionModel().getSelectedItem();
        if (selectedReferee == null) {
            return;
        }

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirm Delete");
        confirmAlert.setHeaderText("Delete Referee");
        confirmAlert.setContentText("Are you sure you want to delete " + selectedReferee.getName() + "?");

        Optional<ButtonType> result = confirmAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            FXMLUtils.executeWithErrorHandling(() -> {
                RefereeApiController.INSTANCE.deleteRefereeById(selectedReferee.getId());
                loadReferees();
            });
        }
    }


    @FXML
    public void onBackClick(ActionEvent actionEvent) {
        FXMLUtils.switchScene("/com/soccernow/ui/soccernowui/fxml/home/home-screen.fxml",
                (Node) actionEvent.getSource());
    }
}
