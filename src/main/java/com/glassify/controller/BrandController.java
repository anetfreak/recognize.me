package com.glassify.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
			@RequestParam(value = "brandFiles", required=false) String brandImage
			) {
		Brand brand = new Brand();
		brand.setName(brandName);
		brand.setWebsite(url);
		brand.setDomain(category);
		//TODO: Change this to upload file content
		brand.setDesc(description);		
		brand.setBrandImage(brandImage);
		
		try{
			brandfacade.saveBrand(brand);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
