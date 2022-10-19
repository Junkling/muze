package hello.muze.web.post.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class PostUpdateForm {
    @NotNull
    private Long id;
    @NotBlank
    public String title;

    @NotBlank
    public String contents;


}
