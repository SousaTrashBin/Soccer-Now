package pt.ul.fc.css.soccernow.domain.entities.tournament;

import jakarta.persistence.*;
import pt.ul.fc.css.soccernow.domain.entities.Team;
import pt.ul.fc.css.soccernow.domain.entities.game.Game;
import pt.ul.fc.css.soccernow.domain.entities.game.GameStats;
import pt.ul.fc.css.soccernow.domain.entities.game.GameTeam;
import pt.ul.fc.css.soccernow.util.SoftDeleteEntity;
import pt.ul.fc.css.soccernow.util.TournamentStatusEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "tournaments")
public abstract class Tournament extends SoftDeleteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @OneToMany(mappedBy = "tournament", orphanRemoval = true, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @OrderBy("happensIn")
    private List<Game> games = new ArrayList<>();

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TournamentStatusEnum status = TournamentStatusEnum.OPEN;

    public TournamentStatusEnum getStatus() {
        return status;
    }

    public void setStatus(TournamentStatusEnum tournamentStatusEnum) {
        this.status = tournamentStatusEnum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void addGame(Game game) {
        this.games.add(game);
        game.setTournament(this);
    }

    public void removeGame(Game game) {
        this.games.remove(game);
        game.setTournament(null);
    }

    public boolean hasGame(Game game) {
        return this.games.contains(game);
    }

    public abstract boolean hasTeam(Team team);

    public abstract void addTeam(Team team);

    public abstract void removeTeam(Team team);

    public abstract boolean hasMinimumTeams();

    public abstract void updateScore(GameTeam gameTeamOne, GameTeam gameTeamTwo, GameStats gameStats);
}
