package hello.muze.web.controller;

import hello.muze.domain.attachment.Attachment;
import hello.muze.domain.attachment.AttachmentAddForm;
import hello.muze.domain.attachment.FileStore;
import hello.muze.domain.comment.Comment;
import hello.muze.domain.heart.Heart;
import hello.muze.domain.member.Member;
import hello.muze.domain.post.CategoryType;
//import hello.muze.domain.post.category.CategoryType;
import hello.muze.domain.post.Post;
import hello.muze.web.repository.attachment.AttachmentRepository;
import hello.muze.web.repository.member.MemberRepository;
import hello.muze.web.repository.post.PostSearchCond;
import hello.muze.web.repository.post.PostUpdateDto;
import hello.muze.web.service.comment.CommentServiceInterface;
import hello.muze.web.service.heart.HeartServiceInterface;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PostController {
    private final PostServiceInterface postService;
    private final CommentServiceInterface commentService;

    private final HeartServiceInterface heartService;

    private final FileStore fileStore;

    private final AttachmentRepository attachmentRepository;


    @ModelAttribute("categoryTypes")

    public List<CategoryType> categoryTypes() {
        List<CategoryType> categoryTypes = new ArrayList<>();
        categoryTypes.add(new CategoryType("???????????????", "???????????????"));
        categoryTypes.add(new CategoryType("???????????????", "???????????????"));
        categoryTypes.add(new CategoryType("????????????", "????????????"));
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
    public String addForm(@ModelAttribute Post post) {
        return "post/addForm";
    }

    @PostMapping("/posts/add")
    public String addPost(@Valid @ModelAttribute Post post, @ModelAttribute AttachmentAddForm attachmentForm, BindingResult bindingResult, RedirectAttributes redirectAttributes, @AuthenticationPrincipal PrincipalDetail principalDetail, @RequestParam MultipartFile file) throws IOException {
        if (bindingResult.hasErrors()) {
            log.info("error={}", bindingResult);
            return "post/addForm";
        }

        MultipartFile attachFile = attachmentForm.getAttachFile();
        List<MultipartFile> imageFiles = attachmentForm.getImageFiles();
        List<Attachment> attachments = fileStore.storeFiles(imageFiles);
        attachmentRepository.saveAll(attachments);
        post.setAttachments(attachments);

        log.info("??????={}", post.getTitle());
        Post savedPost = postService.save(post, principalDetail.getMember());
        redirectAttributes.addAttribute("postId", savedPost.getId());
        redirectAttributes.addAttribute("status", true);

        return "redirect:/post/{postId}";
    }

    @GetMapping("/post/{postId}/edit")
    public String editForm(@PathVariable Long postId, Model model, @AuthenticationPrincipal PrincipalDetail principalDetail, RedirectAttributes redirectAttributes) {
        Post post = postService.findById(postId).get();
        if (post.getMember() == null || !post.getMember().getId().equals(principalDetail.getMember().getId())) {
            log.info("?????????={}", post.getMember().getId());
            log.info("?????????={}", principalDetail.getMember().getId());
            redirectAttributes.addAttribute("authFail", true);
            return "redirect:/post/{postId}";
        }
        model.addAttribute("post", post);
        return "/post/editForm";
    }

    @Transactional
    @PostMapping("/post/{postId}/edit")
    public String edit(@PathVariable Long postId, @ModelAttribute PostUpdateDto updateParam) {
        log.info("?????? ={}", updateParam.getTitle());

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
     * ?????? ??????
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
    public String deleteComment(@Valid @ModelAttribute Comment comment, @AuthenticationPrincipal PrincipalDetail principalDetail, @PathVariable Long commentId, RedirectAttributes redirectAttributes) {
        Comment findComment = commentService.findById(commentId).get();
        Long postId = findComment.getPost().getId();
        log.info("?????? ????????????={}", findComment);

        if (findComment.getMember() == null || !findComment.getMember().equals(principalDetail.getMember())) {
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
     * ????????? ??????
     */
    @Transactional
    @PostMapping("/post/heart/{postId}")
    public String heart(@ModelAttribute Heart heart, @PathVariable Long postId, @AuthenticationPrincipal PrincipalDetail principalDetail) throws IOException {
        log.info("????????? ?????? ?????????={}", principalDetail.getMember().getId());
        log.info("????????? ?????? ?????????={}", postId);
        heartService.save(heart, postId, principalDetail.getMember());
        log.info("????????? ??????={}", heart.getClass());
        return "redirect:/post/{postId}";
    }

    @Transactional
    @DeleteMapping("/post/heart/{postId}")
    public String unHeart(@PathVariable Long postId, @AuthenticationPrincipal PrincipalDetail principalDetail) throws IOException {
        heartService.delete(postId, principalDetail.getMember());
        return "redirect:/post/{postId}";
    }

}

