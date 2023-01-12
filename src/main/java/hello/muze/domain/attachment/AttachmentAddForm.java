package hello.muze.domain.attachment;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

@Data
public class AttachmentAddForm {

    private List<MultipartFile> imageFiles;

}
