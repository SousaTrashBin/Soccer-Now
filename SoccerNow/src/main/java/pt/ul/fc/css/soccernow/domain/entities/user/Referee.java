package pt.ul.fc.css.soccernow.domain.entities.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import org.hibernate.proxy.HibernateProxy;
import pt.ul.fc.css.soccernow.domain.entities.game.Game;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
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

    public boolean hasAnyPendingGames() {
        return primaryRefereeGames.stream().anyMatch(Predicate.not(Game::isClosed))
                || secondaryRefereeGames.stream().anyMatch(Predicate.not(Game::isClosed));
    }

    public long getClosedGamesCount() {
        Function<Set<Game>, Long> getClosedGames = set -> set.stream().filter(Game::isClosed).count();
        return getClosedGames.apply(primaryRefereeGames) + getClosedGames.apply(secondaryRefereeGames);
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Referee referee = (Referee) o;
        return getId() != null && Objects.equals(getId(), referee.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + getId() + ", " +
                "hasCertificate = " + getHasCertificate() + ", " +
                "name = " + getName() + ")";
    }

    public void registerPrimaryRefereeGame(Game refereeGame) {
        this.primaryRefereeGames.add(refereeGame);
    }

    public void registerSecondaryRefereeGame(Game refereeGame) {
        this.secondaryRefereeGames.add(refereeGame);
    }
}
