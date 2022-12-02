package hello.muze.web.config;

//import hello.muze.web.controller.validator.CheckEmailValidator;
//import hello.muze.web.controller.validator.CheckMemberIdValidator;
//import hello.muze.web.controller.validator.CheckNickNameValidator;
import hello.muze.web.repository.comment.jpa.SpringDataJpaCommentRepository;
import hello.muze.web.repository.member.MemberRepository;
import hello.muze.web.repository.member.jpa.JpaMemberRepository;
import hello.muze.web.repository.member.jpa.SpringDataJpaMemberRepository;
import hello.muze.web.repository.post.jpa.PostQueryPostRepository;
import hello.muze.web.repository.post.jpa.SpringDataJpaPostRepository;
import hello.muze.web.service.comment.CommentService;
import hello.muze.web.service.comment.CommentServiceInterface;
import hello.muze.web.service.login.LoginService;
import hello.muze.web.service.post.PostService;
import hello.muze.web.service.post.PostServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

@Configuration
@RequiredArgsConstructor
public class JpaConfig {
    private final SpringDataJpaMemberRepository memberRepository;
    private final SpringDataJpaPostRepository postRepository;
    private final SpringDataJpaCommentRepository commentRepository;
    private final EntityManager em;


    @Bean
    public LoginService loginService() {
        return new LoginService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        return new JpaMemberRepository(memberRepository);
    }
    @Bean
    public PostServiceInterface postService() {
        return new PostService(postRepository(),postRepository);
    }
    @Bean
    public PostQueryPostRepository postRepository() {
        return new PostQueryPostRepository(em);
    }
    @Bean
    public CommentServiceInterface commentService() {
        return new CommentService(commentRepository);
    }


}
