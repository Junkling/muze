package hello.muze.web.controller;

import hello.muze.domain.comment.Comment;
import hello.muze.domain.member.Member;
import hello.muze.domain.post.CategoryType;
//import hello.muze.domain.post.category.CategoryType;
import hello.muze.domain.post.Post;
import hello.muze.web.repository.post.PostSearchCond;
import hello.muze.web.repository.post.PostUpdateDto;
import hello.muze.web.service.comment.CommentServiceInterface;
import hello.muze.web.service.login.PrincipalDetail;
import hello.muze.web.service.post.PostServiceInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PostController {
    private final PostServiceInterface postService;
    private final CommentServiceInterface commentService;

    @ModelAttribute("categoryTypes")
    public List<CategoryType> categoryTypes() {
        List<CategoryType> categoryTypes = new ArrayList<>();
        categoryTypes.add(new CategoryType("자유게시판", "자유게시판"));
        categoryTypes.add(new CategoryType("공연게시판", "공연게시판"));
        categoryTypes.add(new CategoryType("중고거래", "중고거래"));
        return categoryTypes;
    }

    @GetMapping("/posts")
    public String post(@ModelAttribute("postSearch") PostSearchCond postSearch, Model model, @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        List<Post> posts = postService.findPost(postSearch);
        model.addAttribute("posts", posts);
        return "post/posts";
    }

    @GetMapping("/post/{postId}")
    public String post(@PathVariable long postId, Model model, Comment comment) {
        Post post = postService.findById(postId).get();
        model.addAttribute("post", post);
        model.addAttribute("comment", comment);
        return "post/post";
    }

    @GetMapping("/posts/add")
    public String addForm(Model model) {
        model.addAttribute("post", new Post());
        return "post/addForm";
    }

    @PostMapping("/posts/add")
    public String addPost(@Valid @ModelAttribute Post post, BindingResult bindingResult, RedirectAttributes redirectAttributes, @AuthenticationPrincipal PrincipalDetail principalDetail) {
        if (bindingResult.hasErrors()) {
            log.info("error={}", bindingResult);
            return "post/addForm";
        }
        Post savedPost = postService.save(post, principalDetail.getMember());
        redirectAttributes.addAttribute("postId", savedPost.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/post/{postId}";
    }

    @GetMapping("/post/{postId}/edit")
    public String editForm(@PathVariable Long postId, Model model, @AuthenticationPrincipal PrincipalDetail principalDetail) {
        Post post = postService.findById(postId).get();
        model.addAttribute("post", post);
        if (post.getMember() == null || !post.getMember().equals(principalDetail.getMember())) {
            return "post/editFail";
        }
        return "/post/editForm";
    }

    @Transactional
    @PostMapping("/post/{postId}/edit")
    public String edit(@PathVariable Long postId, @ModelAttribute PostUpdateDto updateParam) {
        log.info("제목 ={}", updateParam.getTitle());

        postService.update(postId, updateParam);
        return "redirect:/post/{postId}";
    }

    @Transactional
    @GetMapping("/post/delete/{postId}")
    public String delete(@PathVariable Long postId) {
        postService.delete(postId);
        return "post/delete";
    }


    @Transactional
    @PostMapping("/post/{postId}/comment")
    public String comment(@Valid @ModelAttribute Comment comment, BindingResult bindingResult, @AuthenticationPrincipal PrincipalDetail principalDetail, @PathVariable Long postId, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            log.info("error={}", bindingResult);
            redirectAttributes.addAttribute("commentFail", true);
            return "redirect:/post/{postId}";
        }
        Post post = postService.findById(postId).orElseThrow();
        Member member = principalDetail.getMember();
        commentService.save(comment, member, post);
        log.info("postId={}", postId);
        log.info("comment={}", comment.getContents());
        return "redirect:/post/{postId}";
    }

    @Transactional
    @GetMapping("/comment/delete/{commentId}")
    public String deleteComment(@Valid @ModelAttribute Comment comment, @AuthenticationPrincipal PrincipalDetail principalDetail, @PathVariable Long commentId, RedirectAttributes redirectAttributes) {
        Comment findComment = commentService.findById(commentId).get();
        Long postId = findComment.getPost().getId();
        log.info("댓글 삭제요청={}", findComment);

        if (findComment.getMember() == null || !findComment.getMember().equals(principalDetail.getMember())) {
            redirectAttributes.addAttribute("commentDeleteFail", true);
            redirectAttributes.addAttribute("commentId", findComment.getId());
            return "redirect:/post/"+postId;
        }
        commentService.delete(commentId);
        redirectAttributes.addAttribute("commentDelete", true);
        redirectAttributes.addAttribute("commentId", findComment.getId());
        return "redirect:/post/"+postId;
    }
}

