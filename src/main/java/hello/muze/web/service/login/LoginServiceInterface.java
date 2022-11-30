package hello.muze.web.service.login;

import hello.muze.domain.member.Member;
import hello.muze.web.repository.member.MemberUpdateDto;

public interface LoginServiceInterface {
    Member login(String loginId, String password);
    void update(Integer loginId, MemberUpdateDto updateParam);
}
