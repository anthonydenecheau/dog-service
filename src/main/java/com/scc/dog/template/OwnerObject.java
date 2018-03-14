package com.scc.dog.template;

import io.swagger.annotations.ApiModelProperty;

public class OwnerObject{

	@ApiModelProperty(notes = "Owner lastname", position = 1, allowEmptyValue=true)
	private String lastName;

	@ApiModelProperty(notes = "Owner firstname", position = 2, allowEmptyValue=true)
	private String firstName;

	@ApiModelProperty(notes = "Owner address", position = 3, allowEmptyValue=true)
	private String address;

	@ApiModelProperty(notes = "Owner zip code", position = 4, allowEmptyValue=true)
	private String zipCode;

	@ApiModelProperty(notes = "Owner town", position = 5, allowEmptyValue=true)
	private String town;

	@ApiModelProperty(notes = "Owner Country (ISO 3166 - 2 chars format)", position = 6, allowEmptyValue=true)
	private String country;

	public String getFirstName() { return firstName; }
	public void setFirstName(String firstName) { this.firstName = firstName; }
	
	public String getLastName() { return lastName; }
	public void setLastName(String lastName) { this.lastName = lastName; }
	
	public String getAddress() { return address; }
	public void setAddress(String address) { this.address = address; }
	
	public String getZipCode() { return zipCode; }
	public void setZipCode(String zipCode) { this.zipCode = zipCode; }
	
	public String getTown() { return town; }
	public void setTown(String town) { this.town = town; }
	
	public String getCountry() { return country; }
	public void setCountry(String country) { this.country = country; }

	public OwnerObject withFirstName(String firstName){ this.setFirstName(firstName); return this; }
	public OwnerObject withLastName(String lastName){ this.setLastName(lastName); return this; }
	public OwnerObject withAddress(String address){ this.setAddress(address); return this; }
	public OwnerObject withZipCode(String zipCode){ this.setZipCode(zipCode); return this; }
	public OwnerObject withTown(String town){ this.setTown(town); return this; }
	public OwnerObject withCountry(String country){ this.setCountry(country); return this; }

}
