package com.glassify.controller;

import java.io.File;
import java.util.List;

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

/**
 * Handles requests for the brand module pages.
 */
@Controller
public class BrandController {

	@Autowired
	private BrandFacade brandfacade;
	private String saveDirectory = "/tmp/";

	@RequestMapping(value = "/addBrand")
	public ModelAndView showCreateAdPage() {
		ModelAndView modelView = new ModelAndView("add-brand");
		return modelView;
	}

	@RequestMapping(value = "/addBrand", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView showAddBrandPage(
			@RequestParam("brandName") String brandName,
			@RequestParam("url") String url,
			@RequestParam("category") String category,
			@RequestParam("path") String path,
			@RequestParam("description") String description,
			@RequestParam(value = "brandFiles", required = false) MultipartFile brandImage) throws Exception{
		Brand brand = new Brand();
		brand.setName(brandName.toUpperCase());
		brand.setWebsite(url);
		brand.setDomain(category);
		brand.setDesc(description);
		
		if (!brandImage.isEmpty()) {
			/*
			try {
				validateImageFormat(brandImage);
			} catch (Exception e) {
				e.printStackTrace();
			}
			 */
			
			String filepath = saveImage(brandImage);
				brand.setBrandImage(filepath);
		}
		
		try {
			brandfacade.saveBrand(brand);
			return new ModelAndView("successBrand");
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("errorBrand");
		}
	}
	
	/**
	 * Method to save the file/brand image to file system
	 * @param brandImage
	 * @return
	 */
	private String saveImage(MultipartFile brandImage){
		String filelocation = saveDirectory + brandImage.getOriginalFilename();
		if (brandImage != null && !(brandImage.isEmpty())) {
			try {
				System.out.println("Saving file: "
						+ brandImage.getOriginalFilename());
				if (!brandImage.getOriginalFilename().equals("")) {
					brandImage.transferTo(new File(filelocation));
					System.out.println("You have successfully uploaded the file : " +filelocation);
					
				}
			} catch (Exception e) {
				System.out.println("Failed to upload the file "
						+ brandImage.getOriginalFilename());
			}
		} 
		else {
			System.out.println("Could not upload the file as it was empty.");
			return null;
		}
		return filelocation;
	}

	@RequestMapping(value = "/showBrands")
	public ModelAndView showBrands() {
		List<Brand> brands = brandfacade.getAllBrands();
		ModelAndView modelandview = new ModelAndView("show-brands");
		modelandview.addObject("brands", brands);
		return modelandview;
	}
	
	@RequestMapping("/successBrand")
	public ModelAndView getsuccessMessage() {
		ModelAndView modelAndView = new ModelAndView("successBrand");
		return modelAndView;
	}
	
	@RequestMapping("/errorBrand")
	public ModelAndView getErrorMessage() {
		ModelAndView modelAndView = new ModelAndView("errorBrand");
		return modelAndView;
	}
}
