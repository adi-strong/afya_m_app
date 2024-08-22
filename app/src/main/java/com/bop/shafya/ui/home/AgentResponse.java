package com.bop.shafya.ui.home;

import com.bop.shafya.entity.AgentEntity;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AgentResponse {
  @SerializedName("hydra:member")
  private List<AgentEntity> hydraMember;
  
  public List<AgentEntity> getHydraMember() {
    return hydraMember;
  }
  
  public void setHydraMember(List<AgentEntity> hydraMember) {
    this.hydraMember = hydraMember;
  }
}
