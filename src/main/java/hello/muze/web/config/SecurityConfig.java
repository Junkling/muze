package hello.muze.web.config;

import hello.muze.web.argumentresolber.CustomLoginSuccessHandler;
import hello.muze.web.service.login.LoginService;
import hello.muze.web.service.login.PrincipalDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity  //스프링시큐리티 필터가 스프링 필터 체인에 등록됨
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final LoginService loginService;

    @Bean
    public BCryptPasswordEncoder encodePWD() {
        return new BCryptPasswordEncoder();
    }

    //시큐리티가 대신 로그인할때 받는 password 가 어떤 해쉬로 회원가입이 되었는지 해당 해쉬를 암호화하여 비교가능
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(loginService).passwordEncoder(encodePWD());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/users", "/login", "/posts/list/**","/users/sendEmail", "/logout", "/css/**", "/*.ico", "/error", "/post/{postId}", "/image/**")
                .permitAll()
                .anyRequest()
//                .permitAll()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .usernameParameter("loginId")
                .loginProcessingUrl("/loginProc")
                .successHandler(successHandler())
//                .defaultSuccessUrl("/")
                .and()
                .logout()
                .logoutSuccessUrl("/");

    }

    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return new CustomLoginSuccessHandler("/defaultUrl");
    }
}
