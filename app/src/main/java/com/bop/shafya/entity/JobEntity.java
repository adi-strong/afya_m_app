package com.bop.shafya.entity;

import com.google.gson.annotations.SerializedName;

public class JobEntity {
  @SerializedName("id")
  private int id;
  
  @SerializedName("name")
  private String name;
  
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public int getId() {
    return id;
  }
  
  public void setId(int id) {
    this.id = id;
  }
}
