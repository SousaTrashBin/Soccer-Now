package pt.ul.fc.css.soccernow.service;

import org.springframework.transaction.annotation.Transactional;
import pt.ul.fc.css.soccernow.domain.entities.Team;
import pt.ul.fc.css.soccernow.domain.entities.user.Player;

public interface TeamService extends CrudService<Team> {
    @Transactional
    void removePlayerFromTeam(Player savedPlayer, Team team);

    @Transactional
    void addPlayerToTeam(Player savedPlayer, Team team);
}
