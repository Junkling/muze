package hello.muze.web.controller;

import hello.muze.domain.member.Member;
import hello.muze.domain.post.Post;
import hello.muze.web.argumentresolber.Login;
import hello.muze.web.repository.member.MemberRepository;
import hello.muze.web.service.login.PrincipalDetail;
import hello.muze.web.service.post.PostServiceInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {
    private final MemberRepository memberRepository;
    private final PostServiceInterface postService;

    @GetMapping("/")
    public String loginHome(@Login Member member, Model model, @AuthenticationPrincipal PrincipalDetail principalDetail) {
        List<Post> topPosts = postService.top5Post();
        model.addAttribute("topPosts", topPosts);
        if (principalDetail != null) {
            Member principalDetailMember = memberRepository.findById(principalDetail.getMember().getId()).orElseThrow();
            model.addAttribute("member", member);
            model.addAttribute("principalDetailMember", principalDetailMember);
            return "loginHome";
        }
        return "loginHome";
    }
}
