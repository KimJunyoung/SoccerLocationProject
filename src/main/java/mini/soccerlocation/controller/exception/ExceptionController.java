package mini.soccerlocation.controller.exception;

import mini.soccerlocation.exception.MainException;
import mini.soccerlocation.exception.NoValueException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

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

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> mainExceptionController(MethodArgumentNotValidException e) {

        List<ValidationTuple> errors = e.getFieldErrors().stream().map(f -> new ValidationTuple(f.getField(), f.getDefaultMessage())).collect(Collectors.toList());


        ErrorResponse body = ErrorResponse.builder()
                .code(String.valueOf(400))
                .message("정상적이지 않은 요청입니다.")
                .validationTuples(errors)
                .build();

        ResponseEntity<ErrorResponse> response = ResponseEntity
                .status(BAD_REQUEST)
                .body(body);


        return response;
    }
}
