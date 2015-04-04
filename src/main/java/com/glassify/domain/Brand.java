package com.glassify.domain;

public class Brand {
	
	private int id;
	private String name;
	private String website;
	private String domain;
	private String desc;
	private String brandImage;
	
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
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getBrandImage() {
		return brandImage;
	}
	public void setBrandImage(String file) {
		this.brandImage = file;
	}
}
