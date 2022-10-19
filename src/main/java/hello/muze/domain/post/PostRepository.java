package hello.muze.domain.post;

import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public interface PostRepository {
    Post save(Post post);

    void update(Long postId, PostUpdateDto updateParam);

}
