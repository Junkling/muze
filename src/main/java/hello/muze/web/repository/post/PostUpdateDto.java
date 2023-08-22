package hello.muze.web.repository.post;

//import hello.muze.domain.post.category.CategoryType;

import lombok.Data;

@Data
public class PostUpdateDto {
    private String title;
    private String contents;

//    @Enumerated(EnumType.STRING)
    private String categoryType;

    public PostUpdateDto(String title, String contents, String categoryType) {

        this.title = title;
        this.contents = contents;
        this.categoryType = categoryType;
    }
}
