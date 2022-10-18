package hello.muze.domain.member;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

//@Entity
@Data
public class Member {

    public Member(){

    }

    public Member(String loginId, String nickName, String password, String email) {
        this.loginId = loginId;
        this.nickName = nickName;
        this.password = password;
        this.email = email;
    }

    @Id
    private Integer id;
    @Id
    @NotEmpty(message = "아이디를 입력하세요")
    private String loginId;
    @NotEmpty(message = "닉네임을 입력하세요")
    @Length(min = 2, max = 16,message = "2자리 이상 6자리 이하입니다.")
    private String nickName;
    @NotEmpty(message = "비밀번호를 입력하세요")
    @Length(min = 8, max = 16)
    private String password;

    @Length(min = 8, max = 16)
    private String passwordCheck;

    @NotEmpty(message = "이메일 주소를 입력하세요")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    private String profile;

    private LocalDateTime created;

    private LocalDateTime updated;


}
