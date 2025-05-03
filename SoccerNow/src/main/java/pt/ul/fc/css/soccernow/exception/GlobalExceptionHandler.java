package pt.ul.fc.css.soccernow.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> handleBadRequestException(BadRequestException exception) {
        Map<String, Object> body = Map.of(
                "status", HttpStatus.BAD_REQUEST.value(),
                "error", "Bad Request",
                "message", exception.getMessage()
        );
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<Object> handleResourceAlreadyExistsException(ResourceAlreadyExistsException exception) {
        Map<String, Object> body = Map.of(
                "status", HttpStatus.CONFLICT.value(),
                "error", "Conflict",
                "message", exception.getMessage()
        );
        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ResourceDoesNotExistException.class)
    public ResponseEntity<Object> handleResourceDoesNotExistException(ResourceDoesNotExistException exception) {
        Map<String, Object> body = Map.of(
                "status", HttpStatus.NOT_FOUND.value(),
                "error", "Not Found",
                "message", exception.getMessage()
        );
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceCouldNotBeDeletedException.class)
    public ResponseEntity<Object> handleResourceCouldNotBeDeletedException(ResourceCouldNotBeDeletedException exception) {
        Map<String, Object> body = Map.of(
                "status", HttpStatus.CONFLICT.value(),
                "error", "Conflict",
                "message", exception.getMessage()
        );
        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

}
