package hello.muze.web.controller;

import hello.muze.domain.member.Member;
import hello.muze.web.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class MemberValidator implements Validator {

    private final MemberRepository memberRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return Member.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Member member = (Member) target;
        if (memberRepository.findByMember(member.getLoginId()).orElse(null)!=null) {
            errors.rejectValue("loginId","idUnique","ID 중복입니다.");
        }
        if (memberRepository.findByEmail(member.getEmail()).orElse(null)!=null) {
            errors.rejectValue("email","emailUnique","이메일 중복입니다.");
        }
        if (memberRepository.findByNickName(member.getNickName()).orElse(null)!=null) {
            errors.rejectValue("nickName","nickNameUnique","닉네임 중복입니다.");
        }
        if (!member.getPassword().equals(member.getPasswordConfirm())) {
            errors.rejectValue("password","confirm","비밀번호가 다릅니다.");
        }

    }
}
