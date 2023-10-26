package mini.soccerlocation.domain;

import lombok.Builder;
import lombok.Getter;
import mini.soccerlocation.request.PostEditor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    public Post() {
    }

    @Builder
    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public PostEditor.PostEditorBuilder toEdit() {
        return PostEditor.builder()
                .title(title)
                .content(content);
    }

    public void edit(PostEditor postEditor) {
        this.title = postEditor.getTitle();
        this.content = postEditor.getContent();
    }

}
