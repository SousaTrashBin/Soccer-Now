package pt.ul.fc.css.soccernow.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.ul.fc.css.soccernow.domain.entities.Credentials;
import pt.ul.fc.css.soccernow.domain.entities.CredentialsRepository;

import java.util.List;

@Tag(name = "Credentials", description = "Credentials related operations")
@RestController
@RequestMapping("/api/credentials/")
public class CredentialsController {

    private final CredentialsRepository credentialsRepository;

    public CredentialsController(CredentialsRepository credentialsRepository) {
        this.credentialsRepository = credentialsRepository;
    }

    @PostMapping("login/")
    @Operation(
            summary = "Mock logs in an app user",
            description = "Mock logs a person into the app and returns 200 if the given password and username are valid."
    )
    public ResponseEntity<Void> login(@RequestBody @NotNull LoginRequest request) {
        List<Credentials> credentialsForUsername = credentialsRepository.findByUsername(request.username);
        if (credentialsForUsername.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Credentials usernameCredentials = credentialsForUsername.get(0); // username should be unique
        if (!usernameCredentials.getPassword().equals(request.password)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok().build();
    }

    public record LoginRequest(@NotNull String username, @NotNull String password) {
    }
}
