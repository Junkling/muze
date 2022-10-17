package hello.muze.web.validation.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class MemberAddFrom {
    @NotEmpty
    private String loginId;
    @NotEmpty
    private String nickName;
    @NotEmpty
    private String password;
    @NotEmpty
    private String email;

    private String introduction;
}
