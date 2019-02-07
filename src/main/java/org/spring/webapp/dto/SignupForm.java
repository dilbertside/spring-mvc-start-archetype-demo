package org.spring.webapp.dto;

import java.util.Collection;

import javax.validation.Valid;

import org.hibernate.validator.constraints.*;

import org.spring.webapp.entity.Authority;
import org.spring.webapp.entity.User;
import org.spring.webapp.web.support.EmailExists;

@lombok.Data
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
public class SignupForm {

	private static final String NOT_BLANK_MESSAGE = "{notBlank.message}";
	private static final String EMAIL_MESSAGE = "{email.message}";
	private static final String EMAIL_EXISTS_MESSAGE = "{email-exists.message}";

  @NotBlank(message = SignupForm.NOT_BLANK_MESSAGE)
	@Email(message = SignupForm.EMAIL_MESSAGE)
	@EmailExists(message = SignupForm.EMAIL_EXISTS_MESSAGE)
	private String email;

  @NotBlank(message = SignupForm.NOT_BLANK_MESSAGE)
	private String password;

}
