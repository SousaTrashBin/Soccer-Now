package pt.ul.fc.css.soccernow.domain.entities.tournament;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.LinkedHashSet;
import java.util.Set;
import pt.ul.fc.css.soccernow.domain.entities.Team;
import pt.ul.fc.css.soccernow.domain.entities.games.TournamentGame;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TOURNAMENT_TYPE", discriminatorType = DiscriminatorType.STRING)
@Table(name = "TOURNAMENTS")
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

  @OneToMany(mappedBy = "tournament", cascade = CascadeType.PERSIST, orphanRemoval = true)
  private Set<TournamentGame> tournamentGames = new LinkedHashSet<>();

  public Set<TournamentGame> getTournamentGames() {
    return tournamentGames;
  }

  public void setTournamentGames(Set<TournamentGame> tournamentGames) {
    this.tournamentGames = tournamentGames;
  }

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
