package mini.soccerlocation.repository;

import mini.soccerlocation.domain.Post;
import mini.soccerlocation.request.PostSearch;

import java.util.List;

public interface PostRepositoryCustom {
    List<Post> getList(PostSearch postSearch);
}
