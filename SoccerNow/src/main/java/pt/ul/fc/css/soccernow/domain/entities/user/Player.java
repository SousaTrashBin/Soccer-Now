package pt.ul.fc.css.soccernow.domain.entities.user;

import jakarta.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import org.hibernate.proxy.HibernateProxy;
import pt.ul.fc.css.soccernow.domain.entities.Team;
import pt.ul.fc.css.soccernow.util.FutsalPosition;

@Entity
@DiscriminatorValue("Player")
public class Player extends User {
  @Enumerated(EnumType.STRING)
  @Column(name = "PREFERRED_POSITION")
  private FutsalPosition preferredPosition;

  @ManyToMany(mappedBy = "players", cascade = CascadeType.PERSIST)
  private Set<Team> teams = new LinkedHashSet<>();

  public Set<Team> getTeams() {
    return teams;
  }

  public void setTeams(Set<Team> teams) {
    this.teams = teams;
  }

  public FutsalPosition getPreferredPosition() {
    return preferredPosition;
  }

  public void setPreferredPosition(FutsalPosition preferredPosition) {
    this.preferredPosition = preferredPosition;
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
    Player player = (Player) o;
    return getId() != null && Objects.equals(getId(), player.getId());
  }

  @Override
  public final int hashCode() {
    return this instanceof HibernateProxy
        ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode()
        : getClass().hashCode();
  }
}
