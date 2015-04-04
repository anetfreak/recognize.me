package com.glassify.facade;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.glassify.dao.BrandDao;
import com.glassify.domain.Brand;

@Component
public class BrandFacadeImpl implements BrandFacade{
	
	@Autowired
	private BrandDao brandDao;
	
	public void saveBrand(Brand brand){
		brandDao.saveBrand(brand);
	}
	
	public List<Brand> getAllBrands(){
		return brandDao.getAllBrands();
	}
}
