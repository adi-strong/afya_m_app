package com.bop.shafya.data;

import java.util.ArrayList;
import java.util.List;

public class UserProfileData {

  private String id, username, fullName, phone, email, appId, paramId;
  private List<String> roles = new ArrayList<>();
  
  public UserProfileData() { }
  
  public UserProfileData(
    String id,
    String username,
    String fullName,
    String phone,
    String email,
    String appId,
    String paramId,
    List<String> roles) {
    this.id = id;
    this.username = username;
    this.fullName = fullName;
    this.phone = phone;
    this.email = email;
    this.appId = appId;
    this.paramId = paramId;
    this.roles = roles;
  }
  
  public String getUsername() {
    return username;
  }
  
  public void setUsername(String username) {
    this.username = username;
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
  
  public String getEmail() {
    return email;
  }
  
  public void setEmail(String email) {
    this.email = email;
  }
  
  public String getAppId() {
    return appId;
  }
  
  public void setAppId(String appId) {
    this.appId = appId;
  }
  
  public String getParamId() {
    return paramId;
  }
  
  public void setParamId(String paramId) {
    this.paramId = paramId;
  }
  
  public List<String> getRoles() {
    return roles;
  }
  
  public void setRoles(List<String> roles) {
    this.roles = roles;
  }
  
  public String getId() {
    return id;
  }
  
  public void setId(String id) {
    this.id = id;
  }
}
