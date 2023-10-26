package mini.soccerlocation.service;

import lombok.RequiredArgsConstructor;
import mini.soccerlocation.domain.Post;
import mini.soccerlocation.exception.NoValueException;
import mini.soccerlocation.repository.PostRepository;
import mini.soccerlocation.request.PostCreate;
import mini.soccerlocation.request.PostEdit;
import mini.soccerlocation.request.PostEditor;
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
    public Long write(PostCreate postCreate) {
        Post post = Post.builder()
                .title(postCreate.getTitle())
                .content(postCreate.getContent())
                .build();

        Post save = postRepository.save(post);

        return save.getId();
    }

    public PostResponse getOne(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(NoValueException::new);

        return returnPostResponse(post);
    }

    public List<PostResponse> getByTitleNameAndContent(PostSearch postSearch) {
        List<Post> postLists = postRepository.getList(postSearch);

        List<PostResponse> postResponses = postLists.stream()
                .map(p -> new PostResponse(p.getId(), p.getTitle(), p.getContent()))
                .collect(Collectors.toList());

        return postResponses;
    }

    public List<PostResponse> getAllPost() {
        return postRepository.findAll().stream()
                .map(p -> new PostResponse(p.getId(), p.getTitle(), p.getContent()))
                .collect(Collectors.toList());
    }

    public List<PostResponse> getAllPostPagingAndDesc() {
        return getByTitleNameAndContent(PostSearch.builder().build());
    }


    @Transactional
    public PostResponse edit(Long postId, PostEdit postEdit) {
        Post post = postRepository.findById(postId).orElseThrow(NoValueException::new);

        PostEditor.PostEditorBuilder postEditing = post.toEdit();
        PostEditor postEdited = postEditing
                .title(postEdit.getTitle())
                .content(postEdit.getContent())
                .build();

        post.edit(postEdited);

        return returnPostResponse(post);
    }

    private static PostResponse returnPostResponse(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }


    @Transactional
    public void delete(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(NoValueException::new);
        postRepository.delete(post);
    }
}
