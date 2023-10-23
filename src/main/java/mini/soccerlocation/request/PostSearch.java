package mini.soccerlocation.request;

import lombok.Builder;
import lombok.Data;

@Data
public class PostSearch {

    private String title;

    private String content;

    @Builder
    public PostSearch(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
