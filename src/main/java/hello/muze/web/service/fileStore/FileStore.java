package hello.muze.web.service.fileStore;

import hello.muze.domain.attachment.Attachment;
import hello.muze.domain.post.Post;
import hello.muze.web.repository.attachment.AttachmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileStore {
    private final AttachmentRepository attachmentRepository;

    @Value("${file.dir}")
    private String fileDir;

    public void storeFiles(List<MultipartFile> multipartFiles, Post post) throws IOException {
        List<Attachment> attachments = new ArrayList<Attachment>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFiles.isEmpty()) {
                Attachment attachment = storeFile(multipartFile);
                if(attachment!=null) {
                    attachment.setPost(post);
                    attachments.add(attachment);
                    Attachment saved = attachmentRepository.save(attachment);
                    log.info("이미지파일 ID ={}", saved.getId());
                }
            }
        }
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
        String extracted = extract(originalFilename);

        String storeFileName = uu + "." + extracted;
        return storeFileName;
    }

    private String extract(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        String ext = originalFilename.substring(pos + 1);
        return ext;
    }

}
