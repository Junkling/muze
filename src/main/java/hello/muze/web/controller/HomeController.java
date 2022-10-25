package hello.muze.web.controller;

import hello.muze.domain.member.Member;
import hello.muze.web.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import static hello.muze.web.SessionConst.LONGIN_MEMBER;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final MemberRepository memberRepository;

    @GetMapping("/")
    public String loginHome(@SessionAttribute(name = LONGIN_MEMBER,required = false)Member loginMember, Model model) {
        if (loginMember == null) {
            return "home";
        }
        model.addAttribute("member", loginMember);
        return "loginHome";
    }
}
