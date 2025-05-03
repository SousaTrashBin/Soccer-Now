package pt.ul.fc.css.soccernow.service.impl;

import org.springframework.stereotype.Service;
import pt.ul.fc.css.soccernow.domain.entities.Team;
import pt.ul.fc.css.soccernow.domain.entities.user.Player;
import pt.ul.fc.css.soccernow.exception.BadRequestException;
import pt.ul.fc.css.soccernow.exception.ResourceCouldNotBeDeletedException;
import pt.ul.fc.css.soccernow.exception.ResourceDoesNotExistException;
import pt.ul.fc.css.soccernow.repository.TeamRepository;
import pt.ul.fc.css.soccernow.service.TeamService;

import java.util.List;
import java.util.UUID;

@Service
public class TeamServiceImpl implements TeamService {
    private final TeamRepository teamRepository;

    public TeamServiceImpl(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
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
        return teamRepository.save(newTeam);
    }

    @Override
    public Team update(Team updatedTeam) {
        Team existingTeam = findNotDeletedById(updatedTeam.getId());

        if (updatedTeam.getName() != null) {
            existingTeam.setName(updatedTeam.getName());
        }

        return teamRepository.save(existingTeam);
    }

    public void softDelete(UUID teamId) {
        Team teamToDelete = findNotDeletedById(teamId);
        if (teamToDelete.hasPendingGames() || teamToDelete.hasPendingTournaments()) {
            throw new ResourceCouldNotBeDeletedException("Team has either a pending game or tournament");
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
