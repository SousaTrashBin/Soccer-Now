package pt.ul.fc.css.soccernow.utils;

import pt.ul.fc.css.soccernow.domain.entities.Team;
import pt.ul.fc.css.soccernow.domain.entities.user.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static pt.ul.fc.css.soccernow.utils.UserTestDataUtil.RANDOM;

public class TeamTestDataUtil {
    private static final List<String> RANDOM_TEAM_NAMES = List.of(
            "Sporting CP",
            "SL Benfica",
            "FC Porto",
            "SC Braga",
            "AD Fundão",
            "Leões Porto Salvo",
            "Eléctrico FC",
            "Viseu 2001",
            "Modicus Sandim",
            "Ferreira do Zêzere",
            "Amarense",
            "Os Belenenses",
            "Rio Ave",
            "Boavista FC",
            "Académica Coimbra",
            "Vitória SC",
            "Marítimo",
            "Fabril Barreiro",
            "Pinhalnovense",
            "Burinhosa"
    );

    public static Team createRandomTeam(Set<Player> players) {
        Team team = new Team();
        team.setName(getRandomClubName());
        team.setPlayers(players);
        return team;
    }

    public static String getRandomClubName() {
        int randomIndex = RANDOM.nextInt(RANDOM_TEAM_NAMES.size());
        return RANDOM_TEAM_NAMES.get(randomIndex);
    }

    public static List<Team> createTeams(Set<Player> players) {
        List<Player> playerList = List.copyOf(players);
        List<Team> teams = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            int subListSize = 5 + RANDOM.nextInt(Math.max(1, Math.min(11, playerList.size() - 5)));
            Set<Player> randomPlayers = RANDOM.ints(0, playerList.size())
                    .distinct()
                    .limit(subListSize)
                    .mapToObj(playerList::get)
                    .collect(Collectors.toSet());

            teams.add(createRandomTeam(randomPlayers));
        }

        return teams;
    }
}
