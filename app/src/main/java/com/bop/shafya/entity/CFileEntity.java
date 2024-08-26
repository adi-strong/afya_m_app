package com.bop.shafya.entity;

import com.google.gson.annotations.SerializedName;

public class CFileEntity {

  @SerializedName("id")
  private String id;

  @SerializedName("name")
  private String name;
  
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public String getId() {
    return id;
  }
  
  public void setId(String id) {
    this.id = id;
  }
}
