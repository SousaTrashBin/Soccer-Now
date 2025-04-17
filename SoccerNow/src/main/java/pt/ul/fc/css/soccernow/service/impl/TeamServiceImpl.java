package pt.ul.fc.css.soccernow.service.impl;

import org.springframework.stereotype.Service;
import pt.ul.fc.css.soccernow.domain.entities.Team;
import pt.ul.fc.css.soccernow.domain.entities.user.Player;
import pt.ul.fc.css.soccernow.service.TeamService;

import java.util.List;
import java.util.UUID;

@Service
public class TeamServiceImpl implements TeamService {
    @Override
    public void removePlayerFromTeam(Player savedPlayer, Team team) {

    }

    @Override
    public void addPlayerToTeam(Player savedPlayer, Team team) {

    }

    @Override
    public boolean doesPlayerHaveAPendingGame(Player player, Team team) {
        return false;
    }

    @Override
    public Team add(Team entity) {
        return null;
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
        return null;
    }

    @Override
    public Team findNotDeletedById(UUID entityId) {
        return null;
    }
}
