package hello.muze.domain.member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberRequestDto {
    @NotEmpty(message = "아이디를 입력하세요")
    @Pattern(regexp = "^[a-z0-9]{4,20}$", message = "아이디는 영어 소문자와 숫자만 사용하여 4~20자리여야 합니다.")
    private String loginId;

    @NotEmpty(message = "닉네임을 입력하세요")
    @Length(min = 2, max = 16, message = "2자리 이상 16자리 이하입니다.")
    private String nickName;
    @NotEmpty(message = "비밀번호를 입력하세요")
    private String password;

    private String passwordConfirm;

    @NotEmpty(message = "이메일 주소를 입력하세요")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;
    private String profile;
}
