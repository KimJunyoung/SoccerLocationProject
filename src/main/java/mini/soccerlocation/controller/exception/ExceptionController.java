package mini.soccerlocation.controller.exception;

import mini.soccerlocation.exception.MainException;
import mini.soccerlocation.exception.NoValueException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionController {

    @ResponseBody
    @ExceptionHandler(MainException.class)
    public ResponseEntity<ErrorResponse> mainExceptionController(MainException e) {
        int status = e.statusCode();

        ErrorResponse body = ErrorResponse.builder()
                .code(String.valueOf(status))
                .message(e.getMessage())
                .validationTuples(e.getTupleList())
                .build();

        ResponseEntity<ErrorResponse> response = ResponseEntity
                .status(status)
                .body(body);

        return response;
    }
}
