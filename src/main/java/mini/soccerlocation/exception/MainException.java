package mini.soccerlocation.exception;


public abstract class MainException extends RuntimeException {

    public MainException(String message) {
        super(message);
    }

    public MainException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract int statusCode();

}
