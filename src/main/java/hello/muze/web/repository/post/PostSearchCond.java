package hello.muze.web.repository.post;

import lombok.Data;

@Data
public class PostSearchCond {
    private Long id;

    private Integer categoryId;
    private String title;
    private String contents;

    public PostSearchCond(Integer categoryId, String title, String memberName, String contents) {
        this.categoryId = categoryId;
        this.title = title;
        this.contents = contents;
    }
    public PostSearchCond() {

    }


}
