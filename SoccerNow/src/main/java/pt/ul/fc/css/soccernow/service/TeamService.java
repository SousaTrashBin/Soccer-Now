package pt.ul.fc.css.soccernow.service;

import org.springframework.transaction.annotation.Transactional;
import pt.ul.fc.css.soccernow.domain.entities.Team;
import pt.ul.fc.css.soccernow.domain.entities.user.Player;

import java.util.List;
import java.util.UUID;

public interface TeamService {
    boolean existsById(UUID teamId);
    boolean existsByName(String teamName);
    @Transactional
    Team add(Team team);
    Team getById(UUID teamId);
    Team getByName(String teamName);
    @Transactional
    Team update(Team team);
    @Transactional
    void remove(UUID teamId);
    boolean willTeamPlay(Team team);
}
