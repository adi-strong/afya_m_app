package com.bop.shafya.entity;

import java.util.ArrayList;
import java.util.List;

public class UserEntity {
  private String username, password, repeatPassword, email, fullName, phone, biometricData;
  private List<String> roles = new ArrayList<>();
  
  public UserEntity() { }
  
  public UserEntity(
    String username,
    String password,
    String email,
    String fullName,
    String phone,
    String biometricData,
    List<String> roles) {
    this.username = username;
    this.password = password;
    this.email = email;
    this.fullName = fullName;
    this.phone = phone;
    this.biometricData = biometricData;
    this.roles = roles;
  }
  
  public UserEntity(
    String username,
    String password,
    String email,
    String fullName,
    String phone,
    String biometricData) {
    this.username = username;
    this.password = password;
    this.email = email;
    this.fullName = fullName;
    this.phone = phone;
    this.biometricData = biometricData;
  }
  
  public String getUsername() {
    return username;
  }
  
  public void setUsername(String username) {
    this.username = username;
  }
  
  public String getPassword() {
    return password;
  }
  
  public void setPassword(String password) {
    this.password = password;
  }
  
  public String getRepeatPassword() {
    return repeatPassword;
  }
  
  public void setRepeatPassword(String repeatPassword) {
    this.repeatPassword = repeatPassword;
  }
  
  public String getEmail() {
    return email;
  }
  
  public void setEmail(String email) {
    this.email = email;
  }
  
  public String getFullName() {
    return fullName;
  }
  
  public void setFullName(String fullName) {
    this.fullName = fullName;
  }
  
  public String getPhone() {
    return phone;
  }
  
  public void setPhone(String phone) {
    this.phone = phone;
  }
  
  public String getBiometricData() {
    return biometricData;
  }
  
  public void setBiometricData(String biometricData) {
    this.biometricData = biometricData;
  }
  
  public List<String> getRoles() {
    return roles;
  }
  
  public void setRoles(List<String> roles) {
    this.roles = roles;
  }
}
