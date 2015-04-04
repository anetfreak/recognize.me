package com.glassify.dao;

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

}
