package com.glassify.controller;

import java.io.PrintWriter;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.glassify.domain.MyCredential;
import com.glassify.facade.CredentialFacade;
import com.glassify.util.ImageMatcher;
import com.glassify.util.MyLogger;
import com.glassify.util.PostRequestUtil;
import com.google.api.client.auth.oauth2.Credential;

/**
 * Handles requests for the upload module pages. To be used without the front-end.
 */
@Controller
public class UploadController {
	
	@Autowired
	private CredentialFacade credentialFacade;
	
	@RequestMapping("/upload")
	public ModelAndView showUploadPage() {
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
	public @ResponseBody String uploadImage(@RequestParam("file") MultipartFile file,
			@RequestParam("email") String email,
			@RequestParam("latitude") String latitude,
			@RequestParam("longitude") String longitude) {
		
		System.out.println("Latitude - " + latitude);
		System.out.println("Longitude - " + longitude);
		System.out.println("Email - " + email);
		
		System.out.println("Got upload request");
		logger.info("Got upload request");
		String resultString = "";
		if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                //BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(file.getName())));
                //stream.write(bytes);
                //stream.close();
                
                resultString += "You successfully uploaded " + file.getName() + "! The file size was " + file.getSize()/1000 + " Kb.";
                //Make a call to the OpenCV module to identify the brand.
                ImageMatcher matcher = new ImageMatcher();
                String result_brand = matcher.match(bytes);
                
                if(result_brand == "Failed") {
                	resultString += "\n Not matched with any brand we support.";
                	logger.info(resultString);
                	return resultString;
                }
                
                resultString += "\n Match: "+ result_brand;
                
                //Make a call AdServer with the use and brand information to fetch the ad.
                PostRequestUtil request = new PostRequestUtil();
                request.setUrl("http://localhost:8080/adserver/retrieveAd"); //TODO remove hard coded Url
                request.setStrValues("brandName="+result_brand+"&latitude=1&longitude=5&category=Electronics"); //TODO remove hardcoded
                
                String AdResponse = request.post();
                //TODO - check if response is fine
                resultString += "\nResponse from AdServer: " + AdResponse;
                
                //TODO - Parse the response
                resultString += "\nParsed Ad Response: ";
                
                //TODO - Make Ad to be sent to glass
                resultString += "\n Ad sending to glass: ";
                Credential credential = null;
                //Get user Credential
                if (credentialFacade != null) {
                	MyCredential myCredential = credentialFacade.getCredentialForUser("amit.agrawal@sjsu.edu");
                	if(myCredential != null) {
                		credential = credentialFacade.getCredentialForUser("amit.agrawal@sjsu.edu").getCredential();
                    }
                	else {
                		logger.info("get credential return null");
                	}
                }
                else {
                	logger.info("get credential for user returned null");
                }
                
                resultString += "\nFetch User Credential Success ";
                //Post Ad to Glass Mirror TODO uncomment below lines
                //MirrorClient mirrorClient = new MirrorClient();
                //TimelineItem timelineItem = mirrorClient.createTimeLineItemWithText("Ad for Brand :"+ result_brand + "\n"+AdResponse);
                //mirrorClient.insertTimelineItem(credential, timelineItem);
                
                return "You successfully uploaded " + file.getName() + "! The file size was " + file.getSize()/1000 + " Kb.";
            } catch (Exception e) {
            	e.printStackTrace();
                return "You failed to upload " + file.getName() + " => " + e.getMessage();
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
