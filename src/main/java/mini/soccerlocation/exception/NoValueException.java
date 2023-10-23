package mini.soccerlocation.exception;

/**
 *  404
 */

public class NoValueException extends MainException {

    private static final String MESSAGE = "존재하는 값이 없습니다.";

    public NoValueException() {
        super(MESSAGE);
    }

    public NoValueException(Throwable cause) {
        super(MESSAGE, cause);
    }


    @Override
    public int statusCode() {
        return 404;
    }
}
