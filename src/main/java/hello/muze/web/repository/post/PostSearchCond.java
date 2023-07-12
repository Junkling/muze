package hello.muze.web.repository.post;

//import hello.muze.domain.post.category.CategoryType;
import hello.muze.domain.post.CategoryType;
import lombok.Data;

import javax.persistence.*;

@Data
public class PostSearchCond {
    private Long id;

//    private Integer categoryId;
    private String title;
    private String contents;
    private String categoryType;

//    @Enumerated(EnumType.STRING)
//    private CategoryType categoryName;

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "memberId")
//    private Member member;

    public PostSearchCond(/**CategoryType categoryName,**/ String title, String contents, String categoryType) {
//        this.member = member;
//        this.categoryName = categoryName;
        this.title = title;
        this.contents = contents;
        this.categoryType = categoryType;
    }
    public PostSearchCond() {

    }


}
