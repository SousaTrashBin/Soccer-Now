package pt.ul.fc.css.soccernow.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pt.ul.fc.css.soccernow.domain.dto.TeamDTO;
import pt.ul.fc.css.soccernow.domain.dto.games.GameDTO;
import pt.ul.fc.css.soccernow.domain.dto.tournament.TournamentDTO;
import pt.ul.fc.css.soccernow.domain.dto.user.PlayerDTO;
import pt.ul.fc.css.soccernow.domain.dto.user.RefereeDTO;
import pt.ul.fc.css.soccernow.domain.entities.Team;
import pt.ul.fc.css.soccernow.domain.entities.game.Game;
import pt.ul.fc.css.soccernow.domain.entities.tournament.Tournament;
import pt.ul.fc.css.soccernow.domain.entities.tournament.point.PointTournament;
import pt.ul.fc.css.soccernow.domain.entities.user.Player;
import pt.ul.fc.css.soccernow.domain.entities.user.Referee;
import pt.ul.fc.css.soccernow.mapper.*;
import pt.ul.fc.css.soccernow.service.*;
import pt.ul.fc.css.soccernow.util.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ViewController {
    private final PlayerService playerService;
    private final RefereeService refereeService;
    private final TeamService teamService;
    private final GameService gameService;
    private final PointTournamentService pointTournamentService;
    private final PlayerMapper playerMapper;
    private final RefereeMapper refereeMapper;
    private final TeamMapper teamMapper;
    private final GameMapper gameMapper;
    private final TournamentMapper tournamentMapper;

    public ViewController(
            PlayerService playerService,
            RefereeService refereeService,
            TeamService teamService,
            GameService gameService,
            PointTournamentService pointTournamentService,
            PlayerMapper playerMapper,
            RefereeMapper refereeMapper,
            TeamMapper teamMapper,
            GameMapper gameMapper,
            TournamentMapper tournamentMapper
    ) {
        this.playerService = playerService;
        this.refereeService = refereeService;
        this.teamService = teamService;
        this.gameService = gameService;
        this.pointTournamentService = pointTournamentService;
        this.playerMapper = playerMapper;
        this.refereeMapper = refereeMapper;
        this.teamMapper = teamMapper;
        this.gameMapper = gameMapper;
        this.tournamentMapper = tournamentMapper;
    }

    @GetMapping("/")
    public String redirectToLoginPage() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String redirectToHomePage(
            @RequestParam String email,
            @RequestParam String password,
            RedirectAttributes redirectAttributes
    ) {
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String getHomePage() {
        return "home";
    }

    @GetMapping("/players")
    public String getPlayersPage(
            @ModelAttribute PlayerSearchParams params,
            Model model
    ) {
        List<Player> filteredPlayers = playerService.findAllNotDeleted(params);
        List<PlayerDTO> playersDTO = filteredPlayers.stream()
                                                    .map(playerMapper::toDTO)
                                                    .collect(Collectors.toList());

        model.addAttribute("players", playersDTO);
        return "players";
    }

    @GetMapping("/referees")
    public String getRefereesPage(
            @ModelAttribute RefereeSearchParams params,
            Model model
    ) {
        List<Referee> filteredReferees = refereeService.findAllNotDeleted(params);
        List<RefereeDTO> refereesDTO = filteredReferees.stream()
                                                       .map(refereeMapper::toDTO)
                                                       .collect(Collectors.toList());

        model.addAttribute("referees", refereesDTO);
        return "referees";
    }

    @GetMapping("/teams")
    public String getTeamsPage(
            @ModelAttribute TeamSearchParams params,
            Model model
    ) {
        List<Team> filteredTeams = teamService.findAllNotDeleted(params);
        List<TeamDTO> teamsDTO = filteredTeams.stream()
                                              .map(teamMapper::toDTO)
                                              .collect(Collectors.toList());

        model.addAttribute("teams", teamsDTO);
        return "teams";
    }

    @GetMapping("/games")
    public String getGamesPage(
            @ModelAttribute GameSearchParams params,
            Model model
    ) {
        List<Game> filteredGames = gameService.findAllNotDeleted(params);
        List<GameDTO> gamesDTO = filteredGames.stream()
                                              .map(gameMapper::toDTO)
                                              .collect(Collectors.toList());

        model.addAttribute("games", gamesDTO);
        return "games";
    }

    @GetMapping("/tournaments")
    public String getTournamentsPage(
            @ModelAttribute TournamentSearchParams params,
            Model model
    ) {
        List<PointTournament> filteredTournaments = pointTournamentService.findAllNotDeleted(params);
        List<TournamentDTO> tournamentsDTO = filteredTournaments.stream()
                                                                .map(tournamentMapper::toDTO)
                                                                .collect(Collectors.toList());

        model.addAttribute("tournaments", tournamentsDTO);
        return "tournaments";
    }
}
