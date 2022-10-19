package hello.muze.web.login;

import hello.muze.domain.login.LoginService;
import hello.muze.domain.member.Member;
import hello.muze.web.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static hello.muze.web.SessionConst.LONGIN_MEMBER;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;
//    private final SessionManager sessionManager;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute LoginForm loginForm) {
        return "/login/loginForm";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute LoginForm loginForm, BindingResult bindingResult, HttpServletRequest request) {
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
        return "redirect:/";
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
