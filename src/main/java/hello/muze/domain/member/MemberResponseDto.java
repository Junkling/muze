package hello.muze.domain.member;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MemberResponseDto {

    private String nickName;

    private String profile;

    private List<MemberHeartResponseDto> hearts;
    private List<MemberCommentResponseDto> comments;
    private List<MemberPostResponseDto> posts;

}
