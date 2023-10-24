package mini.soccerlocation.controller.exception;

import lombok.Data;

@Data
public class ValidationTuple {

    private String fieldName;
    private String errorMessage;

    public ValidationTuple(String fieldName, String errorMessage) {
        this.fieldName = fieldName;
        this.errorMessage = errorMessage;
    }
}
