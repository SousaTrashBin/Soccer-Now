package pt.ul.fc.css.soccernow.service.impl;

import org.springframework.stereotype.Service;
import pt.ul.fc.css.soccernow.domain.entities.Team;
import pt.ul.fc.css.soccernow.domain.entities.user.Player;
import pt.ul.fc.css.soccernow.exception.ResourceAlreadyExistsException;
import pt.ul.fc.css.soccernow.exception.ResourceCouldNotBeDeletedException;
import pt.ul.fc.css.soccernow.exception.ResourceDoesNotExistException;
import pt.ul.fc.css.soccernow.repository.PlayerRepository;
import pt.ul.fc.css.soccernow.service.PlayerService;
import pt.ul.fc.css.soccernow.service.TeamService;

import java.util.List;
import java.util.UUID;

@Service
public class PlayerServiceImpl implements PlayerService {
    private final PlayerRepository playerRepository;
    private final TeamService teamService;

    public PlayerServiceImpl(PlayerRepository playerRepository, TeamService teamService) {
        this.playerRepository = playerRepository;
        this.teamService = teamService;
    }

    @Override
    public boolean existsById(UUID playerId) {
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
        return playerRepository.findById(playerId).orElseThrow(() -> new ResourceDoesNotExistException("Player", "id", playerId));
    }

    @Override
    public List<Player> getByName(String name) {
        return playerRepository.findByName(name);
    }

    @Override
    public Player update(Player player) {
        return null;
    }

    @Override
    public void remove(UUID playerId) {
        Player player = getById(playerId);
        boolean canBeRemoved = !player.isDeleted() && player.getTeams().stream().noneMatch(teamService::willTeamPlay);
        if (!canBeRemoved){
            throw new ResourceCouldNotBeDeletedException("Player", "id", playerId);
        }
        player.delete();
        playerRepository.save(player);
    }
}
