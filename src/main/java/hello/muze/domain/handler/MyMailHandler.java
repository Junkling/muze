package hello.muze.domain.handler;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

public class MyMailHandler {
    private JavaMailSender sender;
    private MimeMessage message;
    private MimeMessageHelper messageHelper;

    public MyMailHandler(JavaMailSender sender) throws MessagingException {
        this.sender = sender;
        this.message = this.sender.createMimeMessage();
        this.messageHelper = new MimeMessageHelper(message, true, "UTF-8");
    }

    public void setFrom(String mail, String name) throws UnsupportedEncodingException, MessagingException {
        messageHelper.setFrom(mail, name);
    }

    public void setTo(String mail) throws MessagingException {
        messageHelper.setTo(mail);
    }

    public void setSubject(String subject) throws MessagingException {
        messageHelper.setSubject(subject);
    }

    public void setText(String text) throws MessagingException {
        messageHelper.setText(text, true);
    }

    public void send() {
        try {
            sender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
