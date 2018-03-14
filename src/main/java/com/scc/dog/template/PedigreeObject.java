package com.scc.dog.template;

import io.swagger.annotations.ApiModelProperty;

public class PedigreeObject {

	@ApiModelProperty(notes = "Pedigree country", position = 1, allowEmptyValue=true)
	private String country;

	@ApiModelProperty(notes = "Pedigree type", position = 2, allowEmptyValue=true)
	private String type;

	@ApiModelProperty(notes = "Pedigree number", position = 3, allowEmptyValue=true)
	private String number;

	@ApiModelProperty(notes = "Pedigree Date", position = 4, allowEmptyValue=true)
	private String obtentionDate;

	public String getType() { return type; }
	public void setType(String type) { this.type = type; }

	public String getNumber() { return number; }
	public void setNumber(String number) { this.number= number; }

	public String getCountry() { return country; }
	public void setCountry(String country) { this.country = country; }

	public String getObtentionDate() { return obtentionDate; }
	public void setObtentionDate(String obtentionDate) { this.obtentionDate = obtentionDate; }

	public PedigreeObject withType(String type){ this.setType(type); return this; }
	public PedigreeObject withNumber(String number){ this.setNumber(number); return this; }
	public PedigreeObject withCountry(String country){ this.setCountry(country); return this; }
	public PedigreeObject withObtentionDate(String obtentionDate){ this.setObtentionDate(obtentionDate); return this; }

}
