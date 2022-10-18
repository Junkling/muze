//package hello.muze.web.controller;
//
//import hello.muze.domain.mail.MailService;
//import hello.muze.domain.mail.MailServiceImpl;
//import hello.muze.domain.member.Member;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Map;
//@RequiredArgsConstructor
//@Controller
//public class MailController {
//    private final MailServiceImpl mailService;
//
//
//    @GetMapping("mail/send")
//    public String showSend() {
//        return "mail/send";
//    }
//
//    @PostMapping("mail/send")
//    @ResponseBody
//    public String doSend(@ModelAttribute("users") Member member, String title, String body) {
//        Map<String, Object> sendRs = mailService.send(member.getEmail(), title, body);
//
//        return (String) sendRs.get("msg");
//    }
//}
