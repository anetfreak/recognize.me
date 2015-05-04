package com.glassify.controller;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.joda.time.DateTime;
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
import com.glassify.facade.AuditTrailFacade;
import com.glassify.facade.CredentialFacade;
import com.glassify.util.AuditTrail;
import com.glassify.util.ImageMatcher;
import com.glassify.util.MirrorClient;
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
	
	@Autowired
	private AuditTrailFacade auditTrailFacade;
	private AuditTrail auditTrail;
	final SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
	
	private static Logger logger = Logger.getLogger(UploadController.class.getName());
	
	private Date getCurrentDatetime() {
		try {
			return dateFormat.parse((new DateTime()).toString("MM/dd/yyyy hh:mm:ss"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("/upload")
	public ModelAndView showUploadPage() {
		//MirrorClient client = new MirrorClient();
		//client.testTimelineItem(credentialFacade);
		List<String> imageUrlList = new ArrayList<String>();  
		File imageDir = new File("/Users/amitagra/ImageMatching/glassImages");  
		for(File imageFile : imageDir.listFiles()){  
		  String imageFileName = imageFile.getName();  

		  // add this images name to the list we are building up  
		  imageUrlList.add(imageFileName);  

		}  
		ModelAndView modelandview = new ModelAndView("upload");
		modelandview.addObject("imageUrlList", imageUrlList);
		return modelandview;
	}
	
	@RequestMapping(value = "/uploadMessage", method = RequestMethod.POST)
	public @ResponseBody String uploadMessage(@RequestParam("message") String message) {
		logger.info(message);
		return "Success";
	}
	
	@RequestMapping(value="/uploadImage", method=RequestMethod.POST)
	public @ResponseBody String uploadImage(
			@RequestParam("file") MultipartFile file,
			@RequestParam("email") MultipartFile email,
			@RequestParam("latitude") MultipartFile latitude,
			@RequestParam("longitude") MultipartFile longitude) throws ParseException {
		
		auditTrail = new AuditTrail();
		/*logging in DB*/auditTrail.setType("uploadImage");
		/*logging in DB*/auditTrail.setStartTimeUpload(getCurrentDatetime());
		
		String resultString = "";
		resultString += "\nGot upload request";
		resultString += "\nLatitude - " + latitude.getOriginalFilename();
		resultString += "\nLongitude - " + longitude.getOriginalFilename();
		resultString += "\nEmail - " + email.getOriginalFilename();
		
		String userId = email.getOriginalFilename();
		String userLat = latitude.getOriginalFilename();
		String userLong = longitude.getOriginalFilename();
		/*logging in DB*/auditTrail.setEmail(userId);
		/*logging in DB*/auditTrail.setLatitude(userLat);
		/*logging in DB*/auditTrail.setLongitude(userLong);

		if (!file.isEmpty()) {
			/*logging in DB*/auditTrail.setEndTimeUpload(getCurrentDatetime());
            try {
                byte[] bytes = file.getBytes();              
                resultString += "\nYou successfully uploaded " + file.getName() + "! The file size was " + file.getSize()/1000 + " Kb.";
                /*logging in DB*/auditTrail.setFileSize(file.getSize()/1000);
                
                
                
                /*****STEP: Call Image Matcher to get Brand*****/
                //Make a call to the OpenCV module to identify the brand.
                /*logging in DB*/auditTrail.setStartTimeMatch(getCurrentDatetime());
                ImageMatcher matcher = new ImageMatcher();
                String result_brand = matcher.match(bytes);        
                resultString += "\nMatch: "+ result_brand;
                /*logging in DB*/auditTrail.setEndTimeMatch(getCurrentDatetime());
                
                
                
                /*****STEP: Get Advertisement from Ad Server*****/
                /*logging in DB*/auditTrail.setStartTimeAd(getCurrentDatetime());
                String AdResponse = "";
                if(result_brand.equalsIgnoreCase("Not Found")){
                	AdResponse = "Thank You for using RecognizeMe.\n Unfortunately, we could not identify any brand. Please take a clear any closer picture again.";
                } else {
                	/*logging in DB*/auditTrail.setMatched(true);
                    /*logging in DB*/auditTrail.setBrand(result_brand);
                    
	                //Make a call AdServer with the use and brand information to fetch the ad.
	                PostRequestUtil request = new PostRequestUtil();
	                request.setUrl("http://localhost:8080/adserver/retrieveAd"); //TODO remove hard coded Url
	                request.setStrValues("brandName="+result_brand+"&latitude=" + userLat +"&longitude=" + userLong + "&category=Promotion"); //TODO remove hardcoded
	                AdResponse = request.post();
	                if(StringUtils.isEmpty(AdResponse)){
	                	AdResponse = "Sorry, No offers found for : " + result_brand + ". Please check back later.";
	                	/*logging in DB*/auditTrail.setAdText(AdResponse);
	                }
	                else
	                {
	                	/*logging in DB*/auditTrail.setAdFound(true);;
	                    /*logging in DB*/auditTrail.setAdText(AdResponse);
	                }
                }
                resultString += "\nResponse from AdServer: " + AdResponse;
                resultString += "\nParsed Ad Response: " + AdResponse;
                /*logging in DB*/auditTrail.setEndtimeAd(getCurrentDatetime());
                

                /*****STEP: Sending Ad to GLASS*****/
                resultString += "\nAd sending to glass";
                Credential credential = null;
                //Get user Credential
                if (credentialFacade != null) {
                	MyCredential myCredential = credentialFacade.getCredentialForUser(userId);
                	if(myCredential != null) {
                		credential = credentialFacade.getCredentialForUser(userId).getCredential();
                		/*logging in DB*/auditTrail.setGetCredential(true);
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
                /*logging in DB*/auditTrail.setAdPosted(true);
                return resultString;
                
            } catch (Exception e) {
            	e.printStackTrace();
            	resultString += "\nException: " + e.getMessage();
                return "Exception: " + file.getName() + " => " + e.getMessage();
            }
            finally{
            	logger.info("Final Result string: ");
            	logger.info(resultString);
            	logger.info(auditTrail.toString());
            	try {
					auditTrailFacade.saveAuditTrail(auditTrail);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
            
        } else {
        	logger.info("File is empty");
            return "You failed to upload " + file.getName() + " because the file was empty.";
        }
	}
	
	@RequestMapping(value="/testUpload", method=RequestMethod.POST)
	public @ResponseBody String testUpload(@RequestParam("file") MultipartFile file,
			@RequestParam("email") String email,
			@RequestParam("latitude") String latitude,
			@RequestParam("longitude") String longitude) {
	
		System.out.println("Email - " + email);
		System.out.println("Latitude - " + latitude);
		System.out.println("Longitude - " + longitude);
		System.out.println("\nYou successfully uploaded " + file.getName() + "! The file size was " + file.getSize()/1000 + " Kb.");
		
		return "success";
	}
	
}
