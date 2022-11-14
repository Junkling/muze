package hello.muze.domain.post;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategoryType {
    private String code;
    private String displayName;

}
