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

import java.util.List;
import java.util.UUID;

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
    public void removePlayerFromTeam(Player savedPlayer, Team team) {
        if (!team.hasPlayer(savedPlayer)) {
            throw new BadRequestException(savedPlayer.getName() + " is not on team " + team.getName());
        }
        team.removePlayer(savedPlayer);
        teamRepository.save(team);
    }

    @Override
    public void addPlayerToTeam(Player savedPlayer, Team team) {
        boolean validTransaction = !savedPlayer.hasTeam(team) && !team.hasPlayer(savedPlayer);
        if (!validTransaction) {
            throw new BadRequestException(savedPlayer.getName() + " is already on team " + team.getName());
        }

        team.addPlayer(savedPlayer);
        savedPlayer.addTeam(team);
        teamRepository.save(team);
    }

    @Override
    public Team add(Team entity) {
        Team newTeam = new Team();
        newTeam.setName(entity.getName());

        List<Player> newTeamPlayers = entity.getPlayers()
                                            .stream()
                                            .map(p -> playerService.findNotDeletedById(p.getId()))
                                            .toList();

        newTeam.setPlayers(newTeamPlayers);
        return teamRepository.save(newTeam);
    }

    @Override
    public Team update(Team entity) {
        return null;
    }

    @Override
    public void softDelete(UUID entityId) {
    }

    @Override
    public List<Team> findAllNotDeleted() {
        return List.of();
    }

    @Override
    public Team findById(UUID entityId) {
        return teamRepository.findById(entityId)
                             .orElseThrow(() -> new ResourceDoesNotExistException("Team", "id", entityId));
    }

    @Override
    public Team findNotDeletedById(UUID entityId) {
        Team team = findById(entityId);
        if (team.isDeleted()) {
            throw new ResourceDoesNotExistException("Team", "id", entityId);
        }
        return team;
    }
}
