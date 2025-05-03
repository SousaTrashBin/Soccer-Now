package pt.ul.fc.css.soccernow.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceDoesNotExistException extends RuntimeException {
    public ResourceDoesNotExistException(String message) {
        super(message);
    }

    public ResourceDoesNotExistException(String resourceName, String fieldName, Object fieldValue) {
        super("Resource %s with %s = %s does not exist".formatted(resourceName, fieldName, fieldValue));
    }
}
