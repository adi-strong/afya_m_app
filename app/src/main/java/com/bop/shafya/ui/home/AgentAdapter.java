package com.bop.shafya.ui.home;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.bop.shafya.entity.AgentEntity;

import java.util.ArrayList;
import java.util.List;

public class AgentAdapter extends ArrayAdapter<AgentEntity> {
  private final List<AgentEntity> originalAgents;
  private final List<AgentEntity> filteredAgents;
  
  public AgentAdapter(Context context, List<AgentEntity> agents) {
    super(context, android.R.layout.simple_dropdown_item_1line, agents);
    this.originalAgents = new ArrayList<>(agents);
    this.filteredAgents = new ArrayList<>(agents);
  }
  
  @Override
  public int getCount() {
    return filteredAgents.size();
  }
  
  @Override
  public AgentEntity getItem(int position) {
    return filteredAgents.get(position);
  }
  
  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    if (convertView == null) {
      convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_dropdown_item_1line, parent, false);
    }
    TextView textView = convertView.findViewById(android.R.id.text1);
    AgentEntity agent = getItem(position);
    
    // Construire le nom complet avec ID
    if (agent != null) {
      String fullNameWithId = buildFullName(agent) + " - " + agent.getId();
      textView.setText(fullNameWithId);
    }
    
    return convertView;
  }
  
  @Override
  public Filter getFilter() {
    return new Filter() {
      @Override
      protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();
        List<AgentEntity> filteredList = new ArrayList<>();
        
        if (constraint == null || constraint.length() == 0) {
          filteredList.addAll(originalAgents);
        } else {
          String filterPattern = constraint.toString().toLowerCase().trim();
          
          for (AgentEntity agent : originalAgents) {
            String fullName = buildFullName(agent).toLowerCase();
            if (fullName.contains(filterPattern)) {
              filteredList.add(agent);
            }
          }
        }
        
        results.values = filteredList;
        results.count = filteredList.size();
        return results;
      }
      
      @Override
      protected void publishResults(CharSequence constraint, FilterResults results) {
        filteredAgents.clear();
        filteredAgents.addAll((List<AgentEntity>) results.values);
        notifyDataSetChanged();
      }
    };
  }
  
  private String buildFullName(AgentEntity agent) {
    StringBuilder fullName = new StringBuilder();
    
    if (agent.getFirstName() != null && !agent.getFirstName().isEmpty()) {
      fullName.append(agent.getFirstName()).append(" ");
    }
    
    if (agent.getName() != null && !agent.getName().isEmpty()) {
      fullName.append(agent.getName()).append(" ");
    }
    
    if (agent.getLastName() != null && !agent.getLastName().isEmpty()) {
      fullName.append(agent.getLastName());
    }
    
    return fullName.toString().toUpperCase().trim(); // Enlever les espaces superflus
  }
}
