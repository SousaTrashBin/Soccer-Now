package pt.ul.fc.css.soccernow.domain.entities.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import pt.ul.fc.css.soccernow.domain.entities.game.Game;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

@Entity
public class Referee extends User {
    @Column(name = "has_certificate")
    private Boolean hasCertificate = false;

    @OneToMany(mappedBy = "primaryReferee", orphanRemoval = true)
    private Set<Game> primaryRefereeGames = new HashSet<>();

    @ManyToMany(mappedBy = "secondaryReferees")
    private Set<Game> secondaryRefereeGames = new HashSet<>();

    public Set<Game> getSecondaryRefereeGames() {
        return secondaryRefereeGames;
    }

    public void setSecondaryRefereeGames(Set<Game> secondaryRefereeGames) {
        this.secondaryRefereeGames = secondaryRefereeGames;
    }

    public Set<Game> getPrimaryRefereeGames() {
        return primaryRefereeGames;
    }

    public void setPrimaryRefereeGames(Set<Game> primaryRefereeGames) {
        this.primaryRefereeGames = primaryRefereeGames;
    }

    public Boolean getHasCertificate() {
        return hasCertificate;
    }

    public void setHasCertificate(Boolean hasCertificate) {
        this.hasCertificate = hasCertificate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Referee referee = (Referee) o;
        return Objects.equals(getId(), referee.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    public boolean hasAnyPendingGames() {
        return primaryRefereeGames.stream().anyMatch(Predicate.not(Game::isClosed))
                || secondaryRefereeGames.stream().anyMatch(Predicate.not(Game::isClosed));
    }
}
