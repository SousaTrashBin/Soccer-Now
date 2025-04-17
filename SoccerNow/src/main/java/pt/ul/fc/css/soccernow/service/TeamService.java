package pt.ul.fc.css.soccernow.service;

import pt.ul.fc.css.soccernow.domain.entities.Team;
import pt.ul.fc.css.soccernow.domain.entities.user.Player;

public interface TeamService extends CrudService<Team> {
    void removePlayerFromTeam(Player savedPlayer, Team team);

    void addPlayerToTeam(Player savedPlayer, Team team);

    boolean doesPlayerHaveAPendingGame(Player player, Team team);
}
