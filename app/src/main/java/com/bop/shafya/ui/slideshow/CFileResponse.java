package com.bop.shafya.ui.slideshow;

import com.bop.shafya.entity.CFileEntity;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CFileResponse {
  @SerializedName("hydra:member")
  private List<CFileEntity> hydraMember;
  
  public List<CFileEntity> getHydraMember() {
    return hydraMember;
  }
  
  public void setHydraMember(List<CFileEntity> hydraMember) {
    this.hydraMember = hydraMember;
  }
}
