package hello.muze.web.validation;

import hello.muze.domain.member.Member;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class MemberValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Member.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Member member = (Member) target;
        /**
         * id, nickName,email 중복 확인 로직 추가해야함
         */

        if(!StringUtils.hasText(member.getLoginId())){
            errors.rejectValue("usersName", "required");
        if(!StringUtils.hasText(member.getPassword())||8>member.getPassword().length()&&member.getPassword().length()<20){
            errors.rejectValue("Password", "range");
        }
        if(!StringUtils.hasText(member.getNickName())||2>member.getPassword().length()){
            errors.rejectValue("nickName", "min");
        }
        if(!StringUtils.hasText(member.getEmail())){
            errors.rejectValue("email", "required");
        }
        if(member.getIntroduction().length()>1000){
            errors.rejectValue("introduction", "overRange");
        }
        }
    }
}
