package pt.ul.fc.css.soccernow.service.impl;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import pt.ul.fc.css.soccernow.domain.entities.Team;
import pt.ul.fc.css.soccernow.domain.entities.user.Player;
import pt.ul.fc.css.soccernow.exception.BadRequestException;
import pt.ul.fc.css.soccernow.exception.ResourceDoesNotExistException;
import pt.ul.fc.css.soccernow.repository.TeamRepository;
import pt.ul.fc.css.soccernow.service.PlayerService;
import pt.ul.fc.css.soccernow.service.TeamService;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class TeamServiceImpl implements TeamService {
    private final TeamRepository teamRepository;
    private final PlayerService playerService;

    public TeamServiceImpl(
            TeamRepository teamRepository,
            @Lazy PlayerService playerService
    ) {
        this.teamRepository = teamRepository;
        this.playerService = playerService;
    }

    @Override
    public void removePlayerFromTeam(Player player, Team team) {
        if (!team.hasPlayer(player)) {
            throw new BadRequestException(player.getName() + " is not on team " + team.getName());
        }
        team.removePlayer(player);
        teamRepository.save(team);
    }

    @Override
    public void addPlayerToTeam(Player player, Team team) {
        if (team.hasPlayer(player)) {
            throw new BadRequestException(player.getName() + " is already on team " + team.getName());
        }
        team.addPlayer(player);
        teamRepository.save(team);
    }

    @Override
    public Team add(Team teamData) {
        Team newTeam = new Team();
        newTeam.setName(teamData.getName());

        Set<Player> fullPlayerData = fetchPlayersWithCompleteData(teamData.getPlayers());
        fullPlayerData.forEach(player -> addPlayerToTeam(player, newTeam));

        return teamRepository.save(newTeam);
    }

    @Override
    public Team update(Team updatedTeam) {
        Team existingTeam = findNotDeletedById(updatedTeam.getId());

        if (updatedTeam.getName() != null) {
            existingTeam.setName(updatedTeam.getName());
        }

        if (updatedTeam.getPlayers() != null) {
            Set<Player> newPlayers = fetchPlayersWithCompleteData(updatedTeam.getPlayers());
            Set<Player> playersToAdd = new HashSet<>();
            Set<Player> playersToRemove = new HashSet<>();

            newPlayers.stream()
                    .filter(Predicate.not(existingTeam::hasPlayer))
                    .forEach(playersToAdd::add);

            existingTeam.getPlayers()
                    .stream()
                    .filter(Predicate.not(newPlayers::contains))
                    .forEach(playersToRemove::add);

            for (Player playerToRemove : playersToRemove) {
                if (existingTeam.hasPendingGamesWithPlayer(playerToRemove)) {
                    throw new BadRequestException("Player " + playerToRemove.getName() + " has pending games with team " + existingTeam.getName());
                }
                existingTeam.removePlayer(playerToRemove);
            }

            for (Player playerToAdd : playersToAdd) {
                existingTeam.addPlayer(playerToAdd);
            }
        }

        return teamRepository.save(existingTeam);
    }

    private Set<Player> fetchPlayersWithCompleteData(Set<Player> playerReferences) {
        return playerReferences.stream()
                .map(playerRef -> playerService.findNotDeletedById(playerRef.getId()))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Override
    public void softDelete(UUID teamId) {
        Team teamToDelete = findNotDeletedById(teamId);
        if (teamToDelete.hasPendingGames() || teamToDelete.hasPendingTournaments()) {
            throw new BadRequestException("Team " + teamToDelete.getName() + " has pending games or tournaments");
        }

        teamToDelete.delete();
        teamRepository.save(teamToDelete);
    }

    @Override
    public List<Team> findAllNotDeleted() {
        return teamRepository.findAllNotDeleted();
    }

    @Override
    public Team findById(UUID teamId) {
        return teamRepository.findById(teamId)
                .orElseThrow(() -> new ResourceDoesNotExistException("Team", "id", teamId));
    }

    @Override
    public Team findNotDeletedById(UUID teamId) {
        Team team = findById(teamId);
        if (team.isDeleted()) {
            throw new ResourceDoesNotExistException("Team", "id", teamId);
        }
        return team;
    }
}
