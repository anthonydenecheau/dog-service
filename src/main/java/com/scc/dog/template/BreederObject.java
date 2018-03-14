package com.scc.dog.template;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.scc.dog.model.Breeder;

import io.swagger.annotations.ApiModelProperty;

public class BreederObject {

	@ApiModelProperty(notes = "Breeder lastname", position = 1)
	private String lastName;

	@ApiModelProperty(notes = "Breeder firstname", position = 2)
	private String firstName;
	
	@JsonIgnore
	private String onSuffixe;

	public String getFirstName() { return firstName; }
	public void setFirstName(String firstName) { this.firstName = firstName; }
	
	public String getLastName() { return lastName; }
	public void setLastName(String lastName) { this.lastName = lastName; }

	public String getOnSuffixe() { return onSuffixe; }
	public void setOnSuffixe(String onSuffixe) { this.onSuffixe = onSuffixe; }

	public BreederObject withFirstName(String firstName){ this.setFirstName(firstName); return this; }
	public BreederObject withLastName(String lastName){ this.setLastName(lastName); return this; }
	public BreederObject withOnSuffixe(String onSuffixe){ this.setOnSuffixe(onSuffixe); return this; }

}
