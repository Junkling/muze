package hello.muze.web.repository.member;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Data
public class MemberUpdateDto {

    private String profile;
    @NotEmpty(message = "비밀번호를 입력하세요")
    private String password;

    @NotEmpty(message = "닉네임을 입력하세요")
    @Length(min = 2, max = 16,message = "2자리 이상 16자리 이하입니다.")
    private String nickName;
    private LocalDateTime updated;
    @NotEmpty(message = "이메일 주소를 입력하세요")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;


    public MemberUpdateDto(String nickName, String password, String profile, String email) {

        this.nickName = nickName;
        this.password = password;
        this.profile = profile;
        this.email = email;
        updated = LocalDateTime.now();
    }

    public MemberUpdateDto() {
    }
}
