package com.glassify.dao;

import java.util.List;

import com.glassify.domain.Brand;

public interface BrandDao {

	public void saveBrand(Brand brand) throws Exception;
	public List<Brand> getAllBrands();
}
