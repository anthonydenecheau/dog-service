package com.scc.dog.template.swaggerType;

import io.swagger.annotations.ApiModelProperty;

public class BreedVariety {

	@ApiModelProperty(notes = "Variety id", position = 1, allowEmptyValue=true)
	private int id;

	@ApiModelProperty(notes = "Iso code country property", position = 1, allowEmptyValue=true)
	private String fr;

	public String getFr() { return fr; }
	public void setName(String fr) { this.fr = fr; }

	public int getId() { return id; }
	public void setId(int id) { this.id = id; }

}
