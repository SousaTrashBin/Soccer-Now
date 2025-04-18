package pt.ul.fc.css.soccernow.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pt.ul.fc.css.soccernow.domain.entities.user.Referee;
import pt.ul.fc.css.soccernow.exception.ResourceDoesNotExistException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static pt.ul.fc.css.soccernow.RefereeTestDataUtil.getCertificatedReferees;
import static pt.ul.fc.css.soccernow.RefereeTestDataUtil.getUncertificatedReferees;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class RefereeServiceIntegrationTest {
    List<Referee> certificatedReferees = getCertificatedReferees();
    List<Referee> uncertificatedReferees = getUncertificatedReferees();
    @Autowired
    private RefereeService underTest;

    @Test
    public void testIfRefereeCanBeAdded() {
        Referee referee = certificatedReferees.get(0);
        System.out.println(referee.getName());
        Referee saved = underTest.add(referee);
        assert saved.getId() != null
               && saved.getName()
                       .equals(referee.getName())
               && saved.getHasCertificate()
                       .equals(referee.getHasCertificate());
    }

    @Test
    public void testIfRefereeCanBeDeleted() {
        Referee referee = uncertificatedReferees.get(0);
        Referee saved = underTest.add(referee);
        underTest.softDelete(saved.getId());

        assertThrowsExactly(ResourceDoesNotExistException.class, () -> underTest.findNotDeletedById(saved.getId()));
    }

    @Test
    public void testIfRefereeCanBeUpdated() {
        Referee referee = uncertificatedReferees.get(0);
        Referee saved = underTest.add(referee);

        saved.setName("UPDATED");
        saved.setHasCertificate(!saved.getHasCertificate());
        Referee updated = underTest.update(saved);
        assert updated.getName()
                      .equals("UPDATED")
               && updated.getHasCertificate()
                         .equals(saved.getHasCertificate())
               && updated.getId()
                         .equals(saved.getId());
    }
}
