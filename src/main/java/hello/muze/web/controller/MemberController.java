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
    public String update(@Valid @ModelAttribute MemberUpdateDto memberUpdateDto,BindingResult bindingResult, @AuthenticationPrincipal PrincipalDetail principalDetail, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            log.info("error={}", bindingResult);
            return "redirect:/users/detail";
        }
        Integer memberId = principalDetail.getMember().getId();

        log.info("닉네임 ={}", memberUpdateDto.getNickName());
        log.info("프로필 ={}", memberUpdateDto.getProfile());
        log.info("비밀번호 ={}", principalDetail.getPassword());

        memberRepository.update(memberId, memberUpdateDto);


        return "redirect:/users/detail";
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


    /**
     * 비밀번호 변경 코드
     *         String rawPassword = PwChangeDto.getPassword();
     *         String encPassword = bCryptPasswordEncoder.encode(rawPassword);
     *         PwChangeDto.setPassword(encPassword);
     */
}

