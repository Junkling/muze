package hello.muze.web.validator;

import hello.muze.domain.member.Member;
import hello.muze.web.repository.member.jpa.SpringDataJpaMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@RequiredArgsConstructor
@Component
public class CheckMemberIdValidator extends AbstractValidator<Member> {

    private final SpringDataJpaMemberRepository springDataJpaMemberRepository;

    @Override
    protected void doValidate(Member member, Errors errors) {
        if (springDataJpaMemberRepository.existsByMemberId(member.getLoginId())) {
            errors.rejectValue("loginId", "아이디 중복 오류", "사용중인 아이디입니다.");
        }
    }
}
