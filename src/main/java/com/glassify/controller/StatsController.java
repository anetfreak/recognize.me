package com.glassify.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.glassify.facade.AuditTrailFacade;

/**
 * Handles requests for the statistics page.
 */
@Controller
public class StatsController {
	
	@Autowired
	private AuditTrailFacade auditTrailfacade;
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@RequestMapping("/stats")
	public ModelAndView showContactPage() {
		return new ModelAndView("stats");
	}
	
}
