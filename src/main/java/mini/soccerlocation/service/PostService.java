package mini.soccerlocation.service;

import lombok.RequiredArgsConstructor;
import mini.soccerlocation.domain.Post;
import mini.soccerlocation.exception.NoValueException;
import mini.soccerlocation.repository.PostRepository;
import mini.soccerlocation.request.PostCreate;
import mini.soccerlocation.request.PostSearch;
import mini.soccerlocation.response.PostResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public void write(PostCreate postCreate) {
        Post post = Post.builder()
                .title(postCreate.getTitle())
                .content(postCreate.getContent())
                .build();

        postRepository.save(post);
    }

    public PostResponse getOne(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(NoValueException::new);

        return PostResponse.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }

    public List<PostResponse> getByTitleNameAndContent(PostSearch postSearch) {
        List<Post> postLists = postRepository.getList(postSearch);

        List<PostResponse> postResponses = postLists.stream()
                .map(p -> new PostResponse(p.getTitle(), p.getContent()))
                .collect(Collectors.toList());

        return postResponses;
    }

    public List<PostResponse> getAllPost() {
        return postRepository.findAll().stream()
                .map(p -> new PostResponse(p.getTitle(), p.getContent()))
                .collect(Collectors.toList());
    }
}
