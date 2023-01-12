package hello.muze.web.controller;

import hello.muze.domain.member.Member;
import hello.muze.web.service.login.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static hello.muze.web.SessionConst.LONGIN_MEMBER;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping()
public class LoginController {
    private final LoginService loginService;

    @GetMapping("/login")
    public String loginForm(HttpServletRequest request) {
        String referrer = request.getHeader("Referer");
        request.getSession().setAttribute("prevPage", referrer);
        return "/login/SecurityLoginForm";
    }
}

