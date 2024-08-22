package com.bop.shafya.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bop.shafya.R;
import com.bop.shafya.entity.AgentEntity;
import com.bop.shafya.entity.PatientEntity;
import com.bop.shafya.repository.AgentRepository;
import com.bop.shafya.repository.PatientRepository;

import java.util.ArrayList;
import java.util.List;

public class CreateNewRdvFragment extends Fragment {
  
  private RecyclerView recyclerView;
  private PatientAdapter patientAdapter;
  private List<PatientEntity> patientList;
  private ProgressBar progressBar;
  private View cardViewPatientsTable;
  private View cardViewTable;
  private View formCardView;
  private NewRdvViewModel viewModel;
  private AutoCompleteTextView doctorAutoCompleteTextView;
  private AgentAdapter agentAdapter;
  private List<AgentEntity> agentList;
  private String keyword;
  
  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_create_new_rdv, container, false);
    
    // Initialisation des vues
    recyclerView = view.findViewById(R.id.patient_list);
    progressBar = view.findViewById(R.id.progress_bar);
    cardViewPatientsTable = view.findViewById(R.id.card_view_patients_table);
    cardViewTable = view.findViewById(R.id.card_view_table);
    formCardView = view.findViewById(R.id.form_card_view);
    doctorAutoCompleteTextView = view.findViewById(R.id.doctor);
    
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    
    patientList = new ArrayList<>();
    patientAdapter = new PatientAdapter(patientList, this::onPatientClick);
    recyclerView.setAdapter(patientAdapter);
    
    // Initialiser le ViewModel
    viewModel = new ViewModelProvider(this).get(NewRdvViewModel.class);
    viewModel.setPatientRepository(new PatientRepository(getContext()));
    viewModel.setAgentRepository(new AgentRepository(getContext()));
    
    // Observer les données des patients
    viewModel.getPatientsLiveData().observe(getViewLifecycleOwner(), this::updatePatientList);
    viewModel.getErrorLiveData().observe(getViewLifecycleOwner(), error -> {
      // Gérer l'erreur ici
    });
    
    // Observer les données des agents
    viewModel.getAgentsLiveData().observe(getViewLifecycleOwner(), this::updateAgentList);
    
    // Charger les agents
    viewModel.fetchAgents();
    
    Bundle args = getArguments();
    if (args != null) {
      keyword = args.getString("keyword");
      onSearchPatients(keyword);
    }
    
    Button searchButton = view.findViewById(R.id.search_button_action);
    searchButton.setOnClickListener(v -> searchPatients());
    
    Button biometricSearchButton = view.findViewById(R.id.biometric_search_button_action);
    biometricSearchButton.setOnClickListener(v -> searchPatientsBiometrically());
    
    Button backButton = view.findViewById(R.id.back_button_action);
    backButton.setOnClickListener(v -> showPatientList());
    
    Button validButton = view.findViewById(R.id.valid_rrdv_button_action);
    validButton.setOnClickListener(v -> validateRdv());
    
    return view;
  }
  
  private void updatePatientList(List<PatientEntity> patients) {
    if (patients != null) {
      progressBar.setVisibility(View.GONE); // Masquer le spinner de chargement
      recyclerView.setVisibility(View.VISIBLE); // Afficher la liste des patients
      patientList.clear();
      patientList.addAll(patients);
      patientAdapter.notifyDataSetChanged();
    }
  }
  
  private void updateAgentList(List<AgentEntity> agents) {
    if (agents != null) {
      Log.d("CreateNewRdvFragment", "Agents received: " + agents.size());
      agentList = agents;
      agentAdapter = new AgentAdapter(getContext(), agents);
      doctorAutoCompleteTextView.setAdapter(agentAdapter);
      doctorAutoCompleteTextView.setThreshold(1); // Afficher les suggestions après 1 caractère
    } else {
      Log.d("CreateNewRdvFragment", "No agents received");
    }
  }
  
  
  
  private void onSearchPatients(String search) {
    recyclerView.setVisibility(View.GONE); // Masquer la liste des patients
    progressBar.setVisibility(View.VISIBLE); // Afficher le spinner de chargement
    viewModel.searchPatient(search);
  }
  
  private void searchPatients() {
    String keyword = ((EditText) getView().findViewById(R.id.search_bar)).getText().toString();
    recyclerView.setVisibility(View.GONE); // Masquer la liste des patients
    progressBar.setVisibility(View.VISIBLE); // Afficher le spinner de chargement
    viewModel.searchPatient(keyword);
  }
  
  private void searchPatientsBiometrically() {
    // Implémentez ici la recherche biométrique
  }
  
  private void onPatientClick(PatientEntity patient) {
    // Afficher le formulaire
    cardViewPatientsTable.setVisibility(View.GONE); // Masquer la carte des patients
    formCardView.setVisibility(View.VISIBLE); // Afficher la carte du formulaire
    cardViewTable.setVisibility(View.VISIBLE); // Afficher la carte des détails du patient
    
    // Remplir les détails du patient dans le formulaire si nécessaire
    ((TextView) getView().findViewById(R.id.name_detail)).setText(patient.getName());
    ((TextView) getView().findViewById(R.id.lastname_detail)).setText(patient.getLastName());
    ((TextView) getView().findViewById(R.id.firstname_detail)).setText(patient.getFirstName());
    ((TextView) getView().findViewById(R.id.sex_detail)).setText(patient.getSex());
    ((TextView) getView().findViewById(R.id.age_detail)).setText(patient.getAge());
    
    ((Button) getView().findViewById(R.id.biometric_search_button_action)).setVisibility(View.GONE);
    ((Button) getView().findViewById(R.id.search_button_action)).setVisibility(View.GONE);
    ((EditText) getView().findViewById(R.id.search_bar)).setVisibility(View.GONE);
  }
  
  private void showPatientList() {
    // Revenir à la liste des patients
    formCardView.setVisibility(View.GONE); // Masquer la carte du formulaire
    cardViewTable.setVisibility(View.GONE); // Masquer la carte des détails du patient
    cardViewPatientsTable.setVisibility(View.VISIBLE); // Afficher la carte des patients
  }
  
  private void validateRdv() {
    // Implémentez ici la validation du rendez-vous
  }
}
