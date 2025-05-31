package pt.ul.fc.css.soccernow.service.impl;

import org.springframework.stereotype.Service;
import pt.ul.fc.css.soccernow.domain.entities.user.Referee;
import pt.ul.fc.css.soccernow.exception.ResourceCouldNotBeDeletedException;
import pt.ul.fc.css.soccernow.exception.ResourceDoesNotExistException;
import pt.ul.fc.css.soccernow.repository.RefereeRepository;
import pt.ul.fc.css.soccernow.service.RefereeService;
import pt.ul.fc.css.soccernow.util.RefereeSearchParams;

import java.util.List;
import java.util.UUID;

@Service
public class RefereeServiceImpl implements RefereeService {

    private final RefereeRepository refereeRepository;

    public RefereeServiceImpl(RefereeRepository refereeRepository) {
        this.refereeRepository = refereeRepository;
    }

    @Override
    public Referee add(Referee entity) {
        Referee newReferee = new Referee();
        newReferee.setHasCertificate(entity.getHasCertificate());
        newReferee.setName(entity.getName());
        return refereeRepository.save(newReferee);
    }

    @Override
    public Referee update(Referee updatedReferee) {
        Referee referee = findNotDeletedById(updatedReferee.getId());
        if (referee.getName() != null) {
            referee.setName(updatedReferee.getName());
        }
        if (updatedReferee.getHasCertificate() != null) {
            referee.setHasCertificate(updatedReferee.getHasCertificate());
        }
        return refereeRepository.save(referee);
    }

    @Override
    public void softDelete(UUID refereeId) {
        Referee referee = findNotDeletedById(refereeId);
        if (referee.hasAnyPendingGames()) {
            throw new ResourceCouldNotBeDeletedException("Referee has a pending game");
        }
        referee.delete();
        refereeRepository.save(referee);
    }

    @Override
    public List<Referee> findAllNotDeleted() {
        return refereeRepository.findAllNotDeleted();
    }

    @Override
    public Referee findById(UUID refereeId) {
        return refereeRepository.findById(refereeId)
                .orElseThrow(() -> new ResourceDoesNotExistException("Referee", "id", refereeId));
    }

    @Override
    public Referee findNotDeletedById(UUID refereeId) {
        Referee referee = findById(refereeId);
        if (referee.isDeleted()) {
            throw new ResourceDoesNotExistException("Referee", "id", refereeId);
        }
        return referee;
    }

    @Override
    public List<Referee> findAllNotDeleted(RefereeSearchParams params) {
        return refereeRepository.findAllNotDeleted(params);
    }
}
