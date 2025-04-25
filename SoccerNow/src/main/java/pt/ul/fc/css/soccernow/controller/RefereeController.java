package pt.ul.fc.css.soccernow.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pt.ul.fc.css.soccernow.domain.dto.user.RefereeDTO;
import pt.ul.fc.css.soccernow.domain.entities.user.Referee;
import pt.ul.fc.css.soccernow.mapper.RefereeMapper;
import pt.ul.fc.css.soccernow.service.RefereeService;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

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
    public ResponseEntity<RefereeDTO> registerReferee(@RequestBody @Validated @NotNull RefereeDTO refereeDTO) {
        if (refereeDTO.getName() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Referee referee = refereeMapper.toEntity(refereeDTO);
        Referee savedReferee = refereeService.add(referee);
        return ResponseEntity.status(HttpStatus.CREATED).body(refereeMapper.toDTO(savedReferee));
    }

    @GetMapping("/{refereeId}")
    @ApiOperation(value = "Get referee by ID", notes = "Returns a referee by its ID")
    public ResponseEntity<RefereeDTO> getRefereeById(@PathVariable("refereeId") @NotNull UUID refereeId) {
        Referee referee = refereeService.findNotDeletedById(refereeId);
        return ResponseEntity.ok(refereeMapper.toDTO(referee));
    }

    @GetMapping
    @ApiOperation(value = "Get all referees", notes = "Returns a list of all referees")
    public ResponseEntity<List<RefereeDTO>> getAllReferees(@RequestParam(name = "size", required = false) @Min(0) Integer size,
                                                           @RequestParam(name = "order", required = false) String order) {
        Comparator<Referee> officiatedGamesComparator = Comparator.comparing(Referee::getClosedGamesCount);
        Optional<Comparator<Referee>> optionalRefereeComparator = switch (order) {
            case "asc" -> Optional.of(officiatedGamesComparator.reversed());
            case "dsc" -> Optional.of(officiatedGamesComparator);
            default -> Optional.empty();
        };

        Stream<Referee> refereeStream = refereeService.findAllNotDeleted().stream();
        if (optionalRefereeComparator.isPresent()) {
            refereeStream = refereeStream.sorted(optionalRefereeComparator.get());
        }

        Stream<RefereeDTO> refereeDTOStream = refereeStream.map(refereeMapper::toDTO);
        List<RefereeDTO> referees = size != null ? refereeDTOStream.limit(size).toList() : refereeDTOStream.toList();
        return ResponseEntity.ok(referees);
    }

    @DeleteMapping("/{refereeId}")
    @ApiOperation(value = "Delete a referee with given ID")
    public ResponseEntity<String> deleteRefereeById(@PathVariable("refereeId") @NotNull UUID refereeId) {
        refereeService.softDelete(refereeId);
        return ResponseEntity.ok("Referee deleted successfully");
    }

    @PutMapping("/{refereeId}")
    @ApiOperation(value = "Update a referee with given ID", notes = "Returns the updated referee")
    public ResponseEntity<RefereeDTO> updateRefereeById(
            @PathVariable("refereeId") @NotNull UUID refereeId,
            @RequestBody @Validated @NotNull RefereeDTO refereeDTO
    ) {
        refereeDTO.setId(refereeId);
        Referee referee = refereeMapper.toEntity(refereeDTO);
        Referee savedReferee = refereeService.update(referee);
        return ResponseEntity.ok(refereeMapper.toDTO(savedReferee));
    }
}
