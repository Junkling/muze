package hello.muze.web.repository.comment.jpa;

import hello.muze.domain.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataJpaCommentRepository extends JpaRepository<Comment, Long> {

}
