package hello.muze.web.config;

//import hello.muze.web.controller.validator.CheckEmailValidator;
//import hello.muze.web.controller.validator.CheckMemberIdValidator;
//import hello.muze.web.controller.validator.CheckNickNameValidator;
import hello.muze.web.repository.comment.CommentRepository;
import hello.muze.web.repository.comment.jpa.JpaCommentRepository;
import hello.muze.web.repository.member.MemberRepository;
import hello.muze.web.repository.member.jpa.JpaMemberRepository;
import hello.muze.web.repository.member.jpa.SpringDataJpaMemberRepository;
import hello.muze.web.repository.post.PostRepository;
import hello.muze.web.repository.post.jpa.JpaPostRepository;
import hello.muze.web.service.comment.CommentService;
import hello.muze.web.service.comment.CommentServiceInterface;
import hello.muze.web.service.login.LoginService;
import hello.muze.web.service.login.LoginServiceInterface;
import hello.muze.web.service.post.PostService;
import hello.muze.web.service.post.PostServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

@Configuration
@RequiredArgsConstructor
public class JpaConfig {
    private final SpringDataJpaMemberRepository repository;
    private final EntityManager em;

//    private final CheckMemberIdValidator checkMemberIdValidator;
//
//    private CheckEmailValidator checkEmailValidator;
//
//    private CheckNickNameValidator checkNickNameValidator;


    @Bean
    public LoginServiceInterface loginService() {
        return new LoginService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        return new JpaMemberRepository(repository);
    }
    @Bean
    public PostServiceInterface postService() {
        return new PostService(postRepository());
    }
    @Bean
    public PostRepository postRepository() {
        return new JpaPostRepository(em);
    }
    @Bean
    public CommentServiceInterface commentService() {
        return new CommentService(commentRepository());
    }
    @Bean
    public CommentRepository commentRepository() {
        return new JpaCommentRepository(em);
    }

}
