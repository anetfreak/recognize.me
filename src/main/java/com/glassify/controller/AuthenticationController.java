package com.glassify.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.glassify.facade.CredentialFacade;
import com.glassify.service.GoogleAuthenticationService;
import com.glassify.service.GoogleAuthenticationService.CodeExchangeException;
import com.glassify.service.GoogleAuthenticationService.NoRefreshTokenException;

/**
 * Handles requests for the static application pages.
 */
@Controller
public class AuthenticationController {
	
	@Autowired
	private CredentialFacade credentialFacade;
	
	@RequestMapping("/login")
	public ModelAndView showAboutPage() {
		return new ModelAndView("login");
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping("/oauth2callback")
	public void identifyClient(@RequestParam(value = "code", required = false) String code,
			@RequestParam(value = "state", required = false) String state, 
			HttpServletResponse response) {
		try {
			GoogleAuthenticationService authService = new GoogleAuthenticationService(credentialFacade);
			authService.getCredentials(code, state);
			response.sendRedirect("/");
		} catch (CodeExchangeException e) {
			e.printStackTrace();
		} catch (NoRefreshTokenException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}

//{
//	"html": "<article>\n  <section>\n    <p class=\"text-auto-size\"><strong class=\"blue\">AdFinder</strong> has a new advertisement for you.\n<br/><br/>\nTap to find out more..\n    </p>\n  </section>\n</article>",
//	"notification": {
//	"level": "DEFAULT"
//}
//}


//{
//  "html": "<article>\n  <section>\n    <p class=\"text-auto-size\"><strong class=\"blue\">AdFinder</strong><br/>Sorry, No offers found for : Starbucks. Please check back later.</p>\n  </section>\n</article>",
//	"notification": {
//	"level": "DEFAULT"
//	}
//}
