package hello.muze.web.post;

import hello.muze.domain.post.Post;
import hello.muze.domain.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
    private final PostRepository postRepository;

    @GetMapping("/free")
    public String postFree(Model model) {
        return ("/post/free");
    }
}
