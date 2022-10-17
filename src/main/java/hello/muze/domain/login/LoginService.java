package hello.muze.domain.login;

import hello.muze.domain.member.Member;
import hello.muze.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final MemberRepository memberRepository;

    public Member login(String loginId, String password) {
        Optional<Member> findMemberId = memberRepository.findByLoginId(loginId);
        Member member = findMemberId.get();
        if (member.getPassword().equals(password)) {
            return member;
        } else {
            return null;
        }
    }

}
