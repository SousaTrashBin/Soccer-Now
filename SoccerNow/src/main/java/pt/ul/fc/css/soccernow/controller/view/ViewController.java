package pt.ul.fc.css.soccernow.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import pt.ul.fc.css.soccernow.domain.dto.user.PlayerDTO;
import pt.ul.fc.css.soccernow.domain.entities.user.Player;
import pt.ul.fc.css.soccernow.mapper.PlayerMapper;
import pt.ul.fc.css.soccernow.mapper.RefereeMapper;
import pt.ul.fc.css.soccernow.service.PlayerService;
import pt.ul.fc.css.soccernow.service.RefereeService;
import pt.ul.fc.css.soccernow.util.PlayerSearchParams;
import pt.ul.fc.css.soccernow.util.RefereeSearchParams;

import java.util.List;
import java.util.stream.Stream;

@Controller
public class ViewController {
    private final PlayerService playerService;
    private final PlayerMapper playerMapper;
    private final RefereeService refereeService;
    private final RefereeMapper refereeMapper;

    public ViewController(
            PlayerService playerService,
            PlayerMapper playerMapper,
            RefereeService refereeService,
            RefereeMapper refereeMapper
    ) {
        this.playerService = playerService;
        this.playerMapper = playerMapper;
        this.refereeService = refereeService;
        this.refereeMapper = refereeMapper;
    }

    @GetMapping("/")
    public String getLoginPage() {
        return "login";
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

        return "player";
    }

    @GetMapping("/referees")
    public String getRefereesPage(
            @ModelAttribute RefereeSearchParams params,
            Model model
    ) {
        model.addAttribute("referees", null);
        return "player";
    }
}
