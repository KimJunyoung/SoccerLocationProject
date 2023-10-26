package mini.soccerlocation.request;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotNull;

@Data
@Builder
@Slf4j
public class PostSearch {


    @Builder.Default
    private String title = "";

    @Builder.Default
    private String content = "";

    @Builder.Default
    private Integer page = 0;

    public long getOffset() {
        log.info("page = {}" , page);
        return (int)(Math.max(0, page) * 5);
    }

}
