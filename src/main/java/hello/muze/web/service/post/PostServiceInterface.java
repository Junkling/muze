package hello.muze.web.service.post;

import hello.muze.domain.post.Post;
import hello.muze.web.repository.post.PostSearchCond;
import hello.muze.web.repository.post.PostUpdateDto;

import java.util.List;
import java.util.Optional;

public interface PostServiceInterface {
    Post save(Post post);

    void update(Long postId, PostUpdateDto updateParam);

    Optional<Post> findById(Long id);

//    List<Post> findByCategory(PostSearchCond postSearchCond);

    List<Post> findPost(PostSearchCond postSearchCond);



}
