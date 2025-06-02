package com.soccernow.ui.soccernowui.dto.user;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * DTO for {@link pt.ul.fc.css.soccernow.domain.entities.user.Referee}
 */
public class RefereeInfoDTO implements Serializable {
    private UUID id;
    private String name;
    private Boolean hasCertificate = false;

    public RefereeInfoDTO() {
    }

    public RefereeInfoDTO(UUID id, String name, Boolean hasCertificate) {
        this.id = id;
        this.name = name;
        this.hasCertificate = hasCertificate;
    }

    public RefereeInfoDTO(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public RefereeInfoDTO setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public RefereeInfoDTO setName(String name) {
        this.name = name;
        return this;
    }

    public Boolean getHasCertificate() {
        return hasCertificate;
    }

    public RefereeInfoDTO setHasCertificate(Boolean hasCertificate) {
        this.hasCertificate = hasCertificate;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RefereeInfoDTO entity = (RefereeInfoDTO) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.name, entity.name) &&
                Objects.equals(this.hasCertificate, entity.hasCertificate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, hasCertificate);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "name = " + name + ", " +
                "hasCertificate = " + hasCertificate + ")";
    }
}
