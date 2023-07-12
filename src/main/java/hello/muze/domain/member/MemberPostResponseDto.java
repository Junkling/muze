package hello.muze.domain.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberPostResponseDto {
    private String categoryType;
    private Long postId;
    private String title;
}
