package hello.muze.web.repository.post;

//import hello.muze.domain.post.category.CategoryType;
import hello.muze.domain.post.CategoryType;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
public class PostSearchCond {
    private Long id;

    @NotNull
    private String title;
    private String contents;
    private String categoryType;

    public PostSearchCond(String title, String contents, String categoryType) {
        this.title = title;
        this.contents = contents;
        this.categoryType = categoryType;
    }
    public PostSearchCond() {

    }


}
