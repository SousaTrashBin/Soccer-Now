package com.soccernow.ui.soccernowui.controller.game;

import com.soccernow.ui.soccernowui.SoccerNowApp;
import com.soccernow.ui.soccernowui.api.GameApiController;
import com.soccernow.ui.soccernowui.api.PlayerApiController;
import com.soccernow.ui.soccernowui.api.RefereeApiController;
import com.soccernow.ui.soccernowui.api.TeamApiController;
import com.soccernow.ui.soccernowui.dto.TeamDTO;
import com.soccernow.ui.soccernowui.dto.TeamInfoDTO;
import com.soccernow.ui.soccernowui.dto.games.AddressDTO;
import com.soccernow.ui.soccernowui.dto.games.GameDTO;
import com.soccernow.ui.soccernowui.dto.games.GamePlayerDTO;
import com.soccernow.ui.soccernowui.dto.games.GameTeamDTO;
import com.soccernow.ui.soccernowui.dto.user.PlayerInfoDTO;
import com.soccernow.ui.soccernowui.dto.user.RefereeDTO;
import com.soccernow.ui.soccernowui.dto.user.RefereeInfoDTO;
import com.soccernow.ui.soccernowui.util.FXMLUtils;
import com.soccernow.ui.soccernowui.util.FutsalPositionEnum;
import jakarta.validation.Validator;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CreateGameController {
    private List<TeamDTO> allTeams;
    private ObservableList<TeamDTO> availableTeamsForTeamOne;
    private ObservableList<TeamDTO> availableTeamsForTeamTwo;

    @FXML private ComboBox<TeamDTO> teamOneComboBox;
    @FXML private ComboBox<PlayerInfoDTO> teamOneGoalieComboBox;
    @FXML private ComboBox<PlayerInfoDTO> teamOneSweeperComboBox;
    @FXML private ComboBox<PlayerInfoDTO> teamOneLeftWingerComboBox;
    @FXML private ComboBox<PlayerInfoDTO> teamOneRightWingerComboBox;
    @FXML private ComboBox<PlayerInfoDTO> teamOneForwardComboBox;
    private List<ComboBox<PlayerInfoDTO>> teamOnePlayerComboBoxes;
    private List<PlayerInfoDTO> teamOnePlayers = new ArrayList<>();

    @FXML private ComboBox<TeamDTO> teamTwoComboBox;
    @FXML private ComboBox<PlayerInfoDTO> teamTwoGoalieComboBox;
    @FXML private ComboBox<PlayerInfoDTO> teamTwoSweeperComboBox;
    @FXML private ComboBox<PlayerInfoDTO> teamTwoLeftWingerComboBox;
    @FXML private ComboBox<PlayerInfoDTO> teamTwoRightWingerComboBox;
    @FXML private ComboBox<PlayerInfoDTO> teamTwoForwardComboBox;
    private List<ComboBox<PlayerInfoDTO>> teamTwoPlayerComboBoxes;
    private List<PlayerInfoDTO> teamTwoPlayers = new ArrayList<>();

    private List<RefereeDTO> allReferees = new ArrayList<>();
    private ObservableList<RefereeDTO> otherRefereesObservableList;
    private ObservableList<RefereeDTO> secondaryRefereesObservableList;

    @FXML private ComboBox<RefereeDTO> primaryRefereeComboBox;
    @FXML private TableView<RefereeDTO> secondaryRefereesTableView;
    @FXML private TableColumn<RefereeDTO, String> secondaryRefereesIdColumn;
    @FXML private TableColumn<RefereeDTO, String> secondaryRefereesNameColumn;

    @FXML private TableView<RefereeDTO> otherRefereesTableView;
    @FXML private TableColumn<RefereeDTO, String> otherRefereesIdColumn;
    @FXML private TableColumn<RefereeDTO, String> otherRefereesNameColumn;

    @FXML private TextField countryField;
    @FXML private TextField cityField;
    @FXML private TextField streetField;
    @FXML private TextField postalCodeField;
    @FXML private DatePicker datePicker;
    @FXML private TextField timeField;
    private boolean updatingTeamTwoComboBox;
    private boolean updatingTeamOneComboBox;
    private Validator validator;

    @FXML
    private void initialize() {
        secondaryRefereesIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId().toString()));
        secondaryRefereesNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));

        otherRefereesIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId().toString()));
        otherRefereesNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));

        teamOnePlayerComboBoxes = List.of(teamOneGoalieComboBox, teamOneSweeperComboBox, teamOneLeftWingerComboBox, teamOneRightWingerComboBox, teamOneForwardComboBox);
        teamTwoPlayerComboBoxes = List.of(teamTwoGoalieComboBox, teamTwoSweeperComboBox, teamTwoLeftWingerComboBox, teamTwoRightWingerComboBox, teamTwoForwardComboBox);

        availableTeamsForTeamOne = FXCollections.observableArrayList();
        availableTeamsForTeamTwo = FXCollections.observableArrayList();

        teamOneComboBox.setItems(availableTeamsForTeamOne);
        teamTwoComboBox.setItems(availableTeamsForTeamTwo);

        FXMLUtils.executeWithErrorHandling(TeamApiController.INSTANCE::getAllTeams)
                .ifPresent(teams -> {
                    this.allTeams = teams;
                    availableTeamsForTeamOne.setAll(new ArrayList<>(allTeams));
                    availableTeamsForTeamTwo.setAll(new ArrayList<>(allTeams));
                });

        otherRefereesObservableList = FXCollections.observableArrayList();
        otherRefereesTableView.setItems(otherRefereesObservableList);

        secondaryRefereesObservableList = FXCollections.observableArrayList();
        secondaryRefereesTableView.setItems(secondaryRefereesObservableList);

        FXMLUtils.executeWithErrorHandling(RefereeApiController.INSTANCE::getAllReferees)
                .ifPresent(referees -> {
                    this.allReferees = referees;
                    primaryRefereeComboBox.setItems(FXCollections.observableArrayList(allReferees));
                    otherRefereesTableView.setItems(FXCollections.observableArrayList(allReferees));
                });

        teamOneComboBox.valueProperty().addListener((obs, oldTeam, newTeam) -> {
            updateTeamOnePlayers();
        });

        teamTwoComboBox.valueProperty().addListener((obs, oldTeam, newTeam) -> {
            updateTeamTwoPlayers();
        });

        this.validator = SoccerNowApp.getValidatorFactory().getValidator();
    }

    private void updateTeamOnePlayers() {
        TeamDTO selectedTeam = teamOneComboBox.getSelectionModel().getSelectedItem();
        List<PlayerInfoDTO> players = (selectedTeam != null) ? selectedTeam.getPlayers().stream().toList() : new ArrayList<>();

        for (ComboBox<PlayerInfoDTO> playerComboBox : teamOnePlayerComboBoxes) {
            playerComboBox.getItems().setAll(players);
            playerComboBox.getSelectionModel().clearSelection();
        }
    }

    private void updateTeamTwoPlayers() {
        TeamDTO selectedTeam = teamTwoComboBox.getSelectionModel().getSelectedItem();
        List<PlayerInfoDTO> players = (selectedTeam != null) ? selectedTeam.getPlayers().stream().toList() : new ArrayList<>();

        for (ComboBox<PlayerInfoDTO> playerComboBox : teamTwoPlayerComboBoxes) {
            playerComboBox.getItems().setAll(players);
            playerComboBox.getSelectionModel().clearSelection();
        }
    }


    @FXML
    public void onCreateGameClick(ActionEvent event) {
        LocalDateTime gameDateTime = getGameDateTime();
        AddressDTO addressDTO = getAddressFromFields();

        if (!validateInputs(gameDateTime, addressDTO)) return;

        RefereeInfoDTO primaryReferee = mapPrimaryReferee();
        Set<RefereeInfoDTO> secondaryReferees = mapSecondaryReferees();

        GameTeamDTO teamOne = buildGameTeam(teamOneComboBox, teamOneGoalieComboBox, teamOneSweeperComboBox,
                teamOneLeftWingerComboBox, teamOneRightWingerComboBox, teamOneForwardComboBox, "Team One");

        if (teamOne == null) return;

        GameTeamDTO teamTwo = buildGameTeam(teamTwoComboBox, teamTwoGoalieComboBox, teamTwoSweeperComboBox,
                teamTwoLeftWingerComboBox, teamTwoRightWingerComboBox, teamTwoForwardComboBox, "Team Two");

        if (teamTwo == null) return;

        GameDTO gameDTO = new GameDTO();
        gameDTO.setHappensIn(gameDateTime);
        gameDTO.setLocatedIn(addressDTO);
        gameDTO.setPrimaryReferee(primaryReferee);
        gameDTO.setSecondaryReferees(secondaryReferees);
        gameDTO.setGameTeamOne(teamOne);
        gameDTO.setGameTeamTwo(teamTwo);

        boolean isValid = FXMLUtils.validateAndShowAlert(gameDTO, validator);
        if (!isValid) {
            return;
        }

        FXMLUtils.executeWithErrorHandling(() -> GameApiController.INSTANCE.registerGame(gameDTO))
                .ifPresent(savedDTO -> {
                    System.out.printf(savedDTO.toString());
                    FXMLUtils.showSuccess("Game Successfully Created", "Game " + savedDTO.getId() + " successfully created!");
                });

        FXMLUtils.switchScene("/com/soccernow/ui/soccernowui/fxml/home/home-screen.fxml",
                (Node) event.getSource());
    }

    private boolean validateInputs(LocalDateTime dateTime, AddressDTO address) {
        if (dateTime == null) {
            System.out.println("Invalid gameDateTime");
            return false;
        }
        if (address == null) {
            System.out.println("Invalid addressDTO");
            return false;
        }
        if (primaryRefereeComboBox.getSelectionModel().getSelectedItem() == null) {
            System.out.println("Invalid primaryReferee");
            return false;
        }
        if (teamOneComboBox.getSelectionModel().getSelectedItem() == null) {
            System.out.println("Invalid team one");
            return false;
        }
        if (teamTwoComboBox.getSelectionModel().getSelectedItem() == null) {
            System.out.println("Invalid team two");
            return false;
        }
        return true;
    }

    private RefereeInfoDTO mapPrimaryReferee() {
        RefereeDTO selected = primaryRefereeComboBox.getSelectionModel().getSelectedItem();
        RefereeInfoDTO info = new RefereeInfoDTO();
        info.setId(selected.getId());
        return info;
    }

    private Set<RefereeInfoDTO> mapSecondaryReferees() {
        return secondaryRefereesTableView.getItems().stream()
                .map(ref -> {
                    RefereeInfoDTO info = new RefereeInfoDTO();
                    info.setId(ref.getId());
                    return info;
                })
                .collect(Collectors.toSet());
    }

    private GameTeamDTO buildGameTeam(
            ComboBox<TeamDTO> teamComboBox,
            ComboBox<PlayerInfoDTO> goalie,
            ComboBox<PlayerInfoDTO> sweeper,
            ComboBox<PlayerInfoDTO> leftWinger,
            ComboBox<PlayerInfoDTO> rightWinger,
            ComboBox<PlayerInfoDTO> forward,
            String teamLabel
    ) {
        List<GamePlayerDTO> gamePlayers = List.of(
                new GamePlayerDTO(FutsalPositionEnum.GOALIE, goalie.getValue()),
                new GamePlayerDTO(FutsalPositionEnum.SWEEPER, sweeper.getValue()),
                new GamePlayerDTO(FutsalPositionEnum.LEFT_WINGER, leftWinger.getValue()),
                new GamePlayerDTO(FutsalPositionEnum.RIGHT_WINGER, rightWinger.getValue()),
                new GamePlayerDTO(FutsalPositionEnum.FORWARD, forward.getValue())
        );

        if (gamePlayers.stream().anyMatch(dto -> dto.getPlayer() == null)) {
            System.out.println(teamLabel + " must have 5 players selected.");
            return null;
        }

        Set<GamePlayerDTO> gamePlayerSet = gamePlayers.stream()
                .map(dto -> dto.setPlayer(new PlayerInfoDTO(dto.getPlayer().getId())))
                .collect(Collectors.toSet());

        GameTeamDTO team = new GameTeamDTO();
        TeamInfoDTO teamInfo = new TeamInfoDTO();
        teamInfo.setId(teamComboBox.getSelectionModel().getSelectedItem().getId());
        team.setTeam(teamInfo);
        team.setGamePlayers(gamePlayerSet);
        return team;
    }

    @FXML
    public void onBackClick(ActionEvent actionEvent) {
        FXMLUtils.switchScene("/com/soccernow/ui/soccernowui/fxml/home/home-screen.fxml",
                (Node) actionEvent.getSource());
    }

    @FXML
    public void onRemoveRefereeClick(ActionEvent actionEvent) {
        RefereeDTO selectedReferee = secondaryRefereesTableView.getSelectionModel().getSelectedItem();
        if (selectedReferee == null) {
            return;
        }
        this.otherRefereesTableView.getItems().add(selectedReferee);
        this.secondaryRefereesTableView.getItems().remove(selectedReferee);
    }

    @FXML
    public void onAddRefereeClick(ActionEvent actionEvent) {
        RefereeDTO selectedReferee = otherRefereesTableView.getSelectionModel().getSelectedItem();
        if (selectedReferee == null) {
            return;
        }
        this.secondaryRefereesTableView.getItems().add(selectedReferee);
        this.otherRefereesTableView.getItems().remove(selectedReferee);
    }

    private LocalDateTime getGameDateTime() {
        LocalDate date = datePicker.getValue();
        String timeText = timeField.getText();

        if (date == null || timeText == null || timeText.isBlank()) {
            return null;
        }

        try {
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
            LocalTime time = LocalTime.parse(timeText, timeFormatter);

            return LocalDateTime.of(date, time);
        } catch (DateTimeParseException e) {
            System.err.println("Invalid time format. Expected HH:mm.");
            return null;
        }
    }

    private AddressDTO getAddressFromFields() {
        String country = countryField.getText();
        String city = cityField.getText();
        String street = streetField.getText();
        String postalCode = postalCodeField.getText();

        if (street == null || street.isBlank()) {
            return null;
        }

        return new AddressDTO(country, city, street, postalCode);
    }
}