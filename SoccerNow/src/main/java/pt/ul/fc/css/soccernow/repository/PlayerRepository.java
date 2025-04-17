package pt.ul.fc.css.soccernow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.ul.fc.css.soccernow.domain.entities.user.Player;

import java.util.List;
import java.util.UUID;

public interface PlayerRepository extends JpaRepository<Player, UUID> {
    List<Player> findByName(String name);

    default List<Player> findAllNotDeleted() {
        return findByDeletedAtIsNull();
    }

    List<Player> findByDeletedAtIsNull();
}
