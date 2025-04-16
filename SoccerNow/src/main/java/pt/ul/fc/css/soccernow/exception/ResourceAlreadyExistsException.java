package pt.ul.fc.css.soccernow.exception;

public class ResourceAlreadyExistsException extends RuntimeException {
    public ResourceAlreadyExistsException(String message) {
        super(message);
    }
    public ResourceAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
    public ResourceAlreadyExistsException(String resourceName, String fieldName, Object fieldValue) {
        super("Resource %s with %s = %s already exists".formatted(resourceName, fieldName, fieldValue));
    }
}
