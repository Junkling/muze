package hello.muze.web.repository.comment;

import hello.muze.domain.comment.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {
    Comment save(Comment comment);

    void update(Long commentId, CommentUpdateDto commentUpdateDto);

    List<Comment> findByPostId(CommentSearchCond cond);

    Optional<Comment> findById(Long commentId);

}
