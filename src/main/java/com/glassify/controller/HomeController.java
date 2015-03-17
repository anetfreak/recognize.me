package com.glassify.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Handles requests for the static application pages.
 */
@Controller
public class HomeController {
	
	@RequestMapping("/about")
	public ModelAndView showAboutPage() {
		return new ModelAndView("about");
	}
	
	@RequestMapping("/contact")
	public ModelAndView showContactPage() {
		return new ModelAndView("contact");
	}
}
