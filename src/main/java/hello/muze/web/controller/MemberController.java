package hello.muze.web.controller;

import hello.muze.domain.member.Member;
import hello.muze.domain.post.Post;
import hello.muze.web.repository.member.MailDto;
import hello.muze.web.repository.member.MemberRepository;
import hello.muze.web.repository.member.MemberUpdateDto;
import hello.muze.web.service.login.PrincipalDetail;
import hello.muze.web.service.login.PwChangeDto;
import hello.muze.web.service.mail.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
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
    private final MemberRepository memberRepository;
    private final MemberValidator memberValidator;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final MailService mailService;



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


    @GetMapping("/detail")
    public String detail(Model model, @AuthenticationPrincipal PrincipalDetail principalDetail) {
        Integer memberId = principalDetail.getMember().getId();
        Member member = memberRepository.findById(memberId).orElseThrow();
        model.addAttribute("member",member);
        log.info("loginId={}", memberId);
        return "users/detail";
    }
    @GetMapping("/detail/update")
    public String update(MemberUpdateDto memberUpdateDto, @AuthenticationPrincipal PrincipalDetail principalDetail) {
        Integer memberId = principalDetail.getMember().getId();
        Member member = memberRepository.findById(memberId).orElseThrow();

        memberUpdateDto.setNickName(member.getNickName());
        memberUpdateDto.setProfile(member.getProfile());

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

        log.info("닉네임 ={}", memberUpdateDto.getNickName());
        log.info("프로필 ={}", memberUpdateDto.getProfile());
        log.info("비밀번호 ={}", principalDetail.getPassword());

        memberRepository.update(memberId, memberUpdateDto);
        HttpSession session = httpServletRequest.getSession(false);
        session.invalidate();

        return "redirect:/";
    }

    @GetMapping("/detail/changePW")
    public String changPWForm(@ModelAttribute PwChangeDto pwChangeDto,@AuthenticationPrincipal PrincipalDetail principalDetail) {
        return "/users/changePW";
    }

    @Transactional
    @PostMapping("/detail/changePW")
    public String changPW(@Valid PwChangeDto pwChangeDto, @AuthenticationPrincipal PrincipalDetail principalDetail, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        Integer memberId = principalDetail.getMember().getId();
        Member member = memberRepository.findById(memberId).orElseThrow();
        String inputPw = pwChangeDto.getOriginalPW();
        boolean matches = bCryptPasswordEncoder.matches(inputPw,principalDetail.getPassword());
        log.info("현재 비밀번호={}", inputPw);
        if(inputPw==null|| matches!=true){
            redirectAttributes.addAttribute("originalFail", true);
            return "redirect:/users/detail/changePW";
        }
        if (bCryptPasswordEncoder.matches(pwChangeDto.getChangedPW(), principalDetail.getPassword())) {
            redirectAttributes.addAttribute("samePW", true);
            return "redirect:/users/detail/changePW";
        }
        if(!pwChangeDto.getChangedPW().equals(pwChangeDto.getCheckChange())){
            redirectAttributes.addAttribute("checkFail", true);
            return "redirect:/users/detail/changePW";
        }

//        비밀번호 변경 코드
        String rawPassword = pwChangeDto.getChangedPW();
        String encodedPassword = bCryptPasswordEncoder.encode(rawPassword);
        memberRepository.changePW(member, encodedPassword);

        HttpSession session = request.getSession(false);
        session.invalidate();

        return "/users/changePwSuccess";
    }

    @GetMapping("/sendEmail")
    public String sendEmailForm(MailDto mailDto, HttpServletRequest request, HttpServletResponse response) {
        return "/users/findPw";
    }


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

        mailService.mailSend(dto);

        return "/users/findPw";
    }


    @DeleteMapping("/{memberId}")
    public String delete(@PathVariable Integer id) {
        try {
            memberRepository.delete(id);
        } catch (EmptyResultDataAccessException e) {
            return "삭제 실패하였습니다(해당 id는 존재하지 않습니다.)";
        }

        return "삭제 완료" + id;
    }

}

