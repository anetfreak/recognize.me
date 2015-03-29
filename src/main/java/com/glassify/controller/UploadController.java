package com.glassify.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.glassify.util.ImageMatcher;

/**
 * Handles requests for the upload module pages. To be used without the front-end.
 */
@Controller
public class UploadController {
	
	@RequestMapping("/upload")
	public ModelAndView showUploadPage() {
		return new ModelAndView("upload");
	}
	
	@RequestMapping(value = "/uploadMessage", method = RequestMethod.POST)
	public @ResponseBody String uploadMessage(@RequestParam("message") String message) {
		System.out.println(message);
		return "Success";
	}
	
	@RequestMapping(value="/uploadImage", method=RequestMethod.POST)
	public @ResponseBody String uploadImage(@RequestParam("file") MultipartFile file) {
		if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(file.getName())));
                stream.write(bytes);
                stream.close();
                
                //Call match image at opencv
                ImageMatcher matcher = new ImageMatcher();
                String result_brand = matcher.matchImage(bytes);
                
                //TODO - Make a call to the OpenCV module to identify the brand.
                //TODO - Make a call AdServer with the use and brand information to fetch the ad.
                System.out.println("You successfully uploaded " + 
                		file.getName() + "! The file size was " + 
                			file.getSize()/1000 + " Kb." +
                			"\n Match: "+ result_brand);
                return "You successfully uploaded " + file.getName() + "! The file size was " + file.getSize()/1000 + " Kb.";
            } catch (Exception e) {
            	e.printStackTrace();
                return "You failed to upload " + file.getName() + " => " + e.getMessage();
            }
        } else {
            return "You failed to upload " + file.getName() + " because the file was empty.";
        }
	}
}
