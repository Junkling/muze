package hello.muze.domain.attachment;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class AttachmentAddForm {

    private List<MultipartFile> imageFiles;

}
