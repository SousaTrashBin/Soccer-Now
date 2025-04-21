package pt.ul.fc.css.soccernow.utils;

import pt.ul.fc.css.soccernow.domain.entities.Address;
import pt.ul.fc.css.soccernow.domain.entities.Team;
import pt.ul.fc.css.soccernow.domain.entities.game.GameTeam;
import pt.ul.fc.css.soccernow.domain.entities.tournament.GamePlayer;
import pt.ul.fc.css.soccernow.domain.entities.user.Player;
import pt.ul.fc.css.soccernow.util.FutsalPositionEnum;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static pt.ul.fc.css.soccernow.utils.UserTestDataUtil.RANDOM;

public class GameTeamDataUtil {
    public static GameTeam createGameTeam(Team team) {
        GameTeam gameTeam = new GameTeam();
        gameTeam.setTeam(team);

        List<Player> teamPlayerList = new ArrayList<>(team.getPlayers());
        List<Player> selectedPlayers = RANDOM.ints(0, teamPlayerList.size())
                .distinct()
                .limit(5)
                .mapToObj(teamPlayerList::get)
                .toList();
        List<GamePlayer> gamePlayers = new ArrayList<>();

        GamePlayer goalie = new GamePlayer();
        goalie.setPlayer(selectedPlayers.get(0));
        goalie.setPlayedInPosition(FutsalPositionEnum.GOALIE);
        gamePlayers.add(goalie);
        List<FutsalPositionEnum> positions = List.of(
                FutsalPositionEnum.SWEEPER,
                FutsalPositionEnum.FORWARD,
                FutsalPositionEnum.LEFT_WINGER,
                FutsalPositionEnum.RIGHT_WINGER
        );

        for (int i = 1; i < 5; i++) {
            GamePlayer gamePlayer = new GamePlayer();
            gamePlayer.setPlayer(selectedPlayers.get(i));
            gamePlayer.setPlayedInPosition(positions.get(i - 1));
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
