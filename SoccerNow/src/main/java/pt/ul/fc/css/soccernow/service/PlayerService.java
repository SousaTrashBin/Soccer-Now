package pt.ul.fc.css.soccernow.service;

import org.springframework.transaction.annotation.Transactional;
import pt.ul.fc.css.soccernow.domain.entities.Team;
import pt.ul.fc.css.soccernow.domain.entities.user.Player;

import java.util.List;
import java.util.UUID;

public interface PlayerService {
    boolean existsById(UUID playerId);
    @Transactional
    Player add(Player player);
    Player getById(UUID playerId);
    List<Player> getByName(String playerName);
    @Transactional
    Player update(Player player);
    @Transactional
    void remove(UUID playerId);
}
