package mini.soccerlocation.service;

import mini.soccerlocation.domain.Post;
import mini.soccerlocation.exception.NoValueException;
import mini.soccerlocation.repository.PostRepository;
import mini.soccerlocation.request.PostCreate;
import mini.soccerlocation.request.PostSearch;
import mini.soccerlocation.response.PostResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    public void clear(){
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("게시글 작성")
    public void test1() {
        // given
        PostCreate postCreate = PostCreate.builder()
                .title("제목")
                .content("내용")
                .build();

        // when
        postService.write(postCreate);

        // then
        assertThat(postRepository.count()).isEqualTo(1);
    }


    @Test
    @DisplayName("게시글 한개 조회")
    public void test2() {
        // given
        PostCreate postCreate = PostCreate.builder()
                .title("제목")
                .content("내용")
                .build();

        postService.write(postCreate);

        // when
        PostResponse post = postService.getOne(1L);

        // then
        assertThat(post.getTitle()).isEqualTo("제목");
        assertThat(post.getContent()).isEqualTo("내용");

    }

    @Test
    @DisplayName("등록되지 않은 게시글 등록")
    public void test3() {
        // given
        Post post = Post.builder()
                .title("제목")
                .content("내용")
                .build();

        postRepository.save(post);

        // expected
        assertThatThrownBy(() -> postService.getOne(post.getId() + 1L)).isInstanceOf(NoValueException.class);
    }

    @Test
    @DisplayName("제목으로 게시글 검색")
    public void test4() {
        // given
        Post post1 = Post.builder()
                .title("제목1")
                .content("내용1")
                .build();
        Post post2 = Post.builder()
                .title("제목1")
                .content("내용1")
                .build();

        postRepository.save(post1);
        postRepository.save(post2);

        PostSearch postSearch = PostSearch.builder()
                .title("제목")
                .build();

        // when
        List<PostResponse> postResponseList = postService.getByTitleNameAndContent(postSearch);

        //then
        assertThat(postResponseList.size()).isEqualTo(2);

    }




}