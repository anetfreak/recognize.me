package com.glassify.adserver.domain;

import java.sql.Timestamp;

public class Ad {

	private int id;	
	private String name;
	private String url;
	private AdContentType contentType;
	private AdBrand brand;
	private String region;
	private String language;
	private String content;
	private Timestamp createdDate;
	private Timestamp expiryDate;
	private AdCategory category;
	private double latitude;
	private double longitude;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public AdContentType getContentType() {
		return contentType;
	}
	public void setContentType(AdContentType contentType) {
		this.contentType = contentType;
	}
	public AdBrand getBrand() {
		return brand;
	}
	public void setBrand(AdBrand brand) {
		this.brand = brand;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	public Timestamp getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(Timestamp expiryDate) {
		this.expiryDate = expiryDate;
	}
	public AdCategory getCategory() {
		return category;
	}
	public void setCategory(AdCategory category) {
		this.category = category;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
}
