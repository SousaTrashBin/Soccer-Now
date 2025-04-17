package pt.ul.fc.css.soccernow.service;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface CrudService<T> {
    @Transactional
    T add(T entity);

    @Transactional
    T update(T entity);

    @Transactional
    void softDelete(UUID entityId);

    List<T> findAllNotDeleted();

    T findById(UUID entityId);

    T findNotDeletedById(UUID entityId);
}
