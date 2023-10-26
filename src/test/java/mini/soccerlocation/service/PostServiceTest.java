package mini.soccerlocation.service;

import mini.soccerlocation.domain.Post;
import mini.soccerlocation.exception.NoValueException;
import mini.soccerlocation.repository.PostRepository;
import mini.soccerlocation.request.PostCreate;
import mini.soccerlocation.request.PostEdit;
import mini.soccerlocation.request.PostSearch;
import mini.soccerlocation.response.PostResponse;
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
        Post post = Post.builder()
                .title("제목")
                .content("내용")
                .build();

        postRepository.save(post);

        // when
        PostResponse postResponse = postService.getOne(post.getId());

        // then
        assertThat(postResponse.getTitle()).isEqualTo("제목");
        assertThat(postResponse.getContent()).isEqualTo("내용");

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

    @Test
    @DisplayName("전체 조회")
    public void test5(){
        // given
        Post post1 = Post.builder()
                .title("제목1")
                .content("내용1")
                .build();
        Post post2 = Post.builder()
                .title("제목2")
                .content("내용2")
                .build();

        postRepository.save(post1);
        postRepository.save(post2);

        // when
        List<PostResponse> allPost = postService.getAllPost();

        // then
        assertThat(allPost.get(0).getTitle()).isEqualTo("제목1");
    }



    @Test
    @DisplayName("글 전체 검색 - 페이징 처리")
    public void test6() {
        // given
        for(int i=1; i<=20; i++){
            Post post = Post.builder()
                    .title("제목 : " + i )
                    .content("내용 : " + i + "번째 글 입니다.")
                    .build();

            postRepository.save(post);
        }


        PostSearch postSearch = PostSearch.builder()
                .page(1)
                .build();

        // when
        List<PostResponse> postResponseList = postService.getByTitleNameAndContent(postSearch);

        //then
        assertThat(postResponseList.size()).isEqualTo(5);
        assertThat(postResponseList.get(0).getTitle()).isEqualTo("제목 : 15");
        assertThat(postResponseList.get(0).getContent()).isEqualTo("내용 : 15번째 글 입니다.");


    }

    @Test
    @DisplayName("제목으로 검색 - 페이징 ")
    public void test7() {
        // given
        for(int i=1; i<=20; i++){
            Post post = Post.builder()
                    .title("제목 : " + i )
                    .content("내용 : " + i + "번째 글 입니다.")
                    .build();

            postRepository.save(post);
        }

        for(int i=21; i<=30; i++){
            Post post = Post.builder()
                    .title("제목 : " + i )
                    .content("내용 : " + i + "바보입니다.")
                    .build();

            postRepository.save(post);
        }


        PostSearch postSearch = PostSearch.builder()
                .content("바보")
                .build();

        // when
        List<PostResponse> postResponseList = postService.getByTitleNameAndContent(postSearch);

        //then
        assertThat(postResponseList.size()).isEqualTo(5);
        assertThat(postResponseList.get(0).getTitle()).isEqualTo("제목 : 30");


    }

    @Test
    @DisplayName("게시글 수정 - 제목, 내용 모두 입력")
    public void test8() {
        // given
        Post post = Post.builder()
                .title("제목")
                .content("내용")
                .build();

        postRepository.save(post);

        // when
        PostEdit postEdit = PostEdit.builder()
                .title("마유")
                .content("바보")
                .build();

        PostResponse postResponse = postService.edit(post.getId(), postEdit);

        // then
        assertThat(postResponse.getTitle()).isEqualTo("마유");
    }


    @Test
    @DisplayName("게시글 수정 - 제목만 입력")
    public void test9() {
        // given
        Post post = Post.builder()
                .title("제목")
                .content("내용")
                .build();

        postRepository.save(post);

        // when
        PostEdit postEdit = PostEdit.builder()
                .title("마유")
                .build();

        PostResponse postResponse = postService.edit(post.getId(), postEdit);

        // then
        assertThat(postResponse.getTitle()).isEqualTo("마유");
        assertThat(postResponse.getContent()).isEqualTo("내용");
    }

    @Test
    @DisplayName("게시글 수정 - 없는 글 에러")
    public void test10() {
        // when
        PostEdit postEdit = PostEdit.builder()
                .title("마유")
                .build();

        assertThatThrownBy(() -> postService.edit(10L, postEdit)).isInstanceOf(NoValueException.class);
    }

    @Test
    @DisplayName("게시글 삭제 - 성공")
    public void test11() {
        // given
        Post post = Post.builder()
                .title("제목")
                .content("내용")
                .build();

        postRepository.save(post);

        // when
        postService.delete(post.getId());

        // then
        assertThat(postRepository.count()).isEqualTo(0);
    }

    @Test
    @DisplayName("게시글 삭제 - 없는 글 에러")
    public void test12() {
        assertThatThrownBy(() -> postService.delete(1L)).isInstanceOf(NoValueException.class);
    }



}