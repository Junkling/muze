package hello.muze;

import hello.muze.domain.member.Member;
import hello.muze.domain.post.Post;
import hello.muze.web.repository.member.MemberRepository;
import hello.muze.web.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void initMember() {

        Member member = new Member();
        member.setLoginId("test");
        member.setPassword("1q2w3e4r5t");
        member.setNickName("테스터");
        member.setEmail("ddoly0106@naver.com");

        memberRepository.save(member);

    }
    @PostConstruct
    public void initPost() {

        Post post = new Post();
        post.setCategoryId(1);
        post.setCreated(LocalDateTime.now());
        post.setTitle("테스트");
        post.setContents("게시글 테스트");
        post.setCategoryName("자유");
        post.setMemberName("운영자");
        postRepository.save(post);

    }


}