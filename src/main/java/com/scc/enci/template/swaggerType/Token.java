package com.scc.enci.template.swaggerType;

import io.swagger.annotations.ApiModelProperty;

public class Token {

	@ApiModelProperty(notes = "Type property", position = 1, allowEmptyValue=true)
	private String type;

	@ApiModelProperty(notes = "Number property", position = 2, allowEmptyValue=true)
	private String number;

	public String getType() { return type; }
	public void setType(String type) { this.type = type; }

	public String getNumber() { return number; }
	public void setNumber(String number) { this.number = number; }

}
