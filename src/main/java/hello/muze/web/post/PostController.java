//package hello.muze.web.post;
//
//import hello.muze.domain.post.Post;
//import hello.muze.repository.post.PostSearchCond;
//import hello.muze.service.post.PostService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import java.util.List;
//
//@Controller
//@RequestMapping("/posts")
//@RequiredArgsConstructor
//public class PostController {
//    private final PostService postService;
//
//    @GetMapping
//    public String posts(@ModelAttribute("postSearch") PostSearchCond postSearch, Model model) {
//        List<Post> posts = postService.findPost(postSearch);
//        model.addAttribute("posts", posts);
//        return "post/posts";
//    }
//    @GetMapping("/{postId}")
//    public String item(@PathVariable long itemId, Model model) {
//        Post post = postService.findById(itemId).get();
//        model.addAttribute("post", post);
//        return "post/post";
//    }
//    @GetMapping("/add")
//    public String addForm() {
//        return "post/addForm";
//    }
//
//    @PostMapping("/add")
//    public String addPost(@ModelAttribute Post post, RedirectAttributes redirectAttributes) {
//        Post savePost = postService.save(post);
//        redirectAttributes.addAttribute("itemId", savePost.getId());
//        redirectAttributes.addAttribute("status", true);
//        return "redirect:/post/posts/{postId}";
//    }
//    @GetMapping("/{itemId}/edit")
//    public String editForm(@PathVariable Long postId, Model model) {
//        Post post = postService.findById(postId).get();
//        model.addAttribute("post", post);
//        return "post/editForm";
//    }
//}
//
