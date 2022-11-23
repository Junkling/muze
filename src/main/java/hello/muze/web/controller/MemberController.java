package hello.muze.web.controller;

import hello.muze.domain.member.Member;
import hello.muze.web.repository.member.MemberRepository;
import hello.muze.web.repository.member.MemberUpdateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
@Slf4j
public class MemberController {
    private final MemberRepository memberRepository;
    private final MemberValidator memberValidator;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
//    private final CheckMemberIdValidator checkMemberIdValidator;
//    private final CheckNickNameValidator checkNickNameValidator;
//    private final CheckEmailValidator checkEmailValidator;


    @GetMapping
    public String addForm(@ModelAttribute Member member) {
        return "users/addForm";
    }

    //    @InitBinder
//    public void init(WebDataBinder dataBinder) {
//        dataBinder.addValidators(checkMemberIdValidator);
//        dataBinder.addValidators(checkNickNameValidator);
//        dataBinder.addValidators(checkEmailValidator);
//    }
    @PostMapping
    public String save(@Valid @ModelAttribute Member member, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        memberValidator.validate(member, bindingResult);

        if (bindingResult.hasErrors()) {
            log.info("error={}", bindingResult);
            return "users/addForm";
        }

        member.setRole("ROLE_USER");
        String rawPassword = member.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        member.setPassword(encPassword);
        memberRepository.save(member);
        /**
         * 이메일 보내는 프로세스 추가
         */
        return "redirect:/";
    }

    @GetMapping("/{userId}")
    public String detail(@PathVariable Integer id, Model model) {
        Member member = memberRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("해당사용자는 없습니다");
        });
        model.addAttribute("member", member);
        return "users/detail";
    }

    @Transactional
    @RequestMapping("/{userId}")
    public String detail(@PathVariable Integer id, @ModelAttribute MemberUpdateDto memberUpdateDto) {
        memberRepository.update(id,memberUpdateDto);
        return "redirect:/users/{userId}";
    }

    @DeleteMapping("/{userId}")
    public String delete(@PathVariable Integer id) {
        try {
            memberRepository.delete(id);
        } catch (EmptyResultDataAccessException e) {
            return "삭제 실패하였습니다(해당 id는 존재하지 않습니다.)";
        }

        return "삭제 완료" + id;
    }
}

