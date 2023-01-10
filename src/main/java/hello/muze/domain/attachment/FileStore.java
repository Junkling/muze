package hello.muze.domain.attachment;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FileStore {
    @Value("${file.dir}/")
    private String fileDir;

    public List<Attachment> storeFiles(List<MultipartFile> multipartFiles) throws IOException {
        List<Attachment> storeFileResult = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                Attachment attachment = storeFile(multipartFile);
                storeFileResult.add(attachment);
            }
        }
        return storeFileResult;
    }

    public String getFullPath(String fileName) {
        return fileDir + fileName;
    }

    public Attachment storeFile(MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) {
            return null;
        }
        String originalFilename = multipartFile.getOriginalFilename();

        String storeFileName = createFileName(originalFilename);
        multipartFile.transferTo(new File(getFullPath(storeFileName)));

        return new Attachment(originalFilename, storeFileName);
    }

    private String createFileName(String originalFilename) {
        String uu = UUID.randomUUID().toString();
        String extracted = extracted(originalFilename);

        String storeFileName = uu + "." + extracted;
        return storeFileName;
    }

    private String extracted(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        String ext = originalFilename.substring(pos + 1);
        return ext;
    }

}
