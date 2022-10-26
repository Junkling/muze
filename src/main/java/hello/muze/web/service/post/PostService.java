package hello.muze.web.service.post;

import hello.muze.domain.post.Post;
import hello.muze.web.repository.member.MemberRepository;
import hello.muze.web.repository.post.PostRepository;
import hello.muze.web.repository.post.PostSearchCond;
import hello.muze.web.repository.post.PostUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Repository
@RequiredArgsConstructor
public class PostService implements PostServiceInterface {
    private final PostRepository postRepository;

    @Override
    public Post save(Post post) {
        post.setCreated(LocalDateTime.now());
        return postRepository.save(post);
    }

    @Override
    public void update(Long postId, PostUpdateDto updateParam) {
        postRepository.update(postId, updateParam);
    }

    @Override
    public Optional<Post> findById(Long id) {
        return postRepository.findById(id);
    }

    @Override
    public List<Post> findByCategory(PostSearchCond postSearchCond) {
        return postRepository.findByCategory(postSearchCond);
    }

    @Override
    public List<Post> findPost(PostSearchCond postSearchCond) {
        return postRepository.findPost(postSearchCond);
    }
}
