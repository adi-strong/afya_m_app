package com.bop.shafya.entity;

import com.google.gson.annotations.SerializedName;

public class AgentEntity {
  @SerializedName("id")
  private int id;
  
  @SerializedName("name")
  private String name;
  
  @SerializedName("lastName")
  private String lastName;
  
  @SerializedName("firstName")
  private String firstName;
  
  @SerializedName("sex")
  private String sex;
  
  @SerializedName("maritalStatus")
  private String maritalStatus;
  
  @SerializedName("email")
  private String email;
  
  @SerializedName("phone")
  private String phone;
  
  @SerializedName("service")
  private ServiceEntity service;
  
  @SerializedName("grade")
  private GradeEntity grade;
  
  @SerializedName("job")
  private JobEntity job;
  
  @SerializedName("address")
  private String address;
  
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public String getLastName() {
    return lastName;
  }
  
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }
  
  public String getFirstName() {
    return firstName;
  }
  
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }
  
  public String getSex() {
    return sex;
  }
  
  public void setSex(String sex) {
    this.sex = sex;
  }
  
  public String getMaritalStatus() {
    return maritalStatus;
  }
  
  public void setMaritalStatus(String maritalStatus) {
    this.maritalStatus = maritalStatus;
  }
  
  public String getEmail() {
    return email;
  }
  
  public void setEmail(String email) {
    this.email = email;
  }
  
  public String getPhone() {
    return phone;
  }
  
  public void setPhone(String phone) {
    this.phone = phone;
  }
  
  public ServiceEntity getService() {
    return service;
  }
  
  public void setService(ServiceEntity service) {
    this.service = service;
  }
  
  public GradeEntity getGrade() {
    return grade;
  }
  
  public void setGrade(GradeEntity grade) {
    this.grade = grade;
  }
  
  public JobEntity getJob() {
    return job;
  }
  
  public void setJob(JobEntity job) {
    this.job = job;
  }
  
  public String getAddress() {
    return address;
  }
  
  public void setAddress(String address) {
    this.address = address;
  }
  
  public int getId() {
    return id;
  }
  
  public void setId(int id) {
    this.id = id;
  }
  
  @Override
  public String toString() {
    return "AgentEntity{" +
      "name='" + name + '\'' +
      ", lastName='" + lastName + '\'' +
      ", firstName='" + firstName + '\'' +
      '}';
  }
}
