package pt.ul.fc.css.soccernow.service.impl;

import org.springframework.stereotype.Service;
import pt.ul.fc.css.soccernow.domain.entities.game.Game;
import pt.ul.fc.css.soccernow.service.GameService;

import java.util.List;
import java.util.UUID;

@Service
public class GameServiceImpl implements GameService {
    @Override
    public Game add(Game entity) {
        return null;
    }

    @Override
    public Game update(Game entity) {
        return null;
    }

    @Override
    public void softDelete(UUID entityId) {

    }

    @Override
    public List<Game> findAllNotDeleted() {
        return List.of();
    }

    @Override
    public Game findById(UUID entityId) {
        return null;
    }

    @Override
    public Game findNotDeletedById(UUID entityId) {
        return null;
    }
}
