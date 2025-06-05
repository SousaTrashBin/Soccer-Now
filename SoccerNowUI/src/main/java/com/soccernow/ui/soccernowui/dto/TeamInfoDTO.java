package com.soccernow.ui.soccernowui.dto;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class TeamInfoDTO implements Serializable {
    private UUID id;
    private String name;

    public TeamInfoDTO() {
    }

    public TeamInfoDTO(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public TeamInfoDTO setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public TeamInfoDTO setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeamInfoDTO entity = (TeamInfoDTO) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.name, entity.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return name + " : " + id;
    }
}
