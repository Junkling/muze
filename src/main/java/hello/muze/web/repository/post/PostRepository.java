package hello.muze.web.repository.post;

import hello.muze.domain.post.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository {
    Post save(Post post);

    void update(Long postId, PostUpdateDto updateDto);

    Optional<Post> findById(Long id);

    List<Post> findPost(PostSearchCond cond);

//    List<Post> findByCategory(PostSearchCond cond);

}
