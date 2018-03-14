package com.scc.dog.template;

import io.swagger.annotations.ApiModelProperty;

public class TitleObject {

	@ApiModelProperty(notes = "Title code", position = 1, allowEmptyValue=true)
	private String title;

	@ApiModelProperty(notes = "Title name", position = 2, allowEmptyValue=true)
	private String name;

	@ApiModelProperty(notes = "Title type", position = 3, allowEmptyValue=true)
	private String type;

	@ApiModelProperty(notes = "Title country (ISO 3166 - 2 chars format)", position = 4, allowEmptyValue=true)
	private String country;

	@ApiModelProperty(notes = "Award date", position = 5, allowEmptyValue=true)
	private String obtentionDate;

	public String getTitle() { return title; }
	public void setTitle(String title) { this.title = title; }

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	public String getType() { return type; }
	public void setType(String type) { this.type = type; }

	public String getCountry() { return country; }
	public void setCountry(String country) { this.country = country; }

	public String getObtentionDate() { return obtentionDate; }
	public void setObtentionDate(String obtentionDate) { this.obtentionDate = obtentionDate; }

	public TitleObject withTitle(String title){ this.setTitle(title); return this; }
	public TitleObject withName(String name){ this.setName(name); return this; }
	public TitleObject withType(String type){ this.setType(type); return this; }
	public TitleObject withCountry(String country){ this.setCountry(country); return this; }
	public TitleObject withObtentionDate(String obtentionDate){ this.setObtentionDate(obtentionDate); return this; }

}
