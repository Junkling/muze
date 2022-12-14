package hello.muze.web.controller;

import hello.muze.domain.member.Member;
import hello.muze.domain.post.Post;
import hello.muze.web.repository.member.MemberRepository;
import hello.muze.web.repository.member.MemberUpdateDto;
import hello.muze.web.service.login.PrincipalDetail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    @Autowired
    private AuthenticationManager authenticationManager;


    @GetMapping
    public String addForm(@ModelAttribute Member member) {
        return "users/addForm";
    }

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

        return "redirect:/";
    }


    @GetMapping("/update")
    public String update(Model model, @AuthenticationPrincipal PrincipalDetail principalDetail) {
        Integer memberId = principalDetail.getMember().getId();
        log.info("loginId={}", memberId);
        model.addAttribute("member", principalDetail.getMember());
        return "users/updateForm";
    }

    @Transactional
    @PostMapping("/update")
    public String update(@ModelAttribute MemberUpdateDto memberUpdateDto, @AuthenticationPrincipal PrincipalDetail principalDetail, RedirectAttributes redirectAttributes) {
        Integer memberId = principalDetail.getMember().getId();
        log.info("???????????? ={}", memberUpdateDto.getPassword());
        log.info("????????? ={}", memberUpdateDto.getNickName());
        log.info("????????? ={}", memberUpdateDto.getProfile());
        String rawPassword = memberUpdateDto.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        memberUpdateDto.setPassword(encPassword);
        memberRepository.update(memberId, memberUpdateDto);
        //?????? ??????

        return "redirect:/login";
    }

    @DeleteMapping("/{memberId}")
    public String delete(@PathVariable Integer id) {
        try {
            memberRepository.delete(id);
        } catch (EmptyResultDataAccessException e) {
            return "?????? ?????????????????????(?????? id??? ???????????? ????????????.)";
        }

        return "?????? ??????" + id;
    }
}

