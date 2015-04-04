package com.glassify.facade;

import java.util.List;

import com.glassify.domain.Brand;

public interface BrandFacade {

	public void saveBrand(Brand brand);
	public List<Brand> getAllBrands();
}
