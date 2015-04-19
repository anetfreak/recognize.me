package com.glassify.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.glassify.facade.CredentialFacade;
import com.glassify.util.ImageMatcher;
import com.glassify.util.MirrorClient;
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
		return new ModelAndView("upload");
	}
	
	@RequestMapping(value = "/uploadMessage", method = RequestMethod.POST)
	public @ResponseBody String uploadMessage(@RequestParam("message") String message) {
		logger.info(message);
		return "Success";
	}
	
	private static Logger logger = Logger.getLogger(UploadController.class.getName());
	
	@RequestMapping(value="/uploadImage", method=RequestMethod.POST)
	public @ResponseBody String uploadImage(@RequestParam("file") MultipartFile file) {
		String resultString = "";
		if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(file.getName())));
                stream.write(bytes);
                stream.close();
                
                resultString += "You successfully uploaded " + file.getName() + "! The file size was " + file.getSize()/1000 + " Kb.";
                logger.info(resultString);
                //Make a call to the OpenCV module to identify the brand.
                ImageMatcher matcher = new ImageMatcher();
                String result_brand = matcher.match(bytes);
                
                if(result_brand == "Failed") {
                	resultString += "\n Not matched with any brand we support.";
                	logger.info(resultString);
                	return resultString;
                }
                
                resultString += "\n Match: "+ result_brand;
                logger.info(resultString);
                
                //Make a call AdServer with the use and brand information to fetch the ad.
            //    PostRequestUtil request = new PostRequestUtil();
            //    request.setUrl("http://localhost:8080/retrieveAd"); //TODO remove hard coded Url
            //    request.setStrValues("brandName=Dell&latitude=1.1&longitude=5.5&category=Electronics"); //TODO remove hardcoded
                
//                String AdResponse = request.post();
//                //TODO - check if response is fine
//                resultString += "\nResponse from AdServer: " + AdResponse;
                
                logger.info(resultString);
                
                //TODO - Parse the response
                resultString += "\nParsed Ad Response: ";
                logger.info(resultString);
                
                //TODO - Make Ad to be sent to glass
                resultString += "\n Ad sending to glass: ";
                logger.info(resultString);
                
                //Get user Credential
                Credential credential = credentialFacade.getCredentialForUser("amit.agrawal@sjsu.edu").getCredential();
                resultString += "\nFetch User Credential Success ";
                logger.info(resultString);
                //Post Ad to Glass Mirror
                MirrorClient mirrorClient = new MirrorClient();
                TimelineItem timelineItem = mirrorClient.createTimeLineItemWithText("Ad for Brand : result_brand");
                mirrorClient.insertTimelineItem(credential, timelineItem);
                
                logger.info("You successfully uploaded " + 
                		file.getName() + "! The file size was " + 
                			file.getSize()/1000 + " Kb." +
                			"\n Match: "+ result_brand);
                return "You successfully uploaded " + file.getName() + "! The file size was " + file.getSize()/1000 + " Kb.";
            } catch (Exception e) {
            	e.printStackTrace();
                return "You failed to upload " + file.getName() + " => " + e.getMessage();
            }
        } else {
        	logger.info("Fiel is empty");
            return "You failed to upload " + file.getName() + " because the file was empty.";
        }
	}
}
