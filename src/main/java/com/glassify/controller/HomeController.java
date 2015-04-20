package com.glassify.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Handles requests for the static application pages.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@RequestMapping("/about")
	public ModelAndView showAboutPage() {
		logger.debug("RecognizeMe - Showing about page");
		return new ModelAndView("about");
	}
	
	@RequestMapping("/contact")
	public ModelAndView showContactPage() {
		return new ModelAndView("contact");
	}
}
