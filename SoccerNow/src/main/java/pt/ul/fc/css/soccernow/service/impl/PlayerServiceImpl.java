package pt.ul.fc.css.soccernow.service.impl;

import org.springframework.stereotype.Service;
import pt.ul.fc.css.soccernow.domain.entities.Team;
import pt.ul.fc.css.soccernow.domain.entities.user.Player;
import pt.ul.fc.css.soccernow.exception.ResourceCouldNotBeDeletedException;
import pt.ul.fc.css.soccernow.exception.ResourceDoesNotExistException;
import pt.ul.fc.css.soccernow.repository.PlayerRepository;
import pt.ul.fc.css.soccernow.service.PlayerService;
import pt.ul.fc.css.soccernow.service.TeamService;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PlayerServiceImpl implements PlayerService {
    private final PlayerRepository playerRepository;
    private final TeamService teamService;

    public PlayerServiceImpl(PlayerRepository playerRepository, TeamService teamService) {
        this.playerRepository = playerRepository;
        this.teamService = teamService;
    }

    @Override
    public Player add(Player player) {
        Player newPlayer = new Player();
        newPlayer.setName(player.getName());
        newPlayer.setPreferredPosition(player.getPreferredPosition());
        return playerRepository.save(newPlayer); //201
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

        if (updatedPlayer.getTeams() != null) {
            Set<Team> newTeams = updatedPlayer.getTeams();
            Set<Team> currentTeams = existingPlayer.getTeams();

            Set<Team> teamsToAdd = newTeams.stream()
                    .filter(team -> !currentTeams.contains(team))
                    .collect(Collectors.toSet());
            for (Team teamToAdd : teamsToAdd) {
                teamService.addPlayerToTeam(existingPlayer, teamToAdd);
            }

            Set<Team> teamsToRemove = currentTeams.stream()
                    .filter(team -> !newTeams.contains(team))
                    .collect(Collectors.toSet());
            for (Team teamToRemove : teamsToRemove) {
                teamService.removePlayerFromTeam(existingPlayer, teamToRemove);
            }
        }

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
