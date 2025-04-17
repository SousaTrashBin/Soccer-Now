package pt.ul.fc.css.soccernow.domain.entities.user;

import jakarta.persistence.*;
import pt.ul.fc.css.soccernow.util.SoftDeleteEntity;

import java.util.UUID;

@Entity
@Table(name = "users")
public abstract class User extends SoftDeleteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
