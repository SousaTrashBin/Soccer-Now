package pt.ul.fc.css.soccernow.service;

import pt.ul.fc.css.soccernow.domain.entities.user.Referee;
import pt.ul.fc.css.soccernow.util.RefereeSearchParams;

import java.util.List;

public interface RefereeService extends CrudService<Referee> {
    List<Referee> findAllNotDeleted(RefereeSearchParams params);
}
