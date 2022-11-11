package hello.muze.web.validator;

import hello.muze.domain.member.Member;
import hello.muze.web.repository.member.jpa.SpringDataJpaMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@RequiredArgsConstructor
@Component
public class CheckNickNameValidator extends AbstractValidator<Member> {

    private final SpringDataJpaMemberRepository springDataJpaMemberRepository;

    @Override
    protected void doValidate(Member member, Errors errors) {
        if (springDataJpaMemberRepository.existsByNickname(member.getNickName())) {
            errors.rejectValue("nickName", "닉네임 중복 오류", "사용중인 닉네임입니다.");
        }
    }
}
