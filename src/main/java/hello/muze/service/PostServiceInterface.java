package hello.muze.service;

import hello.muze.domain.post.Post;
import hello.muze.domain.post.PostSearchCond;
import hello.muze.domain.post.PostUpdateDto;

import java.util.List;
import java.util.Optional;

public interface PostServiceInterface {
    Post save(Post post);

    void update(Long postId, PostUpdateDto updateParam);

    Optional<Post> findById(Long id);

    List<Post> findPost(PostSearchCond postSearchCond);



}
