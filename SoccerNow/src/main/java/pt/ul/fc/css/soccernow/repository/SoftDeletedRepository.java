package pt.ul.fc.css.soccernow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import pt.ul.fc.css.soccernow.util.SoftDeleteEntity;

import java.util.List;
import java.util.UUID;

@NoRepositoryBean
public interface SoftDeletedRepository<T extends SoftDeleteEntity> extends JpaRepository<T, UUID>, QuerydslPredicateExecutor<T> {
    List<T> findByDeletedAtIsNull();

    default List<T> findAllNotDeleted() {
        return findByDeletedAtIsNull();
    }


}
