package com.glassify.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import com.glassify.domain.Brand;

@Component
public class BrandDaoImpl implements BrandDao {

	@Autowired
	private DataSource dataSource;

	public void saveBrand(Brand brand) {

		String query = "insert into ad_brand (id, name, website, domain, description, brandImage) values (?,?,?,?,?,?)";

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		Object[] args = new Object[] { brand.getId(), brand.getName(), brand.getWebsite(),
				brand.getDomain(), brand.getDesc(), brand.getBrandImage() };
		
		try {
			int out = jdbcTemplate.update(query, args);

			if (out != 0) {
				System.out.println("Brand saved with id= " + brand.getId());
			} else {
				System.out.println("Brand save failed with id= " + brand.getId());
			}
		} catch (Exception exception) {
			System.out
					.println("Error encountered in saving the brand details to the database");
			exception.printStackTrace();
		}
	}
	
	public List<Brand> getAllBrands(){
		String query = "select id, name, website, domain, description, brandImage from ad_brand";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		List<Brand> brandList = new ArrayList<Brand>();

		List<Map<String, Object>> brandRows = jdbcTemplate.queryForList(query);

		for (Map<String, Object> brandRow : brandRows) {
			Brand brand = new Brand();
			brand.setId(Integer.parseInt(String.valueOf(brandRow.get("id"))));
			brand.setName(String.valueOf(brandRow.get("name")));
			brand.setWebsite(String.valueOf(brandRow.get("website")));
			brand.setDomain(String.valueOf(brandRow.get("domain")));
			brand.setDesc(String.valueOf(brandRow.get("description")));
			brand.setBrandImage(String.valueOf(brandRow.get("brandImage")));
			brandList.add(brand);
		}
		return brandList;
	}

}
