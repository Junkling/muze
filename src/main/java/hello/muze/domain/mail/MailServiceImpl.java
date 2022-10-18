//package hello.muze.domain.mail;
//
//import hello.muze.domain.handler.MyMailHandler;
//import hello.muze.domain.member.Member;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.stereotype.Service;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Service
//@RequiredArgsConstructor
//public class MailServiceImpl implements MailService {
//
//    final private JavaMailSender sender;
//    final private Member member;
//    @Override
//    public Map<String, Object> send(String email, String title, String body) {
//
//        MyMailHandler mail;
//        try {
//            mail = new MyMailHandler(sender);
//            mail.setFrom("master@muze.com", "운영자");
//            mail.setTo(email);
//            mail.setSubject(title);
//            mail.setText(body);
//            mail.send();
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//        String resultCode = "S-1";
//        String msg = "메일이 발송되었습니다.";
//        Map<String, Object> rs = new HashMap<>();
//        rs.put("resultCode", resultCode);
//        rs.put("msg", msg);
//        return rs;
//    }
//}
