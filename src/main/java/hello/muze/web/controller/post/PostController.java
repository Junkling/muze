package hello.muze.web.controller.post;

import hello.muze.domain.post.Post;
import hello.muze.web.repository.post.PostRepository;
import hello.muze.web.repository.post.PostSearchCond;
import hello.muze.web.repository.post.PostUpdateDto;
import hello.muze.web.service.post.PostService;
import hello.muze.web.service.post.PostServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
    private final PostServiceInterface postService;

    @GetMapping
    public String post(@ModelAttribute PostSearchCond postSearchCond, Model model) {
        List<Post> posts = postService.findPost(postSearchCond);
        model.addAttribute("posts", posts);
        return "post/free";
    }
    @GetMapping("/{postId}")
    public String post(@PathVariable long postId, Model model) {
        Post post = postService.findById(postId).get();
        model.addAttribute("post", post);
        return "post";
    }
    @GetMapping("/add")
    public String addForm() {
        return "addForm";
    }
    
    @PostMapping("/add")
    public String addpost(@ModelAttribute Post post, RedirectAttributes redirectAttributes) {
        Post savedPost = postService.save(post);
        redirectAttributes.addAttribute("postId", savedPost.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/posts/{postId}";
    }

    @GetMapping("/{postId}/edit")
    public String editForm(@PathVariable Long postId, Model model) {
        Post post = postService.findById(postId).get();
        model.addAttribute("post", post);
        return "editForm";
    }

    @PostMapping("/{postId}/edit")
    public String edit(@PathVariable Long postId, @ModelAttribute PostUpdateDto updateParam) {
        postService.update(postId, updateParam);
        return "redirect:/posts/{postId}";
    }


}
