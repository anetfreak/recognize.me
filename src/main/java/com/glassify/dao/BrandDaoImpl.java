package com.glassify.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.stereotype.Component;

import com.glassify.domain.Brand;

@Component
public class BrandDaoImpl implements BrandDao{

	@Autowired
	private DataSource dataSource;
	
	@Override
	public void saveBrand(Brand brand) {
		
		String query = "insert into ad_brand (name, website, domain, desc) values (?,?,?,?)";

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		Object[] args = new Object[] { brand.getName(), brand.getWebsite(), 
				brand.getDomain(), brand.getDesc()
				};

		try{
		int out = jdbcTemplate.update(query, args);

		if (out != 0) {
			System.out.println("Brand saved with id=" + brand.getId());
		} else {
			System.out.println("Brand save failed with id=" + brand.getId());
		}
		}
		catch(Exception exception){
			System.out.println("Error encountered in saving the brand details to the database");
			exception.printStackTrace();
		}
	}

	
}
