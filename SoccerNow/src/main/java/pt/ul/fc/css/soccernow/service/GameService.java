package pt.ul.fc.css.soccernow.service;

import org.springframework.transaction.annotation.Transactional;
import pt.ul.fc.css.soccernow.domain.entities.game.Game;
import pt.ul.fc.css.soccernow.domain.entities.game.PlayerGameStats;
import pt.ul.fc.css.soccernow.util.GameSearchParams;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface GameService extends CrudService<Game> {
    @Transactional
    Game closeGame(UUID gameID, Set<PlayerGameStats> playerGameStats);

    List<Game> findAllNotDeleted(GameSearchParams params);

}
