package hello.muze.domain.heart;

import lombok.Data;

@Data
public class HeartRequestDto {
    private Integer memberId;
    private Long postId;
}
