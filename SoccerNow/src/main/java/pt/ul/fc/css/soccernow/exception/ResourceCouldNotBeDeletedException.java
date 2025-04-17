package pt.ul.fc.css.soccernow.exception;

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
