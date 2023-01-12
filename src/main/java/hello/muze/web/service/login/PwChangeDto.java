package hello.muze.web.service.login;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class PwChangeDto {
    @NotEmpty(message = "비밀번호를 입력하세요")
    @Pattern(regexp = "^[a-zA-Z0-9]{4,20}$", message = "비밀번호는 영어와 숫자만 사용하여 4~20자리여야 합니다.")
    private String password;
}
