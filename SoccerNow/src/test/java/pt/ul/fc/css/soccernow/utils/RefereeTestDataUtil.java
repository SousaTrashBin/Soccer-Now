package pt.ul.fc.css.soccernow.utils;

import pt.ul.fc.css.soccernow.domain.entities.user.Referee;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import static pt.ul.fc.css.soccernow.utils.UserTestDataUtil.getRandomUserName;

public class RefereeTestDataUtil {
    public static Referee createRandomRefereeEntity(boolean hasCertificate, Random random) {
        Referee referee = new Referee();
        String randomName = getRandomUserName(random);
        referee.setName(randomName);
        referee.setHasCertificate(hasCertificate);
        return referee;
    }

    public static List<Referee> getCertificatedReferees(Random random) {
        return getReferees(true, random);
    }

    public static List<Referee> getUncertificatedReferees(Random random) {
        return getReferees(false, random);
    }

    public static List<Referee> getReferees(boolean hasCertificate, Random random) {
        return IntStream.range(0, 50)
                .mapToObj(i -> createRandomRefereeEntity(hasCertificate, random))
                .toList();
    }

}
