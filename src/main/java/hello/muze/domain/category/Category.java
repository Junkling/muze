package hello.muze.domain.category;

import lombok.Data;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class Category {
    @Id
    private Integer id;
    @NotNull
    private String categoryName;
    private LocalDateTime created;
    private LocalDateTime updated;
}
