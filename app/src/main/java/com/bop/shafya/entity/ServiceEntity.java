package com.bop.shafya.entity;

import com.google.gson.annotations.SerializedName;

public class ServiceEntity {
  @SerializedName("id")
  private int id;
  
  @SerializedName("name")
  private String name;
  
  @SerializedName("department")
  private DepartmentEntity department;
  
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public DepartmentEntity getDepartment() {
    return department;
  }
  
  public void setDepartment(DepartmentEntity department) {
    this.department = department;
  }
  
  public int getId() {
    return id;
  }
  
  public void setId(int id) {
    this.id = id;
  }
}
