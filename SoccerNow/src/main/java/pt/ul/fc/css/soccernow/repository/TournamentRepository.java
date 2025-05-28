package pt.ul.fc.css.soccernow.repository;

import pt.ul.fc.css.soccernow.domain.entities.tournament.point.PointTournament;
import pt.ul.fc.css.soccernow.util.TournamentSearchParams;

import java.util.ArrayList;
import java.util.List;

public interface TournamentRepository extends SoftDeletedRepository<PointTournament> {
    default List<PointTournament> findAllNotDeleted(TournamentSearchParams params) {
        return new ArrayList<>();
    }
}
