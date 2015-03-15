package com.glassify.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Handles requests for the learning module information.
 */
@Controller
public class LearningController {
	
	@RequestMapping("/showLearn")
	public ModelAndView showLearnPage() {
		return new ModelAndView("learn");
	}

}
