package pt.ul.fc.css.soccernow.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.ul.fc.css.soccernow.domain.dto.user.reference.RefereeDto;
import pt.ul.fc.css.soccernow.service.RefereeService;

import java.util.List;

@Tag(name = "Referee", description = "Referee operations")
@RestController
@RequestMapping("/api/referees/")
public class RefereeController {

    private final RefereeService refereeService;

    public RefereeController(RefereeService refereeService) {
        this.refereeService = refereeService;
    }

    @PostMapping
    @ApiOperation(value = "Register a referee", notes = "Returns the referee registered")
    public ResponseEntity<RefereeDto> registerReferee(@RequestBody RefereeDto referee) {
        // TODO
        return null;
    }

    @GetMapping("/{refereeId}")
    @ApiOperation(value = "Get referee by ID", notes = "Returns a referee by its ID")
    public ResponseEntity<RefereeDto> getRefereeById(@PathVariable("refereeId") long refereeId) {
        // TODO
        return null;
    }

    @GetMapping
    @ApiOperation(value = "Get all referees", notes = "Returns a list of all referees")
    public ResponseEntity<List<RefereeDto>> getAllReferees() {
        // TODO
        return null;
    }

    @DeleteMapping("/{refereeId}")
    @ApiOperation(value = "Delete a referee with given ID", notes = "Returns the deleted referee")
    public ResponseEntity<RefereeDto> deleteRefereeById(@PathVariable("refereeId") long refereeId) {
        // TODO
        return null;
    }

    @PutMapping("/{refereeId}")
    @ApiOperation(value = "Update a referee with given ID", notes = "Returns the updated referee")
    public ResponseEntity<RefereeDto> updateRefereeById(@PathVariable("refereeId") long refereeId) {
        // TODO
        return null;
    }
}
