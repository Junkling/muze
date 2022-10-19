package hello.muze.domain.member;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

//@Entity

/**
 * 추가해야할 기능: nickName 저장시 중복 방지, 메일 인증 추가, 비밀번호 확인 시스템, 메일로 계정정보 찾기
 */
@Data
@Entity
public class Member {

    public Member(){

    }

    public Member(String loginId, String nickName, String password, String email) {
        this.loginId = loginId;
        this.nickName = nickName;
        this.password = password;
        this.email = email;
    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
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
