package com.glassify.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Handles requests for the static application pages.
 */
@Controller
public class AuthenticationController {
	
	@RequestMapping("/login")
	public ModelAndView showAboutPage() {
		return new ModelAndView("login");
	}	
}
