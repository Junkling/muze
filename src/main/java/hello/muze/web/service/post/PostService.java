package hello.muze.web.service.post;

import hello.muze.domain.attachment.Attachment;
import hello.muze.domain.member.Member;
import hello.muze.domain.post.Post;
import hello.muze.domain.post.PostResponseDto;
import hello.muze.web.repository.post.PostSearchCond;
import hello.muze.web.repository.post.PostUpdateDto;
import hello.muze.web.repository.post.jpa.PostQueryPostRepository;
import hello.muze.web.repository.post.jpa.SpringDataJpaPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService implements PostServiceInterface {
    private final PostQueryPostRepository postQueryPostRepository;
    private final SpringDataJpaPostRepository springDataJpaPostRepository;



    @Override
    public Post save(Post post, Member member) {
        post.setView(0);
        post.setHeartCount(0);
        post.setMember(member);
        return springDataJpaPostRepository.save(post);
    }

    @Override
    public void update(Long postId, PostUpdateDto updateParam) {
        Post findPost = springDataJpaPostRepository.findById(postId).orElseThrow();
        findPost.setTitle(updateParam.getTitle());
        findPost.setContents(updateParam.getContents());
        findPost.setCategoryType(updateParam.getCategoryType());
    }

    @Override
    public Optional<Post> findById(Long id) {


        return springDataJpaPostRepository.findById(id);
    }



    @Override
    public void delete(Long postId) {
        springDataJpaPostRepository.delete(
                springDataJpaPostRepository.findById(postId).orElseThrow(()-> new EntityNotFoundException("게시물이 존재하지 않습니다."))
        );
    }

    @Override
    public List<Post> findPost(PostSearchCond postSearchCond) {
        return postQueryPostRepository.findPost(postSearchCond);
    }

    @Override
    public List<Post> top5Post() {
        return postQueryPostRepository.hotPost();
    }
}
