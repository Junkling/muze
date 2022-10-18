package hello.muze.web.member;

import hello.muze.domain.member.Member;
import hello.muze.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
@Slf4j
public class MemberController {
    private final MemberRepository memberRepository;

    @GetMapping
    public String addForm(@ModelAttribute Member member) {
        return "users/addForm";
    }

    @PostMapping
    public String save(@Valid @ModelAttribute Member member, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("error={}", bindingResult);
            return "users/addForm";
        }
//        if (member.getPassword().equals(member.getPasswordCheck())==false) {
//            bindingResult.reject("passwordCheck", "비밀번호가 일치하지 않습니다.");
//            return "users/addForm";
//        }
//        Optional<Member> oldId = memberRepository.findByLoginId(member.getLoginId());
//        if (oldId != null) {
//            bindingResult.rejectValue("loginId", null, "중복된 아이디입니다.");
//        }
        memberRepository.save(member);
        /**
         * 이메일 보내는 프로세스 추가
         */
        return "redirect:/";
    }

//    @GetMapping("/emailCheck")
//    public String emailCheck(@ModelAttribute Member member, HttpServletRequest req) {
//
//        return "users/emailCheck";
//    }

//    @PostMapping("/emailCheck")
//    public String saveCom(@Valid @ModelAttribute("users") Member member) {
//        /**
//         * 이메일 인증 프로세스 추가
//         */
//        memberRepository.save(member);
//        return "/";
//    }
}

