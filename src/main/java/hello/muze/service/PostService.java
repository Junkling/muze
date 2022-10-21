package hello.muze.service;

import hello.muze.domain.post.Post;
import hello.muze.domain.post.PostRepository;
import hello.muze.domain.post.PostSearchCond;
import hello.muze.domain.post.PostUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Repository
@RequiredArgsConstructor
public class PostService implements PostServiceInterface {
    private final PostRepository postRepository;

    @Override
    public Post save(Post post) {
        return postRepository.save(post);
    }

    @Override
    public void update(Long postId, PostUpdateDto updateParam) {
        Post findPost = postRepository.findById(postId).orElseThrow();
        findPost.setTitle(updateParam.getTitle());
        findPost.setContents(updateParam.getContents());
        findPost.setUpdated(updateParam.getUpdated());

    }

    @Override
    public Optional<Post> findById(Long id) {
        return postRepository.findById(id);
    }


    @Override
    public List<Post> findPost(PostSearchCond postSearchCond) {
        return postRepository.findAll();
    }

}
