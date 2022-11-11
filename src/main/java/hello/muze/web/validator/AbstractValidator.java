package hello.muze.web.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public abstract class AbstractValidator<T> implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void validate(Object target, Errors errors) {
        try {
            doValidate((T) target, errors);
        } catch (RuntimeException e) {
            throw e;
        }
    }

    protected abstract void doValidate(final T member, final Errors errors);
}
