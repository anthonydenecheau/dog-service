package com.scc.dog.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ServiceConfig{

  @Value("${example.property}")
  private String exampleProperty;

  @Value("${authentification.key}")
  private String authKey;

  @Value("${authentification.value}")
  private String authValue;

  public String getExampleProperty(){
    return exampleProperty;
  }

  public String getAuthKey(){
	return authKey;
  }

  public String getAuthValue(){
	return authValue;
  }

}
