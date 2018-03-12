package com.scc.enci.template.swaggerType;

import io.swagger.annotations.ApiModelProperty;

public class Mother {

	@ApiModelProperty(notes = "Name property", position = 1, allowEmptyValue=true)
	private String name;

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

}
