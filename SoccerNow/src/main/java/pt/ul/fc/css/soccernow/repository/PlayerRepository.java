package pt.ul.fc.css.soccernow.repository;

import pt.ul.fc.css.soccernow.domain.entities.user.Player;

import java.util.List;

public interface PlayerRepository extends SoftDeletedRepository<Player> {
    List<Player> findByDeletedAtIsNullAndName(String name);

    default List<Player> findNotDeletedByName(String name) {
        return findByDeletedAtIsNullAndName(name);
    }
}
