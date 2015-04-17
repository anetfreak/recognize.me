package com.glassify.controller;

import java.io.IOException;
import java.io.InputStream;

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
import com.google.api.client.http.InputStreamContent;
import com.google.api.services.mirror.Mirror;
import com.google.api.services.mirror.model.NotificationConfig;
import com.google.api.services.mirror.model.TimelineItem;

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
			@RequestParam(value = "state", required = false) String state) {
		System.out.println("Code - " + code);
		System.out.println("State - " + state);
		try {
			GoogleAuthenticationService authService = new GoogleAuthenticationService(credentialFacade);
			authService.getCredentials(code, state);
//			insertTimelineItem(service, "Sample text to see if Timeline works", null, null, "AUDIO_ONLY");
		} catch (CodeExchangeException e) {
			e.printStackTrace();
		} catch (NoRefreshTokenException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	private TimelineItem insertTimelineItem(Mirror service, String text, String contentType, InputStream attachment, String notificationLevel) {
		TimelineItem timelineItem = new TimelineItem();
		timelineItem.setText(text);
		if (notificationLevel != null && notificationLevel.length() > 0) {
			timelineItem.setNotification(new NotificationConfig().setLevel(notificationLevel));
		}
		try {
			if (contentType != null && contentType.length() > 0 && attachment != null) {
				// Insert both metadata and attachment.
				InputStreamContent mediaContent = new InputStreamContent(contentType, attachment);
				return service.timeline().insert(timelineItem, mediaContent).execute();
			} else {
				// Insert metadata only.
				return service.timeline().insert(timelineItem).execute();
			}
		} catch (IOException e) {
			System.err.println("An error occurred: " + e);
			return null;
		}
	}
}

//{
//	"html": "<article>\n  <section>\n    <p class=\"text-auto-size\"><strong class=\"blue\">AdFinder</strong> has a new advertisement for you.\n<br/><br/>\nTap to find out more..\n    </p>\n  </section>\n</article>",
//	"notification": {
//	"level": "DEFAULT"
//}
//}
