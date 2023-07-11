package hello.muze.web.appService;

import hello.muze.domain.member.Member;
import hello.muze.domain.member.MemberResponseDto;
import hello.muze.domain.member.MemberRequestDto;
import hello.muze.web.repository.member.MemberRepository;
import hello.muze.web.repository.member.MemberUpdateDto;
import hello.muze.web.service.login.PwChangeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;

@Service
@RequiredArgsConstructor
public class MemberAppService {
    private final MemberRepository memberRepository;


    public void save(MemberRequestDto dto, String encoding) {
        Member member = new Member();
        member.setRole("ROLE_USER");
        member.setPassword(encoding);
        member.setEmail(dto.getEmail());
        member.setLoginId(dto.getLoginId());
        member.setNickName(dto.getNickName());
        member.setProfile(dto.getProfile());
        memberRepository.save(member);
    }

//    public void validate(MemberRequestDto dto, BindingResult bindingResult) {
//        memberValidator.validate(dto, bindingResult);
//    }

    public MemberResponseDto findById(Integer memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow();
        MemberResponseDto dto = MemberResponseDto.builder()
                .nickName(member.getNickName())
                .profile(member.getProfile())
                .build();
        return dto;
    }

    public void update(Integer memberId, MemberUpdateDto dto) {
        memberRepository.update(memberId, dto);
    }

    public void validChangePw(PwChangeDto pwChangeDto,boolean matches,boolean samePw, Errors errors) {
        if (pwChangeDto.getOriginalPW() == null || matches != true) {
            errors.rejectValue("password", "unmatched", "입력하신 비밀번호가 기존 비밀번호와 다릅니다.");
        }
        if (samePw) {
            errors.rejectValue("password", "same", "변경하신 비밀번호가 기존 비밀번호와 같습니다.");
        }
        if (!pwChangeDto.getChangedPW().equals(pwChangeDto.getCheckChange())) {
            errors.rejectValue("password", "checkFail", "변경하실 비밀번호와 비밀번호 확인이 다르게 입력되었습니다.");
        }

//        비밀번호 변경 코드

    }

    public void changPw(Integer memberId,String encoding) {
        Member member = memberRepository.findById(memberId).orElseThrow();
        memberRepository.changePW(member, encoding);
    }

    public String delete(Integer memberId) {
        try {
            memberRepository.delete(memberId);
        } catch (EmptyResultDataAccessException e) {
            return "삭제 실패하였습니다(해당 id는 존재하지 않습니다.)";
        }
        return "삭제 완료" + memberId;
    }

}
