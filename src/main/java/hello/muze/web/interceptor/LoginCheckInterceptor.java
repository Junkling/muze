package hello.muze.web.interceptor;

import hello.muze.web.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static hello.muze.web.SessionConst.LONGIN_MEMBER;
@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        HttpSession session = request.getSession();
        if (session == null || session.getAttribute(LONGIN_MEMBER) == null) {
            log.info("리다이렉션");
            response.sendRedirect("/login?redirectURL=" + requestURI);
            return false;
        }
            return true;
    }
}
