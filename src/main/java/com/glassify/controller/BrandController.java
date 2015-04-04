package com.glassify.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.glassify.domain.Brand;
import com.glassify.facade.BrandFacade;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Handles requests for the brand module pages.
 */
@Controller
public class BrandController {

	@Autowired
	private BrandFacade brandfacade;

	@RequestMapping(value = "/addBrand")
	public ModelAndView showCreateAdPage() {
		ModelAndView modelView = new ModelAndView("add-brand");
		return modelView;
	}

	@RequestMapping(value = "/addBrand", method = RequestMethod.POST)
	@ResponseBody
	public void showAddBrandPage(
			@RequestParam("brandName") String brandName,
			@RequestParam("url") String url,
			@RequestParam("category") String category,
			@RequestParam("path") String path,
			@RequestParam("description") String description,
			@RequestParam(value = "brandFiles", required = false) MultipartFile brandImage) {
		Brand brand = new Brand();
		brand.setName(brandName);
		brand.setWebsite(url);
		brand.setDomain(category);
		if (!brandImage.isEmpty()) {
			/*
			try {
				validateImageFormat(brandImage);
			} catch (Exception e) {
				e.printStackTrace();
			}
			 */
			try {
				String filepath = saveImage(brand.getName(), brandImage);
				brand.setBrandImage(filepath);
			} catch (IOException ioexception) {
				ioexception.printStackTrace();
			}
		}
		brand.setDesc(description);
		try {
			brandfacade.saveBrand(brand);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method to check the if the format of the image uploaded is jpeg or not
	 * 
	 * @param brandImage
	 */
	private void validateImageFormat(MultipartFile brandImage) {
		if (!brandImage.getContentType().equals("image/jpeg")) {
			throw new RuntimeException("Only JPG images are accepted");
		}
	}

	/**
	 * Method to save the image locally to the file system
	 * 
	 * @param filename
	 * @param image
	 * @throws RuntimeException
	 * @throws IOException
	 */
	private String saveImage(String brandName, MultipartFile image)
			throws RuntimeException, IOException {
		try {
			byte[] bytes = image.getBytes();
			String rootPath = System.getProperty("user.name");
			System.out.println(rootPath);
			File dir = new File(rootPath + File.separator + "tmpFiles");
            if (!dir.exists())
                dir.mkdirs();
            
            System.out.println(dir);
         // Create the file on server
            File serverFile = new File(dir.getAbsolutePath()
                    + File.separator + brandName);
            System.out.println(serverFile);
            
            BufferedOutputStream stream = new BufferedOutputStream(
                    new FileOutputStream(serverFile));
            stream.write(bytes);
            stream.close();
            
            System.out.println("Server File Location="
                    + serverFile.getAbsolutePath());

           return serverFile.toString();
		} catch (Exception e) {
            return "You failed to upload " + brandName + " => " + e.getMessage();
        }
	}

	@RequestMapping(value = "/showBrands")
	public ModelAndView showBrands() {
		List<Brand> brands = brandfacade.getAllBrands();
		ModelAndView modelandview = new ModelAndView("show-brands");
		modelandview.addObject("brands", brands);
		return modelandview;
	}
}
