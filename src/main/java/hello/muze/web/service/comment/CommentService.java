package hello.muze.web.service.comment;

import hello.muze.domain.comment.Comment;
import hello.muze.domain.member.Member;
import hello.muze.domain.post.Post;
import hello.muze.web.repository.comment.CommentUpdateDto;
import hello.muze.web.repository.comment.jpa.SpringDataJpaCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService implements CommentServiceInterface {

    private final SpringDataJpaCommentRepository repository;

    @Override
    public Comment save(Comment comment, Member member, Post post) {
        comment.setMember(member);
        comment.setPost(post);
        return repository.save(comment);
    }

    @Override
    public void update(Long commentId, CommentUpdateDto updateParam) {
        Comment comment = repository.findById(commentId).orElseThrow();
        comment.setContents(updateParam.getContents());
    }


}
