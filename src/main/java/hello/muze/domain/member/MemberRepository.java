package hello.muze.domain.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.validation.BindingResult;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
@Slf4j
@Repository
public class MemberRepository {
    private static Map<Integer,Member> store = new HashMap<>();
    private static int sequence = 0;


    /**
     * save시 email 확인 프로세스 추가
     */

    public Member save(Member member) {
        LocalDateTime calendar = LocalDateTime.now();
        member.setId(++sequence);
        member.setCreated(calendar);
        log.info("new member={}", member);
        log.info("created={}", member.getCreated());
        store.put(member.getId(), member);
        return member;
    }

    public Member findById(Integer id) {
        return store.get(id);
    }

    public Optional<Member> findByLoginId(String loginId) {
        return findAll().stream().filter(member -> member.getLoginId().equals(loginId)).findFirst();
    }

    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }

}
