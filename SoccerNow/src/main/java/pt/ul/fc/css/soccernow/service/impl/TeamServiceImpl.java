package pt.ul.fc.css.soccernow.service.impl;

import org.springframework.stereotype.Service;
import pt.ul.fc.css.soccernow.domain.entities.Team;
import pt.ul.fc.css.soccernow.service.TeamService;

import java.util.UUID;

@Service
public class TeamServiceImpl implements TeamService {
    @Override
    public boolean existsById(UUID teamId) {
        return false;
    }

    @Override
    public boolean existsByName(String teamName) {
        return false;
    }

    @Override
    public Team add(Team team) {
        return null;
    }

    @Override
    public Team getById(UUID teamId) {
        return null;
    }

    @Override
    public Team getByName(String teamName) {
        return null;
    }

    @Override
    public Team update(Team team) {
        return null;
    }

    @Override
    public void remove(UUID teamId) {
    }

    @Override
    public boolean willTeamPlay(Team team) {
        return false;
    }
}
