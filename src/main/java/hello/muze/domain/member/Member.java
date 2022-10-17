package hello.muze.domain.member;

import lombok.Data;


@Data
public class Member {
    private Integer id;

    public Member(String loginId, String nickName, String password, String email) {
        this.loginId = loginId;
        this.nickName = nickName;
        this.password = password;
        this.email = email;
    }

    private String loginId;
    private String nickName;
    private String password;
    private String email;

    private String introduction;
}
