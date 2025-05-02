package pt.ul.fc.css.soccernow.utils;

import pt.ul.fc.css.soccernow.domain.entities.Address;
import pt.ul.fc.css.soccernow.domain.entities.Team;
import pt.ul.fc.css.soccernow.domain.entities.game.GameTeam;
import pt.ul.fc.css.soccernow.domain.entities.tournament.GamePlayer;
import pt.ul.fc.css.soccernow.domain.entities.user.Player;
import pt.ul.fc.css.soccernow.util.FutsalPositionEnum;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public class GameTeamDataUtil {
    public static GameTeam createGameTeam(Team team) {
        GameTeam gameTeam = new GameTeam();
        gameTeam.setTeam(team);

        List<Player> teamPlayerList = new ArrayList<>(team.getPlayers());
        List<Player> selectedPlayers = teamPlayerList.stream()
                .sorted(Comparator.comparing(Player::getName))
                .limit(5)
                .toList();
        List<GamePlayer> gamePlayers = new ArrayList<>();
        List<FutsalPositionEnum> positions = List.of(
                FutsalPositionEnum.GOALIE,
                FutsalPositionEnum.SWEEPER,
                FutsalPositionEnum.FORWARD,
                FutsalPositionEnum.LEFT_WINGER,
                FutsalPositionEnum.RIGHT_WINGER
        );

        for (int i = 0; i < 5; i++) {
            GamePlayer gamePlayer = new GamePlayer();
            gamePlayer.setPlayer(selectedPlayers.get(i));
            gamePlayer.setPlayedInPosition(positions.get(i));
            gamePlayers.add(gamePlayer);
        }

        gameTeam.setGamePlayers(new HashSet<>(gamePlayers));
        return gameTeam;
    }

    public static Address createAddress() {
        Address address = new Address();
        address.setCity("Lisbon");
        address.setCountry("Portugal");
        address.setPostalCode("1500");
        address.setStreet("Av. Eusébio da Silva Ferreira");
        return address;
    }
}
