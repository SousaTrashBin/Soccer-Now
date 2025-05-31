package pt.ul.fc.css.soccernow.service.impl;

import pt.ul.fc.css.soccernow.domain.entities.tournament.point.PointTournament;
import pt.ul.fc.css.soccernow.service.PointTournamentService;

import java.util.List;
import java.util.UUID;

public class PointTournamentServiceImpl implements PointTournamentService {
    @Override
    public PointTournament add(PointTournament entity) {
        return null;
    }

    @Override
    public PointTournament update(PointTournament entity) {
        return null;
    }

    @Override
    public void softDelete(UUID entityId) {

    }

    @Override
    public List<PointTournament> findAllNotDeleted() {
        return List.of();
    }

    @Override
    public PointTournament findById(UUID entityId) {
        return null;
    }

    @Override
    public PointTournament findNotDeletedById(UUID entityId) {
        return null;
    }
}
