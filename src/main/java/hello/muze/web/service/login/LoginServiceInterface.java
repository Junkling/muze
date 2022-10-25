package hello.muze.web.service.login;

import hello.muze.domain.member.Member;

public interface LoginServiceInterface {
    Member login(String loginId, String password);

}
