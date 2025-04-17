package pt.ul.fc.css.soccernow;

import pt.ul.fc.css.soccernow.domain.entities.user.Player;
import pt.ul.fc.css.soccernow.util.FutsalPositionEnum;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class PlayerTestDataUtil {
    private static final long SEED = 42L;
    private static final Random RANDOM = new Random(SEED);

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

    public static Player createRandomPlayerEntity() {
        Player player = new Player();

        String randomName = getRandomPlayerName();
        FutsalPositionEnum randomPosition = getRandomFutsalPosition();

        player.setName(randomName);
        player.setPreferredPosition(randomPosition);
        return player;
    }

    public static String getRandomPlayerName() {
        return "%s %s"
                .formatted(
                        SAMPLE_NAMES.get(RANDOM.nextInt(SAMPLE_NAMES.size())),
                        SAMPLE_SURNAMES.get(RANDOM.nextInt(SAMPLE_SURNAMES.size())));
    }

    public static FutsalPositionEnum getRandomFutsalPosition() {
        FutsalPositionEnum[] values = FutsalPositionEnum.values();
        int randomValue = RANDOM.nextInt(values.length + 1);
        return randomValue == values.length ? null : values[randomValue];
    }

    public static List<Player> getPlayers() {
        return IntStream.range(0, 100)
                        .mapToObj(i -> createRandomPlayerEntity())
                        .toList();
    }
}
