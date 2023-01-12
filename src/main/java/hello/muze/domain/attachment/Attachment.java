package hello.muze.domain.attachment;

import hello.muze.domain.post.Post;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Attachment {
    public Attachment() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String originName;
    private String storeName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    public Attachment(String originName, String storeName) {
        this.originName = originName;
        this.storeName = storeName;
    }
}
