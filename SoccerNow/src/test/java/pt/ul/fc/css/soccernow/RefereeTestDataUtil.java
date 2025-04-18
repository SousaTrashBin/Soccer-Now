package pt.ul.fc.css.soccernow;

import pt.ul.fc.css.soccernow.domain.entities.user.Referee;

import java.util.List;
import java.util.stream.IntStream;

import static pt.ul.fc.css.soccernow.UserTestDataUtil.getRandomUserName;

public class RefereeTestDataUtil {
    public static Referee createRandomRefereeEntity(boolean hasCertificate) {
        Referee referee = new Referee();

        String randomName = getRandomUserName();

        referee.setName(randomName);
        referee.setHasCertificate(hasCertificate);
        return referee;
    }

    public static List<Referee> getCertificatedReferees() {
        return getReferees(true);
    }

    public static List<Referee> getUncertificatedReferees() {
        return getReferees(false);
    }

    public static List<Referee> getReferees(boolean hasCertificate) {
        return IntStream.range(0, 50)
                        .mapToObj(i -> createRandomRefereeEntity(hasCertificate))
                        .toList();
    }

}
