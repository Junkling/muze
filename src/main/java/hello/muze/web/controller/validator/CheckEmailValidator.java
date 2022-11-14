//package hello.muze.web.controller.validator;
//
//import hello.muze.domain.member.Member;
//import hello.muze.web.repository.member.jpa.SpringDataJpaMemberRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//import org.springframework.validation.Errors;
//
//@RequiredArgsConstructor
//@Component
//public class CheckEmailValidator extends AbstractValidator<Member> {
//
//    private final SpringDataJpaMemberRepository springDataJpaMemberRepository;
//
//    @Override
//    protected void doValidate(Member member, Errors errors) {
//        if (springDataJpaMemberRepository.existsByEmail(member.getEmail())) {
//            errors.rejectValue("Email", "이메일 중복 오류", "사용중인 이메일입니다.");
//        }
//    }
//}
