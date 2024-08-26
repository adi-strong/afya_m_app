package com.bop.shafya.ui.slideshow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.bop.shafya.entity.AgentEntity;
import com.bop.shafya.entity.CFileEntity;

import java.util.ArrayList;
import java.util.List;

public class CFileAdapter extends ArrayAdapter<CFileEntity> {
  private final List<CFileEntity> originalFiles;
  private final List<CFileEntity> filteredFiles;
  
  public CFileAdapter(Context context, List<CFileEntity> files) {
    super(context, android.R.layout.simple_dropdown_item_1line, files);
    this.originalFiles = new ArrayList<>(files);
    this.filteredFiles = new ArrayList<>(files);
  }
  
  @Override
  public int getCount() {
    return filteredFiles.size();
  }
  
  @Override
  public CFileEntity getItem(int position) {
    return filteredFiles.get(position);
  }
  
  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    if (convertView == null) {
      convertView = LayoutInflater.from(getContext()).inflate(
        android.R.layout.simple_dropdown_item_1line, parent, false);
    }
    TextView textView = convertView.findViewById(android.R.id.text1);
    CFileEntity cFile = getItem(position);
    
    // Construire le nom complet avec ID
    if (cFile != null) {
      String fullNameWithId = buildFullName(cFile) + " - " + cFile.getId();
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
        List<CFileEntity> filteredList = new ArrayList<>();
        
        if (constraint == null || constraint.length() == 0) {
          filteredList.addAll(originalFiles);
        } else {
          String filterPattern = constraint.toString().toLowerCase().trim();
          
          for (CFileEntity file : originalFiles) {
            String fullName = buildFullName(file).toLowerCase();
            if (fullName.contains(filterPattern)) {
              filteredList.add(file);
            }
          }
        }
        
        results.values = filteredList;
        results.count = filteredList.size();
        return results;
      }
      
      @Override
      protected void publishResults(CharSequence constraint, FilterResults results) {
        filteredFiles.clear();
        filteredFiles.addAll((List<CFileEntity>) results.values);
        notifyDataSetChanged();
      }
    };
  }
  
  private String buildFullName(CFileEntity cFile) {
    StringBuilder fullName = new StringBuilder();
    
    if (cFile.getName() != null && !cFile.getName().isEmpty()) {
      fullName.append(cFile.getName()).append(" ");
    }
    
    return fullName.toString().toUpperCase().trim(); // Enlever les espaces superflus
  }
}
