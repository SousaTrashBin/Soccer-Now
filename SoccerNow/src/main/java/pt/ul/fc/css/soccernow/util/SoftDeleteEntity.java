package pt.ul.fc.css.soccernow.util;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;

import java.time.LocalDateTime;

@MappedSuperclass
public class SoftDeleteEntity {
    @Version
    @Column(name = "version")
    private Integer version;
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

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
