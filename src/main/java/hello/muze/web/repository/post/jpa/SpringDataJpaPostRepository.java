package hello.muze.web.repository.post.jpa;

import hello.muze.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataJpaPostRepository extends JpaRepository<Post, Long> {

}
