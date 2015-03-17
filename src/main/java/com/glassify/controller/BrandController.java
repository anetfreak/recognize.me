package com.glassify.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Handles requests for the brand module pages.
 */
@Controller
public class BrandController {
	
	@RequestMapping("/addBrand")
	public ModelAndView showAddBrandPage() {
		return new ModelAndView("add-brand");
	}

}
