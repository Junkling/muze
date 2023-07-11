package hello.muze.domain.member;

import hello.muze.domain.comment.Comment;
import hello.muze.domain.heart.Heart;
import hello.muze.domain.post.Post;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MemberResponseDto {

    private String nickName;

    private String profile;

    private List<Heart> hearts;
    private List<Comment> comments;
    private List<Post> posts;

}
