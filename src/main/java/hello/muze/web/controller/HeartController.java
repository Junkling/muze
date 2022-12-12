//package hello.muze.web.controller;
//
//import hello.muze.domain.heart.Heart;
//import hello.muze.web.service.heart.HeartServiceInterface;
//import hello.muze.web.service.login.PrincipalDetail;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.stereotype.Controller;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.bind.annotation.*;
//
//import java.io.IOException;
//
//@Slf4j
//@Controller
//@RequiredArgsConstructor
//@RequestMapping("/post/heart/{postId}")
//public class HeartController {
//    private final HeartServiceInterface heartService;
//
//
//    @Transactional
//    @PostMapping
//    public String heart(@ModelAttribute Heart heart, @PathVariable Long postId, @AuthenticationPrincipal PrincipalDetail principalDetail) throws IOException {
//        log.info("좋아요 요청 아이디={}",principalDetail.getMember().getId());
//        log.info("좋아요 요청 게시물={}",postId);
//        heartService.save(heart, postId, principalDetail.getMember());
//        log.info("좋아요 저장={}",heart.getClass());
//        return "redirect:/post/{postId}";
//    }
//    @Transactional
//    @DeleteMapping
//    public String unHeart(@ModelAttribute Heart heart,@PathVariable Long postId, @AuthenticationPrincipal PrincipalDetail principalDetail) throws IOException{
//        heartService.delete(heart,postId, principalDetail.getMember());
//        return "redirect:/post/{postId}";
//    }
//}
