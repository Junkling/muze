package hello.muze.domain.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Lob;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostRequestDto {

    private Long memberId;

    private List<MultipartFile> attachments;


    @NotEmpty(message = "제목을 입력해주세요")
    private String title;
    @Lob
    @NotEmpty(message = "내용을 입력해주세요")
    private String contents;

    @ColumnDefault("0")
    private Integer view;//조회수

    @ColumnDefault("0")
    private Integer heartCount;//조회수

    @NotEmpty(message = "카테고리를 선택해주세요")
    private String categoryType;

}
