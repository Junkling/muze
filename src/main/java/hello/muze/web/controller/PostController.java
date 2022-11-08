package hello.muze.web.controller;

import hello.muze.domain.post.Post;
import hello.muze.web.repository.post.PostRepository;
import hello.muze.web.repository.post.PostSearchCond;
import hello.muze.web.repository.post.PostUpdateDto;
import hello.muze.web.service.post.PostService;
import hello.muze.web.service.post.PostServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PostController {
    private final PostServiceInterface postService;

    @GetMapping("/posts")
    public String post(@ModelAttribute("postSearch") PostSearchCond postSearch, Model model) {
        List<Post> posts = postService.findPost(postSearch);
        model.addAttribute("posts", posts);
        return "/post/posts";
    }
    @GetMapping("/post/{postId}")
    public String post(@PathVariable long postId, Model model) {
        Post post = postService.findById(postId).get();
        model.addAttribute("post", post);
        return "/post/post";
    }
    @GetMapping("/posts/add")
    public String addForm(Model model) {
        return "/post/addForm";
    }

    @PostMapping("/posts/add")
    public String addPost(@ModelAttribute Post post, RedirectAttributes redirectAttributes) {
        Post savedPost = postService.save(post);
        redirectAttributes.addAttribute("postId", savedPost.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/post/{postId}";
    }

    @GetMapping("/post/{postId}/edit")
    public String editForm(@PathVariable Long postId, Model model) {
        Post post = postService.findById(postId).get();
        model.addAttribute("post", post);
        return "/post/editForm";
    }

    @Transactional
    @PostMapping("/post/{postId}/edit")
    public String edit(@PathVariable Long postId, @ModelAttribute PostUpdateDto updateParam) {
        postService.update(postId, updateParam);
        return "redirect:/post/posts/{postId}";
    }


}
