package hello.muze.web.appService;

import hello.muze.domain.attachment.Attachment;
import hello.muze.domain.attachment.AttachmentAddForm;
import hello.muze.domain.comment.Comment;
import hello.muze.domain.comment.CommentRequestDto;
import hello.muze.domain.comment.CommentResponseDto;
import hello.muze.domain.heart.Heart;
import hello.muze.domain.member.Member;
import hello.muze.domain.post.Post;
import hello.muze.domain.post.PostRequestDto;
import hello.muze.domain.post.PostResponseDto;
import hello.muze.web.repository.member.MemberRepository;
import hello.muze.web.repository.post.PostSearchCond;
import hello.muze.web.repository.post.PostUpdateDto;
import hello.muze.web.service.comment.CommentServiceInterface;
import hello.muze.web.service.fileStore.FileStore;
import hello.muze.web.service.heart.HeartServiceInterface;
import hello.muze.web.service.post.PostServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostAppService {
    private final MemberRepository memberRepository;
    private final PostServiceInterface postService;
    private final CommentServiceInterface commentService;
    private final HeartServiceInterface heartService;
    private final FileStore fileStore;

    // 5줄이 적당하걷느
    static void listMapper(Post p, PostResponseDto dto) {
        List<Comment> comment = p.getComment();
        List<CommentResponseDto> commentList = new ArrayList<>();
        if (comment != null) { // size = 0
            for (Comment c : comment) {
                createCommnetResponse(commentList, c);
            }
        }

        List<Heart> hearts = p.getHearts();
        List<Long> heartList = new ArrayList<>();
        if (hearts != null) {
            for (Heart h : hearts) {
                heartList.add(h.getId());
            }
        }
        dto.setHeartsId(heartList);

        List<Attachment> attachments = p.getAttachments();
        List<String> attachmentList = new ArrayList<>();
        if (attachments != null) {
            for (Attachment a : attachments) {
                attachmentList.add(a.getStoreName());
            }
        }
        dto.setAttachments(attachmentList);
    }

    private static void createCommnetResponse(List<CommentResponseDto> commentList, Comment c) {
        CommentResponseDto commentResponseDto = CommentResponseDto.builder()
                .id(c.getId())
                .memberNickName(c.getMember().getNickName())
                .memberId(c.getMember().getId())
                .postId(c.getPost().getId())
                .contents(c.getContents())
                .build();
        commentList.add(commentResponseDto);
    }

    //
    static PostResponseDto entityToDto(Post p) {
        PostResponseDto dto = PostResponseDto.builder()
                .id(p.getId())
                .title(p.getTitle())
                .memberNickName(p.getMember().getNickName())
                .contents(p.getContents())
                .heartCount(p.getHeartCount())
                .view(p.getView())
                .categoryType(p.getCategoryType())
                .memberId(p.getMember().getId())
                .build();
        listMapper(p, dto);
        return dto;
    }

    public List<PostResponseDto> allList(PostSearchCond postSearchCond) {
        List<Post> post = postService.findPost(postSearchCond);
        List<PostResponseDto> list = new ArrayList<>();

        for (Post p : post) {
            PostResponseDto dto = entityToDto(p);
            list.add(dto);
        }
        return list;
    }

    public PostResponseDto findById(Long postId) {
        Post post = postService.findById(postId).orElseThrow();
        PostResponseDto dto = entityToDto(post);
        return dto;

    }

    public Integer heartCheck(Long postId, Integer memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow();
        Integer heartCheck = heartService.heartCheck(postId, member);
        return heartCheck;
    }

    public PostResponseDto savePost(PostRequestDto postRequestDto, AttachmentAddForm attachmentAddForm, Integer memberId) throws IOException {
        Post post = Post.builder()
                .title(postRequestDto.getTitle())
                .contents(postRequestDto.getContents())
                .heartCount(0)
                .view(0)
                .categoryType(postRequestDto.getCategoryType())
                .build();
        Post save = postService.save(post, memberRepository.findById(memberId).orElseThrow());
        List<MultipartFile> imageFiles = attachmentAddForm.getImageFiles();
        fileStore.storeFiles(imageFiles, save);


        PostResponseDto dto = entityToDto(save);
        return dto;
    }

    public void updatePost(Long postId, PostUpdateDto dto) {
        postService.update(postId, dto);
    }

    public void deletePost(Long postId) {
        postService.delete(postId);
    }

    public void saveComment(Long postId, Integer memberId, CommentRequestDto dto) {
        Post post = postService.findById(postId).orElseThrow();
        Member member = memberRepository.findById(memberId).orElseThrow();
        commentService.save(dto, member, post);
    }

    public boolean deleteCommentAuth(Long commentId, Integer memberId) {
        Comment comment = commentService.findById(commentId).orElseThrow();
        if (comment.getMember() == null || comment.getMember().getId() != memberId)
            return false;
        commentService.delete(commentId);
        return true;
    }

    public Long commentPost(Long commentId) {
        Comment comment = commentService.findById(commentId).orElseThrow();
        return comment.getPost().getId();
    }

    public void saveHeart(Long postId, Integer memberId) throws IOException {
        Member member = memberRepository.findById(memberId).orElseThrow();
        Post findPost = postService.findById(postId).orElseThrow();
        Heart heart = new Heart();
        heart.setPost(findPost);
        heart.setMember(member);

        heartService.save(heart, postId, member);
    }

    public void deleteHeart(Long postId, Integer memberId) throws IOException {
        Member member = memberRepository.findById(memberId).orElseThrow();
        heartService.delete(postId, member);
    }

    public String getFileFullPath(String fileName) {
        String fullPath = fileStore.getFullPath(fileName);
        return fullPath;
    }
}
