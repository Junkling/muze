package hello.muze.web.service.login;

import hello.muze.domain.member.Member;
import hello.muze.web.repository.member.MemberRepository;
import hello.muze.web.repository.member.MemberUpdateDto;
import hello.muze.web.repository.member.jpa.JpaMemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService implements LoginServiceInterface {
    private final MemberRepository memberRepository;


    @Override
    public Member login(String loginId, String password) {
        return memberRepository.findMember(loginId)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);
    }
}
