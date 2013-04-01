package eu.confess.springframework.data.rest.domain;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * @author Jon Brisbin
 */
public class EmailValidator implements Validator {

	@Override public boolean supports(Class<?> aClass) {
		return Customer.class.isAssignableFrom(aClass);
	}

	@Override public void validate(Object o, Errors errors) {
		Customer c = (Customer)o;

		ValidationUtils.rejectIfEmpty(errors, "email", "not.blank");

		if(null != c.getEmail() && !c.getEmail().toString().contains("@")) {
			errors.rejectValue("email", "invalid.format");
		}
	}

}
