package hello.muze.web.controller;

import hello.muze.domain.attachment.Attachment;
import hello.muze.domain.attachment.AttachmentAddForm;
import hello.muze.web.service.fileStore.FileStore;
import hello.muze.domain.comment.Comment;
import hello.muze.domain.heart.Heart;
import hello.muze.domain.member.Member;
import hello.muze.domain.post.CategoryType;
import hello.muze.domain.post.Post;
import hello.muze.web.repository.attachment.AttachmentRepository;
import hello.muze.web.repository.post.PostSearchCond;
import hello.muze.web.repository.post.PostUpdateDto;
import hello.muze.web.service.comment.CommentServiceInterface;
import hello.muze.web.service.heart.HeartServiceInterface;
import hello.muze.web.service.login.PrincipalDetail;
import hello.muze.web.service.post.PostServiceInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PostController {
    private final PostServiceInterface postService;
    private final CommentServiceInterface commentService;

    private final HeartServiceInterface heartService;

    private final FileStore fileStore;


    @ModelAttribute("categoryTypes")

    public List<CategoryType> categoryTypes() {
        List<CategoryType> categoryTypes = new ArrayList<>();
        categoryTypes.add(new CategoryType("자유게시판", "자유게시판"));
        categoryTypes.add(new CategoryType("공연게시판", "공연게시판"));
        categoryTypes.add(new CategoryType("중고거래", "중고거래"));
        return categoryTypes;
    }

    @GetMapping("/posts/list")
    public String allList(@ModelAttribute("postSearch") PostSearchCond postSearch, Model model, @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        List<Post> posts = postService.findPost(postSearch);
        model.addAttribute("posts", posts);
        return "post/posts";
    }

    @GetMapping("/posts/list/{categoryType}")
    public String freeList(@ModelAttribute("postSearch") PostSearchCond postSearch, Model model, @PathVariable String categoryType, @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        postSearch.setCategoryType(categoryType);
        List<Post> posts = postService.findPost(postSearch);
        model.addAttribute("posts", posts);
        return "post/posts";
    }

    @GetMapping("/post/{postId}")
    public String post(@PathVariable Long postId, Model model, Comment comment, @AuthenticationPrincipal PrincipalDetail principalDetail) throws IOException {
        Post post = postService.findById(postId).get();
        model.addAttribute("post", post);
        model.addAttribute("comment", comment);

        if (principalDetail != null) {
            Member member = principalDetail.getMember();
            Integer heartCheck = heartService.heartCheck(postId, member);
            log.info("Check={}", heartCheck);
            model.addAttribute("heartCheck", heartCheck);
        }
        return "post/post";
    }

    @GetMapping("/posts/add")
    public String addForm(@ModelAttribute Post post, @ModelAttribute AttachmentAddForm attachmentForm) {
        return "post/addForm";
    }

    @PostMapping("/posts/add")
        public String addPost(@Valid @ModelAttribute Post post, @ModelAttribute AttachmentAddForm attachmentForm, BindingResult bindingResult, RedirectAttributes redirectAttributes, @AuthenticationPrincipal PrincipalDetail principalDetail) throws IOException {
        if (bindingResult.hasErrors()) {
            log.info("error={}", bindingResult);
            return "post/addForm";
        }

        log.info("제목={}", post.getTitle());
        Post savedPost = postService.save(post, principalDetail.getMember());
        redirectAttributes.addAttribute("postId", savedPost.getId());
        redirectAttributes.addAttribute("status", true);

            List<MultipartFile> imageFiles = attachmentForm.getImageFiles();
            fileStore.storeFiles(imageFiles, post);

        return "redirect:/post/{postId}";
    }

    @GetMapping("/post/{postId}/edit")
    public String editForm(@PathVariable Long postId, Model model, @AuthenticationPrincipal PrincipalDetail principalDetail, RedirectAttributes redirectAttributes) {
        Post post = postService.findById(postId).orElseThrow();
        if (post.getMember() == null || !post.getMember().getId().equals(principalDetail.getMember().getId())) {
            log.info("게시자={}", post.getMember().getId());
            log.info("접속자={}", principalDetail.getMember().getId());
            redirectAttributes.addAttribute("authFail", true);
            return "redirect:/post/{postId}";
        }
        model.addAttribute("post", post);
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
    @GetMapping("/post/{postId}/delete")
    public String delete(@PathVariable Long postId) {
        postService.delete(postId);
        return "post/delete";
    }

    /**
     * 댓글 기능
     */
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
    public String deleteComment(@AuthenticationPrincipal PrincipalDetail principalDetail, @PathVariable Long commentId, RedirectAttributes redirectAttributes) {
        Comment findComment = commentService.findById(commentId).get();
        Long postId = findComment.getPost().getId();
        log.info("댓글 삭제요청={}", findComment.getId());

        if (findComment.getMember() == null || !findComment.getMember().getId().equals(principalDetail.getMember().getId())) {
            redirectAttributes.addAttribute("commentDeleteFail", true);
            redirectAttributes.addAttribute("commentId", findComment.getId());
            return "redirect:/post/" + postId;
        }
        commentService.delete(commentId);
        redirectAttributes.addAttribute("commentDelete", true);
        redirectAttributes.addAttribute("commentId", findComment.getId());
        return "redirect:/post/" + postId;
    }

    /**
     * 좋아요 기능
     */
    @Transactional
    @PostMapping("/post/heart/{postId}")
    public String heart(@ModelAttribute Heart heart, @PathVariable Long postId, @AuthenticationPrincipal PrincipalDetail principalDetail) throws IOException {
        log.info("좋아요 요청 아이디={}", principalDetail.getMember().getId());
        log.info("좋아요 요청 게시물={}", postId);
        heartService.save(heart, postId, principalDetail.getMember());
        log.info("좋아요 저장={}", heart.getClass());
        return "redirect:/post/{postId}";
    }

    @Transactional
    @DeleteMapping("/post/heart/{postId}")
    public String unHeart(@PathVariable Long postId, @AuthenticationPrincipal PrincipalDetail principalDetail) throws IOException {
        heartService.delete(postId, principalDetail.getMember());
        return "redirect:/post/{postId}";
    }

    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:" + fileStore.getFullPath(filename));
    }
}

