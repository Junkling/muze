package hello.muze.web.service.comment;

import hello.muze.domain.comment.Comment;
import hello.muze.domain.comment.CommentRequestDto;
import hello.muze.domain.member.Member;
import hello.muze.domain.post.Post;
import hello.muze.web.repository.comment.CommentUpdateDto;
import hello.muze.web.repository.comment.jpa.SpringDataJpaCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService implements CommentServiceInterface {

    private final SpringDataJpaCommentRepository repository;

    @Override
    public Comment save(CommentRequestDto dto, Member member, Post post) {
        Comment comment = new Comment();
        comment.setMember(member);
        comment.setPost(post);
        comment.setContents(dto.getContents());

        return repository.save(comment);
    }

    @Override
    public void update(Long commentId, CommentUpdateDto updateParam) {
        Comment comment = repository.findById(commentId).orElseThrow();
        comment.setContents(updateParam.getContents());
    }
    @Override
    public void delete(Long commentId) {
        repository.delete(
                repository.findById(commentId).orElseThrow(()-> new EntityNotFoundException("댓글이 존재하지 않습니다."))
        );
    }

    @Override
    public Optional<Comment> findById(Long id) {
        return repository.findById(id);
    }

}
