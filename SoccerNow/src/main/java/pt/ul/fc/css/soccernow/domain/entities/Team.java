package pt.ul.fc.css.soccernow.domain.entities;

import jakarta.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import org.hibernate.proxy.HibernateProxy;
import pt.ul.fc.css.soccernow.domain.entities.tournament.Tournament;
import pt.ul.fc.css.soccernow.domain.entities.user.Player;

@Entity
@Table(name = "TEAM")
public class Team {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "ID", nullable = false)
  private Long id;

  @Column(name = "NAME", nullable = false, unique = true)
  private String name;

  @ManyToMany(cascade = CascadeType.PERSIST)
  @JoinTable(
      name = "TEAM_PLAYERS",
      joinColumns = @JoinColumn(name = "TEAM_ID"),
      inverseJoinColumns = @JoinColumn(name = "PLAYERS_ID"))
  private Set<Player> players = new LinkedHashSet<>();

  @ManyToMany(mappedBy = "teams", cascade = CascadeType.PERSIST)
  private Set<Tournament> tournaments = new LinkedHashSet<>();

  @OneToMany(mappedBy = "team", cascade = CascadeType.PERSIST, orphanRemoval = true)
  private Set<GameTeam> gameTeams = new LinkedHashSet<>();

  public Set<GameTeam> getGameTeams() {
    return gameTeams;
  }

  public void setGameTeams(Set<GameTeam> gameTeams) {
    this.gameTeams = gameTeams;
  }

  public Set<Tournament> getTournaments() {
    return tournaments;
  }

  public void setTournaments(Set<Tournament> tournaments) {
    this.tournaments = tournaments;
  }

  public Set<Player> getPlayers() {
    return players;
  }

  public void setPlayers(Set<Player> players) {
    this.players = players;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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
    Team team = (Team) o;
    return getId() != null && Objects.equals(getId(), team.getId());
  }

  @Override
  public final int hashCode() {
    return this instanceof HibernateProxy
        ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode()
        : getClass().hashCode();
  }
}
