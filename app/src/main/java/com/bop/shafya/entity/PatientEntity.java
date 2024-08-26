package com.bop.shafya.entity;

import com.google.gson.annotations.SerializedName;

public class PatientEntity {
  
  @SerializedName("id")
  private String id;
  
  public String getId() {
    return id;
  }
  
  public void setId(String id) {
    this.id = id;
  }
  
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
  
  @SerializedName("bornPlace")
  private String bornPlace;
  
  @SerializedName("bornAt")
  private String bornAt;
  
  @SerializedName("nationality")
  private String nationality;
  
  @SerializedName("phone")
  private String phone;
  
  @SerializedName("email")
  private String email;
  
  @SerializedName("father")
  private String father;
  
  @SerializedName("mother")
  private String mother;
  
  @SerializedName("address")
  private String address;
  
  @SerializedName("age")
  private String age;
  
  public String getAge() {
    return age;
  }
  
  public void setAge(String age) {
    this.age = age;
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
  
  public String getBornPlace() {
    return bornPlace;
  }
  
  public void setBornPlace(String bornPlace) {
    this.bornPlace = bornPlace;
  }
  
  public String getBornAt() {
    return bornAt;
  }
  
  public void setBornAt(String bornAt) {
    this.bornAt = bornAt;
  }
  
  public String getNationality() {
    return nationality;
  }
  
  public void setNationality(String nationality) {
    this.nationality = nationality;
  }
  
  public String getPhone() {
    return phone;
  }
  
  public void setPhone(String phone) {
    this.phone = phone;
  }
  
  public String getEmail() {
    return email;
  }
  
  public void setEmail(String email) {
    this.email = email;
  }
  
  public String getFather() {
    return father;
  }
  
  public void setFather(String father) {
    this.father = father;
  }
  
  public String getMother() {
    return mother;
  }
  
  public void setMother(String mother) {
    this.mother = mother;
  }
  
  public String getAddress() {
    return address;
  }
  
  public void setAddress(String address) {
    this.address = address;
  }
  
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
}
