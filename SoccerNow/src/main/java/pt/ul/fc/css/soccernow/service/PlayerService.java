package pt.ul.fc.css.soccernow.service;

import org.springframework.transaction.annotation.Transactional;
import pt.ul.fc.css.soccernow.domain.entities.Team;
import pt.ul.fc.css.soccernow.domain.entities.user.Player;

public interface PlayerService extends CrudService<Player> {
    @Transactional
    void removeTeamFromPlayer(Team team, Player savedPlayer);

    @Transactional
    void addTeamToPlayer(Team team, Player savedPlayer);
}
