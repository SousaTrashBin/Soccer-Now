package pt.ul.fc.css.soccernow.repository;

import pt.ul.fc.css.soccernow.domain.entities.Team;
import pt.ul.fc.css.soccernow.util.TeamSearchParams;

import java.util.ArrayList;
import java.util.List;

public interface TeamRepository extends SoftDeletedRepository<Team> {
    Team findByName(String name);

    default List<Team> findAllNotDeleted(TeamSearchParams params) {
        return new ArrayList<>();
    }
}
