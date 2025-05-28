package pt.ul.fc.css.soccernow.repository;

import pt.ul.fc.css.soccernow.domain.entities.game.Game;
import pt.ul.fc.css.soccernow.util.GameSearchParams;

import java.util.ArrayList;
import java.util.List;

public interface GameRepository extends SoftDeletedRepository<Game> {
    default List<Game> findAllNotDeleted(GameSearchParams params) {
        return new ArrayList<>();
    }
}
