package mini.soccerlocation.exception;

public class OneMissingException extends MainException{

    /**
     * title 또는 content 값이 누락됐습니다.
     * */

    private  static final String MESSAGE = "title 또는 content 값이 누락됐습니다.";

    public OneMissingException() {
        super(MESSAGE);
    }

    public OneMissingException(String fieldName, String message) {
        super(MESSAGE);
        addValidation(fieldName,message);
    }

    @Override
    public int statusCode() {
        return 400;
    }
}
