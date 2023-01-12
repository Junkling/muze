package hello.muze.web.repository.attachment;

import hello.muze.domain.attachment.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {

}
