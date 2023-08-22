package hello.muze.web.service.login;

import hello.muze.domain.member.Member;
import hello.muze.web.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService implements UserDetailsService {
    private final MemberRepository memberRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member principal = memberRepository.findByMember(username).orElseThrow(()-> {
            return new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다. : " + username);});
        return new PrincipalDetail(principal);// 시큐리티의 세션에 유저정보 저장됨
    }
}
