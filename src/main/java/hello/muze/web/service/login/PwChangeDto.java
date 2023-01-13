package hello.muze.web.service.login;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
public class PwChangeDto {

    private String originalPW;

    @NotEmpty(message = "비밀번호를 입력하세요")
    private String changedPW;

    private String checkChange;
}
