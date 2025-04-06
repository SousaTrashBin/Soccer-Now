package pt.ul.fc.css.soccernow.domain.entities.tournament;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.LinkedHashSet;
import java.util.Set;
import pt.ul.fc.css.soccernow.domain.entities.Team;

@Entity
@Table(name = "TOURNAMENT")
public abstract class Tournament {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "ID", nullable = false)
  private Long id;

  @NotNull
  @Size(min = 8)
  @ManyToMany(cascade = {CascadeType.PERSIST})
  @JoinTable(
      name = "TOURNAMENT_TEAMS",
      joinColumns = @JoinColumn(name = "TOURNAMENT_ID"),
      inverseJoinColumns = @JoinColumn(name = "TEAMS_ID"))
  private Set<Team> teams = new LinkedHashSet<>();

  public Set<Team> getTeams() {
    return teams;
  }

  public void setTeams(Set<Team> teams) {
    this.teams = teams;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }
}
