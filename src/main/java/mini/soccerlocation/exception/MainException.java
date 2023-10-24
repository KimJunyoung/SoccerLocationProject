package mini.soccerlocation.exception;


import lombok.Getter;
import mini.soccerlocation.controller.exception.ValidationTuple;

import java.util.ArrayList;
import java.util.List;

@Getter
public abstract class MainException extends RuntimeException {

    public final List<ValidationTuple> tupleList = new ArrayList<>();

    public MainException(String message) {
        super(message);
    }

    public MainException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract int statusCode();

    public void addValidation(String fieldName, String errorMessage) {
        tupleList.add(new ValidationTuple(fieldName, errorMessage));
    }

}
