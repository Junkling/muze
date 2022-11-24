package hello.muze.web.repository.post.jpa;

import hello.muze.domain.post.Post;
import hello.muze.web.repository.post.PostSearchCond;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.*;

public interface SpringDataJpaPostRepository extends JpaRepository<Post, Long> {

}
