package com.scc.dog.template;

import java.util.Map;

import io.swagger.annotations.ApiModelProperty;

public class BreedObject {

   @ApiModelProperty(notes = "Breed id", position = 1, allowEmptyValue = true)
   private int id;

   @ApiModelProperty(notes = "FCI Number", position = 2, allowEmptyValue = true)
   private String fciNumber;

   @ApiModelProperty(dataType = "com.scc.dog.template.swaggerType.BreedName", notes = "Breed name", position = 3, allowEmptyValue = true)
   private Map<String, Object> name;

   @ApiModelProperty(dataType = "com.scc.dog.template.swaggerType.BreedColor", notes = "Breed color", position = 4, allowEmptyValue = true)
   private Map<String, Object> color;

   @ApiModelProperty(dataType = "com.scc.dog.template.swaggerType.BreedVariety", notes = "Breed variety", position = 5, allowEmptyValue = true)
   private Map<String, Object> variety;

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public String getFciNumber() {
      return fciNumber;
   }

   public void setFciNumber(String fciNumber) {
      this.fciNumber = fciNumber;
   }

   public Map<String, Object> getName() {
      return name;
   }

   public void setName(Map<String, Object> name) {
      this.name = name;
   }

   public Map<String, Object> getColor() {
      return color;
   }

   public void setColor(Map<String, Object> color) {
      this.color = color;
   }

   public Map<String, Object> getVariety() {
      return variety;
   }

   public void setVariety(Map<String, Object> variety) {
      this.variety = variety;
   }

}
