package com.bop.shafya.entity;

import com.google.gson.annotations.SerializedName;

public class ConsultationEntity {
  
  @SerializedName("id")
  private String id;
  
  @SerializedName("background")
  private String background;
  
  @SerializedName("particularMentions")
  private String particularMentions;
  
  @SerializedName("surgical")
  private String surgical;
  
  @SerializedName("family")
  private String family;
  
  @SerializedName("complement")
  private String complement;
  
  @SerializedName("conclusion")
  private String conclusion;
  
  @SerializedName("file")
  private CFileEntity file;
  
  public String getId() {
    return id;
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
  
  public CFileEntity getFile() {
    return file;
  }
  
  public void setFile(CFileEntity file) {
    this.file = file;
  }
  
}
