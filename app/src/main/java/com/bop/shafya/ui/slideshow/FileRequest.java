package com.bop.shafya.ui.slideshow;

import java.util.HashMap;

public class FileRequest {
  
  private String background, particularMentions, surgical, family, complement, conclusion;
  private String agent;
  private String file;
  private String patient;
  private String total;
  
  public String getClinicAdvice() {
    return clinicAdvice;
  }
  
  public void setClinicAdvice(String clinicAdvice) {
    this.clinicAdvice = clinicAdvice;
  }
  
  private String clinicAdvice;
  private HashMap<String, String> content = new HashMap<>();
  
  public HashMap<String, String> getContent() {
    return content;
  }
  
  public void setContent(HashMap<String, String> content) {
    this.content = content;
  }
  
  public String getTotalWithoutTax() {
    return totalWithoutTax;
  }
  
  public void setTotalWithoutTax(String totalWithoutTax) {
    this.totalWithoutTax = totalWithoutTax;
  }
  
  public String getTotal() {
    return total;
  }
  
  public void setTotal(String total) {
    this.total = total;
  }
  
  private String totalWithoutTax;
  
  public String getPatient() {
    return patient;
  }
  
  public void setPatient(String patient) {
    this.patient = patient;
  }
  
  public String getFile() {
    return file;
  }
  
  public void setFile(String file) {
    this.file = file;
  }
  
  public String getAgent() {
    return agent;
  }
  
  public void setAgent(String agent) {
    this.agent = agent;
  }
  
  public String getParticularMentions() {
    return particularMentions;
  }
  
  public void setParticularMentions(String particularMentions) {
    this.particularMentions = particularMentions;
  }
  
  public String getSurgical() {
    return surgical;
  }
  
  public void setSurgical(String surgical) {
    this.surgical = surgical;
  }
  
  public String getFamily() {
    return family;
  }
  
  public void setFamily(String family) {
    this.family = family;
  }
  
  public String getComplement() {
    return complement;
  }
  
  public void setComplement(String complement) {
    this.complement = complement;
  }
  
  public String getConclusion() {
    return conclusion;
  }
  
  public void setConclusion(String conclusion) {
    this.conclusion = conclusion;
  }
  
  public String getBackground() {
    return background;
  }
  
  public void setBackground(String background) {
    this.background = background;
  }
  
}
