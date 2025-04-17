package pt.ul.fc.css.soccernow.exception;

public class ResourceDoesNotExistException extends RuntimeException {
    public ResourceDoesNotExistException(String message) {
        super(message);
    }

    public ResourceDoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceDoesNotExistException(String resourceName, String fieldName, Object fieldValue) {
        super("Resource %s with %s = %s does not exist".formatted(resourceName, fieldName, fieldValue));
    }
}
