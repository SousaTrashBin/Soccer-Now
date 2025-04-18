package pt.ul.fc.css.soccernow;

import java.util.List;
import java.util.Random;

public class UserTestDataUtil {
    public static final long SEED = 42L;
    public static final Random RANDOM = new Random(SEED);

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

    public static String getRandomUserName() {
        return "%s %s"
                .formatted(
                        SAMPLE_NAMES.get(RANDOM.nextInt(SAMPLE_NAMES.size())),
                        SAMPLE_SURNAMES.get(RANDOM.nextInt(SAMPLE_SURNAMES.size())));
    }
}
