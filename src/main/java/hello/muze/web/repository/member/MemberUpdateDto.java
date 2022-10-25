package hello.muze.web.repository.member;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MemberUpdateDto {
    private String nickName;
    private String password;
    private String profile;
    private LocalDateTime updated;


    public MemberUpdateDto(String nickName, String password, String profile) {

        this.nickName = nickName;
        this.password = password;
        this.profile = profile;
        updated = LocalDateTime.now();
    }

    public MemberUpdateDto() {
    }
}
