package com.egen.model;

public class CompanyModel {

	private String name;
	private String website;
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
	@Override
	public String toString() {
		return "Company [name=" + name + ", website=" + website + "]";
	}
}
