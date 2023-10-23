package mini.soccerlocation.repository;

import mini.soccerlocation.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long>, PostRepositoryCustom{

}
