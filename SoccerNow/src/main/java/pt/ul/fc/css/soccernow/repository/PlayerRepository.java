package pt.ul.fc.css.soccernow.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import pt.ul.fc.css.soccernow.domain.entities.user.Player;

public interface PlayerRepository extends JpaRepository<Player, UUID> {
  List<Player> findByName(String name);

  default List<Player> findAllActivePlayers() {
    return findByDeletedAtIsNull();
  }

  List<Player> findByDeletedAtIsNull();
}
