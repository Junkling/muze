package hello.muze.domain.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberHeartResponseDto {
    private Long heartId;
    private Long postId;
    private String postTitle;
}
