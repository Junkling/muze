package hello.muze.web.repository.member;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Data
public class MemberUpdateDto {

    private String profile;

    @NotEmpty(message = "닉네임을 입력하세요")
    @Length(min = 2, max = 16,message = "2자리 이상 16자리 이하입니다.")
    private String nickName;

    public MemberUpdateDto(String profile, String nickName) {
        this.profile = profile;
        this.nickName = nickName;
    }
}
