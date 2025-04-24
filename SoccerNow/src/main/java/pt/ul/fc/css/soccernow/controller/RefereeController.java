package pt.ul.fc.css.soccernow.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.ul.fc.css.soccernow.domain.dto.user.RefereeDTO;
import pt.ul.fc.css.soccernow.mapper.RefereeMapper;
import pt.ul.fc.css.soccernow.service.RefereeService;

import java.util.List;
import java.util.UUID;

@Tag(name = "Referee", description = "Referee operations")
@RestController
@RequestMapping("/api/referees/")
public class RefereeController {

    private final RefereeService refereeService;
    private final RefereeMapper refereeMapper;

    public RefereeController(RefereeService refereeService,
                             RefereeMapper refereeMapper) {
        this.refereeService = refereeService;
        this.refereeMapper = refereeMapper;
    }

    @PostMapping
    @ApiOperation(value = "Register a referee", notes = "Returns the referee registered")
    public ResponseEntity<RefereeDTO> registerReferee(@RequestBody RefereeDTO referee) {
        // TODO
        return null;
    }

    @GetMapping("/{refereeId}")
    @ApiOperation(value = "Get referee by ID", notes = "Returns a referee by its ID")
    public ResponseEntity<RefereeDTO> getRefereeById(@PathVariable("refereeId") UUID refereeId) {
        // TODO
        return null;
    }

    @GetMapping
    @ApiOperation(value = "Get all referees", notes = "Returns a list of all referees")
    public ResponseEntity<List<RefereeDTO>> getAllReferees() {
        // TODO
        return null;
    }

    @DeleteMapping("/{refereeId}")
    @ApiOperation(value = "Delete a referee with given ID", notes = "Returns the deleted referee")
    public ResponseEntity<RefereeDTO> deleteRefereeById(@PathVariable("refereeId") UUID refereeId) {
        // TODO
        return null;
    }

    @PutMapping("/{refereeId}")
    @ApiOperation(value = "Update a referee with given ID", notes = "Returns the updated referee")
    public ResponseEntity<RefereeDTO> updateRefereeById(@PathVariable("refereeId") UUID refereeId) {
        // TODO
        return null;
    }
}
