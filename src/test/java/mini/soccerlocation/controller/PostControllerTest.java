package mini.soccerlocation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import mini.soccerlocation.domain.Post;
import mini.soccerlocation.repository.PostRepository;
import mini.soccerlocation.request.PostCreate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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
    @DisplayName("게시글 한건 조회 - 성공")
    public void test2() throws Exception {
        // given
        Post post = Post.builder()
                .title("제목")
                .content("내용")
                .build();

        postRepository.save(post);

        // expected
        mockMvc.perform(get("/post/{postId}", 1L))
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

}