package pt.ul.fc.css.soccernow.service.impl;

import org.springframework.stereotype.Service;
import pt.ul.fc.css.soccernow.domain.entities.user.Referee;
import pt.ul.fc.css.soccernow.service.RefereeService;

import java.util.List;
import java.util.UUID;

@Service
public class RefereeServiceImpl implements RefereeService {
    @Override
    public Referee add(Referee entity) {
        return null;
    }

    @Override
    public Referee update(Referee entity) {
        return null;
    }

    @Override
    public void softDelete(UUID entityId) {
    }

    @Override
    public List<Referee> findAllNotDeleted() {
        return List.of();
    }

    @Override
    public Referee findById(UUID entityId) {
        return null;
    }

    @Override
    public Referee findNotDeletedById(UUID entityId) {
        return null;
    }
}
