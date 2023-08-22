package hello.muze.web.service.heart;

import hello.muze.domain.heart.Heart;
import hello.muze.domain.member.Member;
import hello.muze.domain.post.Post;
import hello.muze.web.repository.heart.HeartRepository;
import hello.muze.web.repository.post.jpa.SpringDataJpaPostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class HeartService implements HeartServiceInterface {
    private final SpringDataJpaPostRepository postRepository;
    private final HeartRepository heartRepository;


    @Override
    public void save(Heart heart, Long postId, Member member)throws IOException{
        if (findHeartWithUserAndPostId(member, postId).isPresent()) {
            throw new IOException("이미 좋아요 표기된 게시물");
        }
        heart.setMember(member);
        Post findPost = postRepository.findById(postId).orElseThrow();
        heart.setPost(findPost);
        heartRepository.save(heart);

        Integer rawHeartCount = findPost.getHeartCount();
        findPost.setHeartCount((rawHeartCount) + 1);
    }

    @Override
    public void delete(Long postId, Member member) throws IOException{
        Optional<Heart> heartWithUserAndPostId = findHeartWithUserAndPostId(member, postId);
        if (heartWithUserAndPostId.isEmpty()) {
            throw new IOException("좋아요된 게시물을 찾을 수 없습니다.");
        }
        log.info("delete 호출");

        log.info("heartId={}",heartWithUserAndPostId.orElseThrow().getId());
        heartRepository.delete(heartWithUserAndPostId.orElseThrow());

        Post findPost = postRepository.findById(postId).orElseThrow();
        Integer rawHeartCount = findPost.getHeartCount();
        findPost.setHeartCount((rawHeartCount)-1);
    }

    @Override
    public Integer heartCheck(Long postId, Member member) {
        Optional<Heart> heartWithUserAndPostId = findHeartWithUserAndPostId(member, postId);

        if (heartWithUserAndPostId.isEmpty()) {
            return 0;
        } else {
            return 1;
        }
    }

    public Optional<Heart> findHeartWithUserAndPostId(Member member, Long postId) {
        return heartRepository.findByMemberAndPostId(member, postId);
    }

}
