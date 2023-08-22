package hello.muze.web.service.mail;

import hello.muze.web.repository.member.MailDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailService {
    private final JavaMailSenderImpl mailSender;

    public MailDto createMailAndChangePassword(String memberEmail, String s) {
        MailDto dto = new MailDto();
        dto.setEmail(memberEmail);
        dto.setTitle("Muze 임시비밀번호 안내 이메일 입니다.");
        dto.setMessage("안녕하세요. Muze 임시비밀번호 안내 관련 이메일 입니다." + System.lineSeparator() + " 회원님의 임시 비밀번호는 "
                + s + " 입니다." + System.lineSeparator() + "로그인 후에 비밀번호를 변경을 해주세요");
        log.info("변경 비밀번호={}", s);
        log.info("port={}", mailSender.getPort());

        return dto;
    }

    //임시 비밀번호로 업데이트
    public String updatePassword(String email) {
        String s = UUID.randomUUID().toString();

        return s;
    }

    public void mailSend(MailDto mailDto) {
        System.out.println("전송 완료!");
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailDto.getEmail());
        message.setSubject(mailDto.getTitle());
        message.setText(mailDto.getMessage());
        message.setFrom("ddoly0106@naver.com");
        message.setReplyTo("ddoly0106@naver.com");
        System.out.println("message" + message);
        mailSender.send(message);
    }

}
