package hello.muze.domain.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

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
        member.setId(++sequence);
        log.info("new member={}", member);
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
