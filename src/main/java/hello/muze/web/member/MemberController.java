package hello.muze.web.member;

import hello.muze.domain.member.Member;
import hello.muze.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class MemberController {
    private final MemberRepository memberRepository;

    @GetMapping
    public String addForm(@ModelAttribute("users") Member member) {
        return "users/addForm";
    }

    @PostMapping
    public String save(@Valid @ModelAttribute("users") Member member, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "users/addForm";
        }
        memberRepository.save(member);
        return "redirect:/";
    }
}
