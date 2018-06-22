package com.codingdojo.events.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.codingdojo.events.models.User;

@Component
public class LogAndRegValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.equals(clazz);
	}
	
	@Override
	public void validate(Object target, Errors errors) {
		User user = (User) target;
		
		if(!user.getPassconf().equals(user.getPassword())) {
			errors.rejectValue("passconf", "Match");
		}
	}
}
