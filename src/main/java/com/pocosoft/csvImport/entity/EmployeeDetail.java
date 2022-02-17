package com.pocosoft.csvImport.entity;

public class EmployeeDetail {
	
	private String name;
	private String email;
	private String address;
	private String telephone;
	
	
	
	public EmployeeDetail()
	{
		
	}
	
	
	public EmployeeDetail(String name, String email, String address, String telephone) {
		super();
		this.name = name;
		this.email = email;
		this.address = address;
		this.telephone = telephone;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	

}
