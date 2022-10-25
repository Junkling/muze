//package hello.muze;
//
//import hello.muze.domain.member.Member;
//import hello.muze.web.repository.member.MemberRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//
//@Component
//@RequiredArgsConstructor
//public class TestDataInit {
//
//    private final MemberRepository memberRepository;
//
//    /**
//     * 테스트용 데이터 추가
//     */
//    @PostConstruct
//    public void init() {
//
//        Member member = new Member();
//        member.setLoginId("test");
//        member.setPassword("1q2w3e4r5t");
//        member.setNickName("테스터");
//        member.setEmail("ddoly0106@naver.com");
//
//        memberRepository.save(member);
//
//    }
//
//}