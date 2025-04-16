package pt.ul.fc.css.soccernow.service;

import pt.ul.fc.css.soccernow.domain.entities.user.Player;

import java.util.UUID;

public interface PlayerService {
    boolean exists(UUID playerId);
    Player add(Player player);
    Player getById(UUID playerId);
    Player getByName(String name);
    Player update(Player player);
    Player remove(UUID playerId);
}
