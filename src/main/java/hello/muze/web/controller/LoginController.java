package hello.muze.web.controller;

import hello.muze.domain.member.Member;
import hello.muze.web.service.login.LoginServiceInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static hello.muze.web.SessionConst.LONGIN_MEMBER;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping
public class LoginController {
    private final LoginServiceInterface loginService;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute LoginForm loginForm) {
        return "/login/loginForm";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute LoginForm loginForm, BindingResult bindingResult, @RequestParam(defaultValue = "/") String redirectURL, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "/login/loginForm";
        }
        Member loginId = loginService.login(loginForm.getLoginId(), loginForm.getPassword());
        if (loginId == null) {
            bindingResult.reject("loginFailed", "아이디 또는 패스워드가 맞지 않습니다.");
            return "/login/loginForm";
        }
        HttpSession session = request.getSession();
        session.setAttribute(LONGIN_MEMBER, loginId);
        return "redirect:"+redirectURL;
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        //세션을 종료하는것이 목적이기 때문에 생성 여부를 false로 줌
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }
}