package pt.ul.fc.css.soccernow.service;

import org.springframework.transaction.annotation.Transactional;
import pt.ul.fc.css.soccernow.domain.entities.user.Referee;

import java.util.List;
import java.util.UUID;

public interface RefereeService {
    boolean existsById(UUID refereeId);
    boolean existsByName(String refereeName);
    @Transactional
    Referee add(Referee referee);
    Referee getById(UUID refereeId);
    List<Referee> getByName(String refereeName);
    @Transactional
    Referee update(Referee referee);
    @Transactional
    void remove(UUID refereeId);
}
