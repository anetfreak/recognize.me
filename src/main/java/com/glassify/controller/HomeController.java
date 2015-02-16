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


/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	@RequestMapping("/showUpload")
	public ModelAndView showUploadPage() {
		return new ModelAndView("file-upload");
	}
	
	@RequestMapping(value="/uploadImage", method=RequestMethod.POST)
	public @ResponseBody String uploadImage(@RequestParam("file") MultipartFile file) {
		if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(file.getName())));
                stream.write(bytes);
                stream.close();
                return "You successfully uploaded " + file.getName() + "! The file size was " + file.getSize()/1000 + " Kb.";
            } catch (Exception e) {
                return "You failed to upload " + file.getName() + " => " + e.getMessage();
            }
        } else {
            return "You failed to upload " + file.getName() + " because the file was empty.";
        }
	}
	
}
