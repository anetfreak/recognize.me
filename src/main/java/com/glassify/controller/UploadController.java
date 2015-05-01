package com.glassify.controller;

import java.io.PrintWriter;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.glassify.domain.MyCredential;
import com.glassify.facade.CredentialFacade;
import com.glassify.util.ImageMatcher;
import com.glassify.util.MirrorClient;
import com.glassify.util.MyLogger;
import com.glassify.util.PostRequestUtil;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.services.mirror.model.TimelineItem;

/**
 * Handles requests for the upload module pages. To be used without the front-end.
 */
@Controller
public class UploadController {
	
	@Autowired
	private CredentialFacade credentialFacade;
	
	@RequestMapping("/upload")
	public ModelAndView showUploadPage() {
		MirrorClient client = new MirrorClient();
		client.testTimelineItem(credentialFacade);
		return new ModelAndView("upload");
	}
	
	@RequestMapping(value = "/uploadMessage", method = RequestMethod.POST)
	public @ResponseBody String uploadMessage(@RequestParam("message") String message) {
		logger.info(message);
		return "Success";
	}
	
	private static Logger logger = Logger.getLogger(UploadController.class.getName());
	private static PrintWriter writer = MyLogger.getWriter();
	
	@RequestMapping(value="/uploadImage", method=RequestMethod.POST)
	public @ResponseBody String uploadImage(
			@RequestParam("file") MultipartFile file,
			@RequestParam("email") MultipartFile email,
			@RequestParam("latitude") MultipartFile latitude,
			@RequestParam("longitude") MultipartFile longitude)
			{
		String resultString = "";
		resultString += "\nGot upload request";
		resultString += "\nLatitude - " + latitude.getOriginalFilename();
		resultString += "\nLongitude - " + longitude.getOriginalFilename();
		resultString += "\nEmail - " + email.getOriginalFilename();
		
		String userId = email.getOriginalFilename();
		String userLat = latitude.getOriginalFilename();
		String userLong = longitude.getOriginalFilename();

		if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();              
                resultString += "\nYou successfully uploaded " + file.getName() + "! The file size was " + file.getSize()/1000 + " Kb.";
                
                //Make a call to the OpenCV module to identify the brand.
                ImageMatcher matcher = new ImageMatcher();
                String result_brand = matcher.match(bytes);
                
                if(result_brand == "Failed") {
                	resultString += "\n Not matched with any brand we support.";
                	logger.info(resultString);
                	return resultString;
                }
                
                resultString += "\nMatch: "+ result_brand;
                
                String AdResponse = "";
                if(result_brand.equalsIgnoreCase("Not Found")){
                	AdResponse = "Thank You for using RecognizeMe.\n Unfortunately, we could not identify any brand. Please take a clear any closer picture again.";
                } else {
	                //Make a call AdServer with the use and brand information to fetch the ad.
	                PostRequestUtil request = new PostRequestUtil();
	                request.setUrl("http://localhost:8080/adserver/retrieveAd"); //TODO remove hard coded Url
	                request.setStrValues("brandName="+result_brand+"&latitude=" + userLat +"&longitude=" + userLong + "&category=Promotion"); //TODO remove hardcoded
	                AdResponse = request.post();
	                if(StringUtils.isEmpty(AdResponse)){
	                	AdResponse = "Sorry, No offers found for : " + result_brand + ". Please check back later.";
	                }
                }
	                
                //TODO - check if response is fine
                resultString += "\nResponse from AdServer: " + AdResponse;
                
                //TODO - Parse the response
                resultString += "\nParsed Ad Response: " + AdResponse;
                
                //TODO - Make Ad to be sent to glass
                resultString += "\nAd sending to glass";
                Credential credential = null;
                //Get user Credential
                if (credentialFacade != null) {
                	MyCredential myCredential = credentialFacade.getCredentialForUser(userId);
                	if(myCredential != null) {
                		credential = credentialFacade.getCredentialForUser(userId).getCredential();
                    }
                	else {
                		resultString += "\nget credential return null";
                	}
                }
                else {
                	resultString += "\nget credential for user returned null";
                }
                
                resultString += "\nFetch User Credential Success ";
                //Post Ad to Glass Mirror TODO uncomment below lines
                MirrorClient mirrorClient = new MirrorClient();
                TimelineItem timelineItem = mirrorClient.createTimeLineItemWithText(AdResponse);
                mirrorClient.insertTimelineItem(credential, timelineItem);
                resultString += "\nAd posted to glass time line";
                return resultString;
                
            } catch (Exception e) {
            	e.printStackTrace();
            	resultString += "\nException: " + e.getMessage();
                return "Exception: " + file.getName() + " => " + e.getMessage();
            }
            finally{
            	logger.info("Final Result string: ");
            	logger.info(resultString);
            }
        } else {
        	logger.info("Fiel is empty");
            return "You failed to upload " + file.getName() + " because the file was empty.";
        }
	}
}
