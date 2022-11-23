package hello.muze.web.service.login;

import hello.muze.domain.member.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

//스프링 시큐리티가 로그인 요청을 가로채서 로그인을 진행하고 완료되면 UserDetails 타입의 오브젝트를 세션 저장소에 저장
public class PrincipalDetail implements UserDetails {
    private Member member;

    public PrincipalDetail(Member member) {
        this.member = member;
    }

    //계정의 권한 리턴
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
//        collection.add(new GrantedAuthority() {
//            @Override
//            public String getAuthority() {
//                return "" + member.getId();
//            }
//        });
        collection.add(() -> {
            return "" + member.getId();
        });
        return collection;
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getLoginId();
    }

    //계정이 만료되지 않았는지 리턴
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //계정이 잠겨있는지 리턴 (true: 잠기지 않음)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //비밀번호가 만료되지 않았는지 리턴 (true:만료)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //계정이 활성화 상태인지 리턴(true:활성화)
    @Override
    public boolean isEnabled() {
        return true;
    }
}
