package pt.ul.fc.css.soccernow.service.impl;

import org.springframework.stereotype.Service;
import pt.ul.fc.css.soccernow.domain.entities.Team;
import pt.ul.fc.css.soccernow.domain.entities.game.Game;
import pt.ul.fc.css.soccernow.service.GameService;

import java.util.UUID;

@Service
public class GameServiceImpl implements GameService {
    @Override
    public boolean existsById(UUID gameId) {
        return false;
    }

    @Override
    public Game add(Game game) {
        return null;
    }

    @Override
    public Game getById(UUID gameId) {
        return null;
    }

    @Override
    public Game update(Game game) {
        return null;
    }

    @Override
    public void remove(UUID gameId) {
    }
}
