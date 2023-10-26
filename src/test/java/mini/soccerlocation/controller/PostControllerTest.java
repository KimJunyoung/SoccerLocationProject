package mini.soccerlocation.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import mini.soccerlocation.domain.Post;
import mini.soccerlocation.repository.PostRepository;
import mini.soccerlocation.request.PostCreate;
import mini.soccerlocation.request.PostEdit;
import mini.soccerlocation.request.PostSearch;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.JsonPath;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class PostControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    /**
     * API
     *  저장
     *  조회 ( 단건 조회, 전체 조회(페이징 처리), 조건 조회(페이징 처리) )
     *  수정
     *  삭제
     */


    @Test
    @DisplayName("게시글 저장")
    public void test1() throws Exception {
        // given
        PostCreate postCreate = PostCreate.builder()
                .title("제목")
                .content("내용")
                .build();

        String json = objectMapper.writeValueAsString(postCreate);

        // expected
            mockMvc.perform(post("/post/save")
                    .contentType(APPLICATION_JSON)
                    .content(json))
                    .andExpect(status().isOk())
                    .andDo(print());
    }

    @Test
    @DisplayName("게시글 저장시도 -> 입력 값 누락")
    public void test1_1() throws Exception {
        // given
        PostCreate postCreate = PostCreate.builder()
                .title("제목")
                .build();

        String json = objectMapper.writeValueAsString(postCreate);

        // expected
            mockMvc.perform(post("/post/save")
                    .contentType(APPLICATION_JSON)
                    .content(json))
                    .andExpect(status().isBadRequest())
                    .andDo(print());
    }

    @Test
    @DisplayName("게시글 한건 조회 - 성공")
    public void test2() throws Exception {
        // given
        Post post = Post.builder()
                .title("제목")
                .content("내용")
                .build();

        postRepository.save(post);

        // expected
        mockMvc.perform(get("/post/{postId}", post.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("제목"))
                .andExpect(jsonPath("$.content").value("내용"))
                .andDo(print());

    }

    @Test
    @DisplayName("게시글 한건 조회 - 실패")
    public void test3() throws Exception {
        // given
        Post post = Post.builder()
                .title("제목")
                .content("내용")
                .build();

        postRepository.save(post);

        // expected
        mockMvc.perform(get("/post/{postId}", post.getId() + 1L))
                .andExpect(status().isNotFound())
                .andDo(print());

    }


    @Test
    @DisplayName("게시글 전체 조회")
    public void test4() throws Exception {
        // given
        for(int i=1; i<=20; i++){
            Post post = Post.builder()
                    .title("제목 : " + i )
                    .content("내용 : " + i + "번째 글 입니다.")
                    .build();

            postRepository.save(post);
        }

        // expected
        mockMvc.perform(get("/post/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("제목 : 20"))
                .andExpect(jsonPath("$[0].content").value("내용 : 20번째 글 입니다."))
                .andDo(print());

    }

    @Test
    @DisplayName("제목으로 검색")
    public void test5() throws Exception {
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

        String json = objectMapper.writeValueAsString(postSearch);

        // expected
        mockMvc.perform(post("/post/search")
                        .content(json)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[1].title").value("제목 : 29"))
                .andExpect(jsonPath("$[1].content").value("내용 : 29바보입니다."))
                .andDo(print());

    }


    @Test
    @DisplayName("게시글 수정 - 제목/이름")
    public void test6() throws Exception {
        // given
        Post post = Post.builder()
                .title("제목")
                .content("내용")
                .build();

        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title("마유")
                .content("변경된 내용")
                .build();

        String json = objectMapper.writeValueAsString(postEdit);

        // expected
        mockMvc.perform(post("/post/{postId}", post.getId())
                        .content(json)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("마유"))
                .andExpect(jsonPath("$.content").value("변경된 내용"))
                .andDo(print());

    }


    @Test
    @DisplayName("게시글 수정 시 에러 - 없는 글")
    public void test7() throws Exception {
        // given
        Post post = Post.builder()
                .title("제목")
                .content("내용")
                .build();

        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title("마유")
                .content("변경된 내용")
                .build();

        String json = objectMapper.writeValueAsString(postEdit);
        // test

        // expected
        mockMvc.perform(post("/post/{postId}", post.getId() + 1L)
                        .content(json)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }


    @Test
    @DisplayName("게시글 삭제 - 성공")
    public void test8() throws Exception {
        // given
        Post post = Post.builder()
                .title("제목")
                .content("내용")
                .build();

        postRepository.save(post);

        // expected
        mockMvc.perform(delete("/post/{postId}", post.getId()))
                .andExpect(status().isOk())
                .andDo(print());
    }


}