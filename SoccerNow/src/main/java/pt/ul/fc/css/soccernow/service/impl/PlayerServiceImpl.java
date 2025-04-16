package pt.ul.fc.css.soccernow.service.impl;

import org.springframework.stereotype.Service;
import pt.ul.fc.css.soccernow.domain.entities.user.Player;
import pt.ul.fc.css.soccernow.exception.ResourceAlreadyExistsException;
import pt.ul.fc.css.soccernow.repository.PlayerRepository;
import pt.ul.fc.css.soccernow.service.PlayerService;

import java.util.UUID;

@Service
public class PlayerServiceImpl implements PlayerService {
    private final PlayerRepository playerRepository;

    public PlayerServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public boolean exists(UUID playerId) {
        return playerRepository.existsById(playerId);
    }

    @Override
    public Player add(Player player) {
        Player newPlayer = new Player();
        newPlayer.setName(player.getName());
        newPlayer.setPreferredPosition(player.getPreferredPosition());
        return playerRepository.save(newPlayer); //201
    }

    @Override
    public Player getById(UUID playerId) {
        return null;
    }

    @Override
    public Player getByName(String name) {
        return null;
    }

    @Override
    public Player update(Player player) {
        return null;
    }

    @Override
    public Player remove(UUID playerId) {
        return null;
    }
}
