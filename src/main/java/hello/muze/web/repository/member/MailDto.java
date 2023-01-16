package hello.muze.web.repository.member;

import lombok.Data;

@Data
public class MailDto {
    private String loginId;
    private String email;
    private String title;
    private String message;
}
