package org.spring.webapp.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutController {

	@ModelAttribute("module")
	public String module() {
		return "about";
	}

	@GetMapping("/about")
	public String about() {
		return "home/about";
	}
}
