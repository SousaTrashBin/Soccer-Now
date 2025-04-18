package pt.ul.fc.css.soccernow.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ResourceCouldNotBeDeletedException extends RuntimeException {
    public ResourceCouldNotBeDeletedException(String message) {
        super(message);
    }

    public ResourceCouldNotBeDeletedException(String message, Throwable cause) {
    }

    public ResourceCouldNotBeDeletedException(String resourceName, String fieldName, Object fieldValue) {
        super("Resource %s with %s = %s could not be deleted".formatted(resourceName, fieldName, fieldValue));
    }
}
