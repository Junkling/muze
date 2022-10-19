package hello.muze.web.post.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
public class PostSaveForm {
    @NotBlank
    public String title;

    @NotBlank
    public String contents;


}
