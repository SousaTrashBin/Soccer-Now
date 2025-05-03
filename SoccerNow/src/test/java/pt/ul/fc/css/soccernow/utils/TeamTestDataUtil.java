package pt.ul.fc.css.soccernow.utils;

import pt.ul.fc.css.soccernow.domain.entities.Team;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class TeamTestDataUtil {
    private static final List<String> RANDOM_TEAM_NAMES = List.of(
            "Sporting CP",
            "SL Benfica",
            "FC Porto",
            "SC Braga",
            "AD Fundão",
            "Leões Porto Salvo",
            "Eléctrico FC",
            "Viseu",
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

    public static Team createRandomTeam(Random random) {
        Team team = new Team();
        team.setName(getRandomClubName(random));
        return team;
    }

    public static String getRandomClubName(Random random) {
        int randomIndex = random.nextInt(RANDOM_TEAM_NAMES.size());
        return RANDOM_TEAM_NAMES.get(randomIndex);
    }

    public static List<Team> getTeams(Random random) {
        return IntStream.range(0, 20).mapToObj(i -> createRandomTeam(random)).toList();
    }
}
