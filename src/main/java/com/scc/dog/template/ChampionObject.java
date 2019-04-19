package com.scc.dog.template;

import io.swagger.annotations.ApiModelProperty;

public class ChampionObject {

   @ApiModelProperty(notes = "Dog id", position = 1, allowEmptyValue = true)
   private int id;

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public ChampionObject withId(int id) {
      this.setId(id);
      return this;
   }

}
