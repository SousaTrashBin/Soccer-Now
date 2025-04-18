package pt.ul.fc.css.soccernow.service.impl;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import pt.ul.fc.css.soccernow.domain.entities.Team;
import pt.ul.fc.css.soccernow.domain.entities.user.Player;
import pt.ul.fc.css.soccernow.exception.ResourceCouldNotBeDeletedException;
import pt.ul.fc.css.soccernow.exception.ResourceDoesNotExistException;
import pt.ul.fc.css.soccernow.repository.PlayerRepository;
import pt.ul.fc.css.soccernow.service.GameService;
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
    private final GameService gameService;

    public PlayerServiceImpl(PlayerRepository playerRepository, TeamService teamService, @Lazy GameService gameService) {
        this.playerRepository = playerRepository;
        this.teamService = teamService;
        this.gameService = gameService;
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
    public Player update(Player player) {
        Player savedPlayer = findNotDeletedById(player.getId());
        if (player.getTeams() != null) {
            Set<Team> incomingTeams = player.getTeams();
            Set<Team> existingTeams = savedPlayer.getTeams();

            Set<Team> teamsToBeAdded = incomingTeams.stream()
                                                    .filter(team -> !existingTeams.contains(team))
                                                    .collect(Collectors.toSet());
            for (Team teamToBeAdded : teamsToBeAdded) {
                teamService.addPlayerToTeam(savedPlayer, teamToBeAdded);
            }

            Set<Team> teamsToBeRemoved = existingTeams.stream()
                                                      .filter(team -> !incomingTeams.contains(team))
                                                      .collect(Collectors.toSet());
            for (Team teamToBeRemoved : teamsToBeRemoved) {
                teamService.removePlayerFromTeam(savedPlayer, teamToBeRemoved);
            }
        }
        if (player.getName() != null) {
            savedPlayer.setName(player.getName());
        }
        if (player.getPreferredPosition() != null) {
            savedPlayer.setPreferredPosition(player.getPreferredPosition());
        }
        return playerRepository.save(savedPlayer);
    }

    @Override
    public void softDelete(UUID playerId) {
        Player player = findNotDeletedById(playerId);
        boolean canBeRemoved = player.getTeams()
                                     .stream()
                                     .noneMatch(team -> gameService.hasPendingGame(player, team));
        if (!canBeRemoved) {
            throw new ResourceCouldNotBeDeletedException("Player", "id", playerId);
        }
        player.delete();
        playerRepository.save(player);
    }
}
