package pt.ul.fc.css.soccernow.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.ul.fc.css.soccernow.service.RefereeService;

@Tag(name = "Referee", description = "Referee operations")
@RestController
@RequestMapping("/api/referee/")
public class RefereeController {

    private final RefereeService refereeService;

    public RefereeController(RefereeService refereeService) {
        this.refereeService = refereeService;
    }
}
