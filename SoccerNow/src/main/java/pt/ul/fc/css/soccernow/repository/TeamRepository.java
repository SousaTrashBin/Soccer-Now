package pt.ul.fc.css.soccernow.repository;

import pt.ul.fc.css.soccernow.domain.entities.Team;

public interface TeamRepository extends SoftDeletedRepository<Team> {
    Team findByName(String name);
}
