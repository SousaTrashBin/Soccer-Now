package pt.ul.fc.css.soccernow.utils;

import pt.ul.fc.css.soccernow.domain.entities.user.Player;
import pt.ul.fc.css.soccernow.util.FutsalPositionEnum;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import static pt.ul.fc.css.soccernow.utils.UserTestDataUtil.getRandomUserName;

public class PlayerTestDataUtil {

    public static Player createRandomPlayerEntity(Random random) {
        Player player = new Player();
        String randomName = getRandomUserName(random);
        FutsalPositionEnum randomPosition = getRandomFutsalPosition(random);
        player.setName(randomName);
        player.setPreferredPosition(randomPosition);
        return player;
    }

    public static FutsalPositionEnum getRandomFutsalPosition(Random random) {
        FutsalPositionEnum[] values = FutsalPositionEnum.values();
        int randomValue = random.nextInt(values.length + 1);
        return randomValue == values.length ? null : values[randomValue];
    }

    public static List<Player> getPlayers(Random random) {
        return IntStream.range(0, 100)
                .mapToObj(i -> createRandomPlayerEntity(random))
                .toList();
    }

}
