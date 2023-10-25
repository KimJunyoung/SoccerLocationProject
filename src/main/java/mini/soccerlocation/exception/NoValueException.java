package mini.soccerlocation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *  404
 *  @ResponseStatus(value = HttpStatus.NOT_FOUND)
 *  사용해서 에러 코드를 바꿔도 되는데  유지보수 시 어려움이 있다.
 */


public class NoValueException extends MainException {

    private static final String MESSAGE = "존재하는 값이 없습니다.";

    public NoValueException() {
        super(MESSAGE);
    }

    public NoValueException(String fieldName, String message) {
        super(MESSAGE);
    }


    @Override
    public int statusCode() {
        return 404;
    }
}
