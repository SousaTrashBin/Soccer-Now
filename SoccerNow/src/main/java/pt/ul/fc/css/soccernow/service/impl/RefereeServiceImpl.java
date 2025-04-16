package pt.ul.fc.css.soccernow.service.impl;

import org.springframework.stereotype.Service;
import pt.ul.fc.css.soccernow.domain.entities.user.Referee;
import pt.ul.fc.css.soccernow.service.RefereeService;

import java.util.List;
import java.util.UUID;

@Service
public class RefereeServiceImpl implements RefereeService {
    @Override
    public boolean existsById(UUID refereeId) {
        return false;
    }

    @Override
    public boolean existsByName(String refereeName) {
        return false;
    }

    @Override
    public Referee add(Referee referee) {
        return null;
    }

    @Override
    public Referee getById(UUID refereeId) {
        return null;
    }

    @Override
    public List<Referee> getByName(String refereeName) {
        return List.of();
    }

    @Override
    public Referee update(Referee referee) {
        return null;
    }

    @Override
    public void remove(UUID refereeId) {
    }
}
