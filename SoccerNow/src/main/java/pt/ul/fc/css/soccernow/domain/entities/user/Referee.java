package pt.ul.fc.css.soccernow.domain.entities.user;

import jakarta.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import org.hibernate.proxy.HibernateProxy;
import pt.ul.fc.css.soccernow.domain.entities.games.Game;

@Entity
@DiscriminatorValue("Referee")
public class Referee extends User {
  @Column(name = "HAS_CERTIFICATE", nullable = false)
  private Boolean hasCertificate = false;

  @OneToMany(mappedBy = "primaryReferee", cascade = CascadeType.PERSIST, orphanRemoval = true)
  private Set<Game> primaryRefereeGames = new LinkedHashSet<>();

  @ManyToMany(mappedBy = "secondaryReferees", cascade = CascadeType.PERSIST)
  private Set<Game> secondaryRefereeGames = new LinkedHashSet<>();

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
  public final boolean equals(Object o) {
    if (this == o) return true;
    if (o == null) return false;
    Class<?> oEffectiveClass =
        o instanceof HibernateProxy
            ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass()
            : o.getClass();
    Class<?> thisEffectiveClass =
        this instanceof HibernateProxy
            ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass()
            : this.getClass();
    if (thisEffectiveClass != oEffectiveClass) return false;
    Referee referee = (Referee) o;
    return getId() != null && Objects.equals(getId(), referee.getId());
  }

  @Override
  public final int hashCode() {
    return this instanceof HibernateProxy
        ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode()
        : getClass().hashCode();
  }
}
