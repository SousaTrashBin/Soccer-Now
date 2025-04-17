package pt.ul.fc.css.soccernow.util;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

import java.time.LocalDateTime;

@MappedSuperclass
public class SoftDeleteEntity {
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    public void delete() {
        deletedAt = LocalDateTime.now();
    }

    public void restore() {
        deletedAt = null;
    }

    public boolean isDeleted() {
        return deletedAt != null;
    }
}
