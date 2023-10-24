package mini.soccerlocation.controller.exception;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class ErrorResponse {

    private String code;
    private String message;
    private List<ValidationTuple> validationTuples;


    @Builder
    public ErrorResponse(String code, String message, List<ValidationTuple> validationTuples) {
        this.code = code;
        this.message = message;
        this.validationTuples = validationTuples;
    }


}
