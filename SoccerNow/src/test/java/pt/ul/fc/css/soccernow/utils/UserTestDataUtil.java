package pt.ul.fc.css.soccernow.utils;

import java.util.List;
import java.util.Random;

public class UserTestDataUtil {
    public static final long SEED = 42L;

    private static final List<String> SAMPLE_NAMES =
            List.of(
                    "Renan",
                    "Cristiano",
                    "Wellington",
                    "Xavier",
                    "Pedro",
                    "Erling",
                    "Luka",
                    "João",
                    "Bruno",
                    "Diogo");

    private static final List<String> SAMPLE_SURNAMES =
            List.of(
                    "Silva",
                    "Ferreira",
                    "Oliveira",
                    "Angelo",
                    "Rodrigues",
                    "Costa",
                    "Martins",
                    "Sousa",
                    "Reia",
                    "Almeida");

    public static String getRandomUserName(Random random) {
        return "%s %s".formatted(
                SAMPLE_NAMES.get(random.nextInt(SAMPLE_NAMES.size())),
                SAMPLE_SURNAMES.get(random.nextInt(SAMPLE_SURNAMES.size())));
    }
}
