package hello.muze.web.service.comment;

import hello.muze.domain.comment.Comment;
import hello.muze.domain.comment.CommentRequestDto;
import hello.muze.domain.member.Member;
import hello.muze.domain.post.Post;
import hello.muze.web.repository.comment.CommentSearchCond;
import hello.muze.web.repository.comment.CommentUpdateDto;
import hello.muze.web.repository.post.PostSearchCond;

import java.util.List;
import java.util.Optional;

public interface CommentServiceInterface {
    Comment save(CommentRequestDto commentRequestDto, Member member, Post post);

    void update(Long commentId, CommentUpdateDto updateParam);

    void delete(Long commentId);

    Optional<Comment> findById(Long id);

}
