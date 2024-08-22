package com.bop.shafya.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bop.shafya.R;
import com.bop.shafya.entity.PatientEntity;

import java.util.ArrayList;
import java.util.List;

public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.PatientViewHolder> {
  
  private List<PatientEntity> patients;
  private final OnPatientClickListener onPatientClickListener;
  
  public PatientAdapter(List<PatientEntity> patients, OnPatientClickListener onPatientClickListener) {
    // Assurez-vous que la liste n'est pas null
    this.patients = (patients != null) ? patients : new ArrayList<>();
    this.onPatientClickListener = onPatientClickListener;
  }
  
  @NonNull
  @Override
  public PatientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_patient, parent, false);
    return new PatientViewHolder(view);
  }
  
  @Override
  public void onBindViewHolder(@NonNull PatientViewHolder holder, int position) {
    PatientEntity patient = patients.get(position);
    // Afficher le prénom et le nom en majuscules
    holder.nameTextView.setText(patient.getName().toUpperCase());
    holder.nameTextView.setText(patient.getName().toUpperCase());
    
    // Configurez l'image du patient si disponible
    // holder.patientImageView.setImageResource(...); // Exemple si vous avez une image à définir
    
    // Configurer le bouton pour afficher les détails
    holder.detailsButton.setOnClickListener(v -> {
      if (onPatientClickListener != null) {
        onPatientClickListener.onPatientClick(patient);
      }
    });
  }
  
  @Override
  public int getItemCount() {
    return patients.size(); // patients ne devrait jamais être null ici
  }
  
  // Interface pour gérer les clics sur les patients
  public interface OnPatientClickListener {
    void onPatientClick(PatientEntity patient);
  }
  
  static class PatientViewHolder extends RecyclerView.ViewHolder {
    final ImageView patientImageView;
    final TextView nameTextView;
    final Button detailsButton;
    
    PatientViewHolder(View itemView) {
      super(itemView);
      patientImageView = itemView.findViewById(R.id.patient_image);
      nameTextView = itemView.findViewById(R.id.patient_name);
      detailsButton = itemView.findViewById(R.id.patient_details_button);
    }
  }
}
