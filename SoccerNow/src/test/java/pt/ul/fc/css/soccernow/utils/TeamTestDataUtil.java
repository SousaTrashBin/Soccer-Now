package pt.ul.fc.css.soccernow.utils;

import pt.ul.fc.css.soccernow.domain.entities.Team;

import java.util.List;
import java.util.stream.IntStream;

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

    public static Team createRandomTeam() {
        Team team = new Team();
        team.setName(getRandomClubName());
        return team;
    }

    public static String getRandomClubName() {
        int randomIndex = RANDOM.nextInt(RANDOM_TEAM_NAMES.size());
        return RANDOM_TEAM_NAMES.get(randomIndex);
    }

    public static List<Team> getTeams() {
        return IntStream.range(0, 20).mapToObj(i -> createRandomTeam()).toList();
    }
}
