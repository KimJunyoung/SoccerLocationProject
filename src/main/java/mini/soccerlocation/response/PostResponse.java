package mini.soccerlocation.response;

import lombok.Builder;
import lombok.Data;

@Data
public class PostResponse {

    private String title;
    private String content;

    @Builder
    public PostResponse(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
