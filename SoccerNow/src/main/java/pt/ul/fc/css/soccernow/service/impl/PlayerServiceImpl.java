package pt.ul.fc.css.soccernow.service.impl;

import org.springframework.stereotype.Service;
import pt.ul.fc.css.soccernow.domain.entities.user.Player;
import pt.ul.fc.css.soccernow.exception.ResourceCouldNotBeDeletedException;
import pt.ul.fc.css.soccernow.exception.ResourceDoesNotExistException;
import pt.ul.fc.css.soccernow.repository.PlayerRepository;
import pt.ul.fc.css.soccernow.service.PlayerService;

import java.util.List;
import java.util.UUID;

@Service
public class PlayerServiceImpl implements PlayerService {
    private final PlayerRepository playerRepository;

    public PlayerServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public Player add(Player player) {
        Player newPlayer = new Player();
        newPlayer.setName(player.getName());
        newPlayer.setPreferredPosition(player.getPreferredPosition());
        return playerRepository.save(newPlayer);
    }

    @Override
    public Player findNotDeletedById(UUID playerId) {
        Player player = findById(playerId);
        if (player.isDeleted()) {
            throw new ResourceDoesNotExistException("Player", "id", playerId);
        }
        return player;
    }

    @Override
    public Player findById(UUID playerId) {
        return playerRepository.findById(playerId)
                .orElseThrow(() -> new ResourceDoesNotExistException("Player", "id", playerId));
    }

    @Override
    public List<Player> findAllNotDeleted() {
        return playerRepository.findAllNotDeleted();
    }

    @Override
    public Player update(Player updatedPlayer) {
        Player existingPlayer = findNotDeletedById(updatedPlayer.getId());

        if (updatedPlayer.getName() != null) {
            existingPlayer.setName(updatedPlayer.getName());
        }

        if (updatedPlayer.getPreferredPosition() != null) {
            existingPlayer.setPreferredPosition(updatedPlayer.getPreferredPosition());
        }

        return playerRepository.save(existingPlayer);
    }

    @Override
    public void softDelete(UUID playerId) {
        Player player = findNotDeletedById(playerId);
        if (player.hasPendingGames()) {
            throw new ResourceCouldNotBeDeletedException("Player", "id", playerId);
        }
        player.delete();
        playerRepository.save(player);
    }

    @Override
    public List<Player> findNotDeletedByName(String playerName) {
        return playerRepository.findNotDeletedByName(playerName);
    }
}
