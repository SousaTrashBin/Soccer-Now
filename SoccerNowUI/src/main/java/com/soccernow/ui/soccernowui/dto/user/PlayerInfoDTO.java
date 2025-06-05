package com.soccernow.ui.soccernowui.dto.user;


import com.soccernow.ui.soccernowui.util.FutsalPositionEnum;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class PlayerInfoDTO implements Serializable {
    private UUID id;
    private String name;
    private FutsalPositionEnum preferredPosition;

    public PlayerInfoDTO() {
    }

    public PlayerInfoDTO(UUID id, String name, FutsalPositionEnum preferredPosition) {
        this.id = id;
        this.name = name;
        this.preferredPosition = preferredPosition;
    }

    public PlayerInfoDTO(UUID uuid) {
        this.id = uuid;
    }

    public PlayerInfoDTO(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public PlayerInfoDTO setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public PlayerInfoDTO setName(String name) {
        this.name = name;
        return this;
    }

    public FutsalPositionEnum getPreferredPosition() {
        return preferredPosition;
    }

    public PlayerInfoDTO setPreferredPosition(FutsalPositionEnum preferredPosition) {
        this.preferredPosition = preferredPosition;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerInfoDTO entity = (PlayerInfoDTO) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.name, entity.name) &&
                Objects.equals(this.preferredPosition, entity.preferredPosition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, preferredPosition);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "name = " + name + ", " +
                "preferredPosition = " + preferredPosition + ")";
    }
}
