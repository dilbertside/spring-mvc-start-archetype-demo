package org.spring.webapp.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.spring.webapp.dto.SignupForm;
import org.spring.webapp.entity.User;
import org.spring.webapp.service.UserService;
import org.spring.webapp.web.support.*;

@Controller
class SignupController {

	private static final String SIGNUP_VIEW_NAME = "signup/signup";

	private UserService userService;

	@Autowired
	public SignupController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("signup")
	String signup(Model model, @RequestHeader(value = "X-Requested-With", required = false) String requestedWith) {
		model.addAttribute(new SignupForm());
		if (Ajax.isAjaxRequest(requestedWith)) {
			return SIGNUP_VIEW_NAME.concat(" :: signupForm");
		}
		return SIGNUP_VIEW_NAME;
	}

	@PostMapping("signup")
	String signup(@Valid @ModelAttribute SignupForm signupForm, Errors errors, RedirectAttributes ra) {
		if (errors.hasErrors()) {
			return SIGNUP_VIEW_NAME;
		}
		User account = userService.createUser(signupForm);
		userService.signin(account);
    // see /WEB-INF/i18n/messages.properties and /WEB-INF/views/homeSignedIn.html
    MessageHelper.addSuccessAttribute(ra, "signup.success");
		return "redirect:/";
	}
}
