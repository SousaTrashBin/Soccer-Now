package pt.ul.fc.css.soccernow.service;

import pt.ul.fc.css.soccernow.domain.entities.Team;
import pt.ul.fc.css.soccernow.domain.entities.game.Game;
import pt.ul.fc.css.soccernow.domain.entities.user.Player;

public interface GameService extends CrudService<Game> {
    boolean hasPendingGame(Player player, Team team);

    boolean teamHasPendingGame(Team team);
}
