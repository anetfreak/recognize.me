package com.glassify.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.glassify.domain.AuditTrailFailure;
import com.glassify.domain.AuditTrailResult;
import com.glassify.domain.AuditTrailSuccess;
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
	
	@RequestMapping("/stats/getSuccessResults")
	public @ResponseBody List<AuditTrailSuccess> getAuditSuccessResults() {
		
		AuditTrailResult result = auditTrailfacade.getResultStats();
		return result.getSuccess();
	}
	
	@RequestMapping("/stats/getFailureResults")
	public @ResponseBody List<AuditTrailFailure> getAuditFailureResults() {
		
		AuditTrailResult result = auditTrailfacade.getResultStats();
		return result.getFailures();
	}
	
}
