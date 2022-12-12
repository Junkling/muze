package hello.muze.web.controller;

import hello.muze.domain.member.Member;
import hello.muze.web.argumentresolber.Login;
import hello.muze.web.repository.member.MemberRepository;
import hello.muze.web.service.login.PrincipalDetail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import static hello.muze.web.SessionConst.LONGIN_MEMBER;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {
    private final MemberRepository memberRepository;

    @GetMapping("/")
    public String loginHome(@Login Member loginMember, Model model) {
        if (loginMember == null) {
            return "home";
        }
        model.addAttribute("member", loginMember);
        return "loginHome";
    }
}
