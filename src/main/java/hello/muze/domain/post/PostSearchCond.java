package hello.muze.domain.post;

import lombok.Data;

@Data
public class PostSearchCond {
    private Long id;

    private Integer categoryId;
    private String title;
    private String memberName;
    private String contents;

    public PostSearchCond(Integer categoryId, String title, String memberName, String contents) {
        this.categoryId = categoryId;
        this.title = title;
        this.memberName = memberName;
        this.contents = contents;
    }
    public PostSearchCond(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public PostSearchCond() {

    }


}
