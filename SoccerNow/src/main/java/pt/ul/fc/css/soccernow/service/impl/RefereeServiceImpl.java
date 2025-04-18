package pt.ul.fc.css.soccernow.service.impl;

import org.springframework.stereotype.Service;
import pt.ul.fc.css.soccernow.domain.entities.user.Referee;
import pt.ul.fc.css.soccernow.exception.ResourceCouldNotBeDeletedException;
import pt.ul.fc.css.soccernow.exception.ResourceDoesNotExistException;
import pt.ul.fc.css.soccernow.repository.RefereeRepository;
import pt.ul.fc.css.soccernow.service.RefereeService;

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
    public Referee update(Referee entity) {
        Referee referee = findNotDeletedById(entity.getId());
        if (referee.getName() != null) {
            referee.setName(entity.getName());
        }
        if (entity.getHasCertificate() != null) {
            referee.setHasCertificate(entity.getHasCertificate());
        }
        return refereeRepository.save(referee);
    }

    @Override
    public void softDelete(UUID entityId) {
        Referee referee = findNotDeletedById(entityId);
        if (referee.hasAnyPendingGames()) {
            throw new ResourceCouldNotBeDeletedException("Referee %s has pending games".formatted(referee.getName()));
        }
        referee.delete();
        refereeRepository.save(referee);
    }

    @Override
    public List<Referee> findAllNotDeleted() {
        return refereeRepository.findAllNotDeleted();
    }

    @Override
    public Referee findById(UUID entityId) {
        return refereeRepository.findById(entityId)
                                .orElseThrow(() -> new ResourceDoesNotExistException("Referee", "id", entityId));
    }

    @Override
    public Referee findNotDeletedById(UUID entityId) {
        Referee referee = findById(entityId);
        if (referee.isDeleted()) {
            throw new ResourceDoesNotExistException("Referee", "id", entityId);
        }
        return referee;
    }
}
