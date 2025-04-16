package pt.ul.fc.css.soccernow.service;

import org.springframework.transaction.annotation.Transactional;
import pt.ul.fc.css.soccernow.domain.entities.Team;
import pt.ul.fc.css.soccernow.domain.entities.game.Game;

import java.util.UUID;

public interface GameService {
    boolean existsById(UUID gameId);
    @Transactional
    Game add(Game game);
    Game getById(UUID gameId);
    @Transactional
    Game update(Game game);
    @Transactional
    void remove(UUID gameId);
}
