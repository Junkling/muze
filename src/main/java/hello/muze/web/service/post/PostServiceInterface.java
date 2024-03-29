package hello.muze.web.service.post;

import hello.muze.domain.member.Member;
import hello.muze.domain.post.Post;
import hello.muze.web.repository.post.PostSearchCond;
import hello.muze.web.repository.post.PostUpdateDto;

import java.util.List;
import java.util.Optional;


// SOLID 원칙중에 단일책임원칙이라고 있어
public interface PostServiceInterface {
    Post save(Post post, Member member);

    void update(Long postId, PostUpdateDto updateParam);

    Optional<Post> findById(Long id);

    List<Post> findPost(PostSearchCond postSearchCond);

    List<Post> top5Post();

    void delete(Long postId);

}
