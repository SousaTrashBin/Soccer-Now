package pt.ul.fc.css.soccernow.service;

import jakarta.validation.constraints.NotNull;
import pt.ul.fc.css.soccernow.domain.entities.user.Player;
import pt.ul.fc.css.soccernow.util.PlayerSearchParams;

import java.util.List;

public interface PlayerService extends CrudService<Player> {
    List<Player> findNotDeletedByName(@NotNull String playerName);

    List<Player> findAllNotDeleted(PlayerSearchParams params);
}
