package com.bop.shafya.ui.home;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import com.bop.shafya.entity.AgentEntity;

import java.util.ArrayList;
import java.util.List;

public class AgentAdapter extends ArrayAdapter<AgentEntity> implements Filterable {
  
  private List<AgentEntity> originalAgents;
  private List<AgentEntity> filteredAgents;
  
  public AgentAdapter(Context context, List<AgentEntity> agents) {
    super(context, android.R.layout.simple_dropdown_item_1line, agents);
    this.originalAgents = new ArrayList<>(agents);
    this.filteredAgents = new ArrayList<>(agents);
  }
  
  public CharSequence getDisplayValue(int position) {
    AgentEntity agent = getItem(position);
    return agent != null ? agent.getName() : "";
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
            if (agent.getName().toLowerCase().contains(filterPattern)) {
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
        @SuppressWarnings("unchecked")
        List<AgentEntity> filteredList = (List<AgentEntity>) results.values;
        clear();
        if (filteredList != null) {
          addAll(filteredList);
        }
        notifyDataSetChanged();
      }
    };
  }
}
