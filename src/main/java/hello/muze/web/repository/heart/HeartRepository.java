package hello.muze.web.repository.heart;

import hello.muze.domain.heart.Heart;
import hello.muze.domain.member.Member;
import hello.muze.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HeartRepository extends JpaRepository<Heart, Long> {
    Optional<Heart> findByMemberAndPostId(Member member, Long postId);
}
