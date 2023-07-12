package hello.muze.web.controller;

import hello.muze.domain.member.Member;
import hello.muze.domain.member.MemberRequestDto;
import hello.muze.domain.member.MemberResponseDto;
import hello.muze.web.appService.MemberAppService;
import hello.muze.web.appService.MemberValidator;
import hello.muze.web.repository.member.MailDto;
import hello.muze.web.repository.member.MemberUpdateDto;
import hello.muze.web.service.login.PrincipalDetail;
import hello.muze.web.service.login.PwChangeDto;
import hello.muze.web.service.mail.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
@Slf4j
public class MemberController {


    private final MemberAppService memberAppService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    private final MailService mailService;
    private final MemberValidator memberValidator;


    @GetMapping
    public String addForm(@ModelAttribute Member member) {
        return "users/addForm";
    }

    @PostMapping
    public String save(@Valid @ModelAttribute MemberRequestDto dto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {


        memberValidator.validate(dto, bindingResult);

        if (bindingResult.hasErrors()) {
            log.info("error={}", bindingResult);
            return "users/addForm";
        }

        String encPassword = bCryptPasswordEncoder.encode(dto.getPassword());
        memberAppService.save(dto,encPassword);

        return "redirect:/";
    }


    @GetMapping("/detail")
    public String detail(Model model, @AuthenticationPrincipal PrincipalDetail principalDetail) {
        Integer memberId = principalDetail.getMember().getId();
        MemberResponseDto dto= memberAppService.findById(memberId);
        model.addAttribute("member", dto);
        log.info("loginId={}", memberId);
        return "users/detail";
    }

    @GetMapping("/detail/update")
    public String update(MemberUpdateDto memberUpdateDto, @AuthenticationPrincipal PrincipalDetail principalDetail) {
        Integer memberId = principalDetail.getMember().getId();
        MemberResponseDto dto= memberAppService.findById(memberId);

        log.info("loginId={}", memberId);
        return "users/updateForm";
    }

    @Transactional
    @PostMapping("/detail/update")
    public String update(@Valid @ModelAttribute MemberUpdateDto memberUpdateDto, BindingResult bindingResult, @AuthenticationPrincipal PrincipalDetail principalDetail, RedirectAttributes redirectAttributes, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            log.info("error={}", bindingResult);
            return "redirect:/users/detail";
        }
        Integer memberId = principalDetail.getMember().getId();

        memberAppService.update(memberId, memberUpdateDto);
        HttpSession session = httpServletRequest.getSession(false);
        session.invalidate();

        return "redirect:/";
    }

    @GetMapping("/detail/changePW")
    public String changPWForm(@ModelAttribute PwChangeDto pwChangeDto, @AuthenticationPrincipal PrincipalDetail principalDetail) {
        return "/users/changePW";
    }

    @Transactional
    @PostMapping("/detail/changePW")
    public String changPW(@Valid PwChangeDto pwChangeDto, @AuthenticationPrincipal PrincipalDetail principalDetail,BindingResult bindingResult, HttpServletRequest request) {
        Integer memberId = principalDetail.getMember().getId();
        String original = principalDetail.getPassword();
        boolean matches = bCryptPasswordEncoder.matches(pwChangeDto.getOriginalPW(), original);
        boolean samePw = bCryptPasswordEncoder.matches(pwChangeDto.getChangedPW(), original);
        memberAppService.validChangePw(pwChangeDto, matches, samePw, bindingResult);

        if (bindingResult.hasErrors()) {
            log.info("error={}", bindingResult);
            return "users/changePW";
        }
        String rawPassword = pwChangeDto.getChangedPW();
        String encodedPassword = bCryptPasswordEncoder.encode(rawPassword);
        memberAppService.changPw(memberId, encodedPassword);

        HttpSession session = request.getSession(false);
        session.invalidate();

        return "/users/changePwSuccess";
    }

    @GetMapping("/sendEmail")
    public String sendEmailForm(MailDto mailDto, HttpServletRequest request, HttpServletResponse response) {
        return "/users/findPw";
    }

/**
 * 추가 작업 필요
 *
    @Transactional
    @PostMapping("/sendEmail")
    public String sendEmail(Member member, MailDto mailDto, RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response) {
        log.info("변경할 ID={}", mailDto.getLoginId());
        String memberEmail = mailDto.getEmail();
        String memberId = mailDto.getLoginId();
        Member findMember = memberRepository.findByMember(memberId).orElseThrow();

        if (!member.getEmail().equals(memberEmail)) {
            redirectAttributes.addAttribute("checkFail", true);
            return "redirect:/users/sendEmail";
        }

        String rawRandomPW = mailService.updatePassword(memberEmail);
        String encodePW = bCryptPasswordEncoder.encode(rawRandomPW);
        MailDto dto = mailService.createMailAndChangePassword(memberEmail, rawRandomPW);
        dto.setLoginId(mailDto.getLoginId());

        memberRepository.changePW(findMember, encodePW);
        log.info("변경 암호화 비밀번호={}", findMember.getPassword());

//        mailService.mailSend(dto);

        return "/users/findPw";
    }

**/

    @DeleteMapping("/{memberId}")
    public String delete(@PathVariable Integer id) {
        return memberAppService.delete(id);
    }

}

