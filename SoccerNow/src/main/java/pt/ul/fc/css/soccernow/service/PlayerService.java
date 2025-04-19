package pt.ul.fc.css.soccernow.service;

import pt.ul.fc.css.soccernow.domain.entities.Team;
import pt.ul.fc.css.soccernow.domain.entities.user.Player;

public interface PlayerService extends CrudService<Player> {
    void addTeamToPlayer(Team savedTeam, Player player);
}
