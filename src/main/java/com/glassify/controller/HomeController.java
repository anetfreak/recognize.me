package com.glassify.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.glassify.facade.AuditTrailFacade;
import com.glassify.util.AuditTrail;

/**
 * Handles requests for the static application pages.
 */
@Controller
public class HomeController {
	@Autowired
	private AuditTrailFacade auditTrailfacade;
	
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
	
	@RequestMapping("/audit")
	public ModelAndView showAuditPage() {
		List<AuditTrail> auditTrails = auditTrailfacade.getAllAuditTrail();
		ModelAndView modelandview = new ModelAndView("audit-trails");
		modelandview.addObject("auditTrails", auditTrails);
		return modelandview;
	}
}
