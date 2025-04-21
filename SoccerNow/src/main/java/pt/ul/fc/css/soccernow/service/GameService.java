package pt.ul.fc.css.soccernow.service;

import org.springframework.transaction.annotation.Transactional;
import pt.ul.fc.css.soccernow.domain.entities.game.Game;
import pt.ul.fc.css.soccernow.domain.entities.game.PlayerGameStats;

import java.util.Set;
import java.util.UUID;

public interface GameService extends CrudService<Game> {
    @Transactional
    Game closeGame(UUID gameID, Set<PlayerGameStats> playerGameStats);
}
