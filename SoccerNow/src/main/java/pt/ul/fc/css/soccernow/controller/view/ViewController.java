package pt.ul.fc.css.soccernow.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pt.ul.fc.css.soccernow.domain.dto.user.PlayerDTO;
import pt.ul.fc.css.soccernow.domain.entities.user.Player;
import pt.ul.fc.css.soccernow.mapper.PlayerMapper;
import pt.ul.fc.css.soccernow.service.PlayerService;
import pt.ul.fc.css.soccernow.util.*;

import java.util.List;
import java.util.stream.Stream;

@Controller
public class ViewController {
    private final PlayerService playerService;
    private final PlayerMapper playerMapper;

    public ViewController(
            PlayerService playerService,
            PlayerMapper playerMapper
    ) {
        this.playerService = playerService;
        this.playerMapper = playerMapper;
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
        Stream<Player> playerStream = filteredPlayers.stream();

        Stream<PlayerDTO> playerDTOStream = playerStream.map(playerMapper::toDTO);
        List<PlayerDTO> players = params.getSize() != null ? playerDTOStream.limit(params.getSize()).toList() : playerDTOStream.toList();

        model.addAttribute("players", players);

        return "players";
    }

    @GetMapping("/referees")
    public String getRefereesPage(
            @ModelAttribute RefereeSearchParams params,
            Model model
    ) {
        // TODO
        model.addAttribute("referees", null);
        return "referees";
    }

    @GetMapping("/teams")
    public String getTeamsPage(
            @ModelAttribute TeamSearchParams params,
            Model model
    ) {
        // TODO
        model.addAttribute("teams", null);
        return "teams";
    }

    @GetMapping("/games")
    public String getGamesPage(
            @ModelAttribute GameSearchParams params,
            Model model
    ) {
        // TODO
        model.addAttribute("games", null);
        return "games";
    }

    @GetMapping("/tournaments")
    public String getTournamentsPage(
            @ModelAttribute TournamentSearchParams params,
            Model model
    ) {
        // TODO
        model.addAttribute("tournaments", null);
        return "tournaments";
    }
}
