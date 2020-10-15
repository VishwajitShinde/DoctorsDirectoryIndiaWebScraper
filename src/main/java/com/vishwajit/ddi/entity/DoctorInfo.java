package com.vishwajit.ddi.entity;

public class DoctorInfo {

	private int index = 0;
	private String name = "";
	private String gender = "";
	private String location = "";
	private String phone = "";
	private String website = "";
	private String mailId = "";
	
	private String degree = "";
	private String specialization = "";
	private String desc = "";
	
	private String delimeter = "#$#$";
	
	public DoctorInfo(int index, String name, String gender, String adress, String phone, String mailId, String website, String degree,
			String specialization, String desc) {
		super();
		this.index = index;
		this.name = name;
		this.gender = gender;
		this.location = adress;
		this.phone = phone;
		this.mailId = mailId;
		this.website= website; 
		this.degree = degree;
		this.specialization = specialization;
		this.desc = desc;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAdress() {
		return location;
	}

	public void setAdress(String adress) {
		this.location = adress;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMailId() {
		return mailId;
	}

	public void setMailId(String mailId) {
		this.mailId = mailId;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getSpecialization() {
		return specialization;
	}

	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getDelimeter() {
		return delimeter;
	}

	public void setDelimeter(String delimeter) {
		this.delimeter = delimeter;
	}

	@Override
	public String toString() {
		return "" + index + delimeter + name + delimeter + gender + delimeter + location + delimeter + phone
				+ delimeter + website + delimeter + mailId + delimeter + degree + delimeter
				+ specialization + delimeter + desc + "";
	}
}
