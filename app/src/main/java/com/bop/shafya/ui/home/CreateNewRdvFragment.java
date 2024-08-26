package com.bop.shafya.ui.home;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bop.shafya.R;
import com.bop.shafya.entity.AgentEntity;
import com.bop.shafya.entity.PatientEntity;
import com.bop.shafya.repository.AgentRepository;
import com.bop.shafya.repository.PatientRepository;
import com.bop.shafya.repository.RdvRepository;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

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
  private String patientId;
  private String applicant;
  
  private TextInputEditText dateTimeInput;
  private Calendar calendar;
  private SimpleDateFormat dateFormat;
  
  @Nullable
  @Override
  public View onCreateView(
    @NonNull LayoutInflater inflater,
    @Nullable ViewGroup container,
    @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_create_new_rdv, container, false);
    
    // Initialisation des vues
    recyclerView = view.findViewById(R.id.patient_list);
    progressBar = view.findViewById(R.id.progress_bar);
    cardViewPatientsTable = view.findViewById(R.id.card_view_patients_table);
    cardViewTable = view.findViewById(R.id.card_view_table);
    formCardView = view.findViewById(R.id.form_card_view);
    doctorAutoCompleteTextView = view.findViewById(R.id.doctor);
    dateTimeInput = view.findViewById(R.id.at);
    calendar = Calendar.getInstance();
    dateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm", Locale.getDefault());
    
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    
    patientList = new ArrayList<>();
    patientAdapter = new PatientAdapter(patientList, this::onPatientClick);
    recyclerView.setAdapter(patientAdapter);
    
    // Initialiser le ViewModel
    viewModel = new ViewModelProvider(this).get(NewRdvViewModel.class);
    viewModel.setPatientRepository(new PatientRepository(getContext()));
    viewModel.setAgentRepository(new AgentRepository(getContext()));
    
    viewModel.getAgentsLiveData().observe(getViewLifecycleOwner(), this::updateAgentList);
    viewModel.fetchAgents();
    
    // Observer les données des patients
    viewModel.getPatientsLiveData().observe(getViewLifecycleOwner(), this::updatePatientList);
    viewModel.getErrorLiveData().observe(getViewLifecycleOwner(), error -> {
      // Gérer l'erreur ici
    });
    
    Bundle args = getArguments();
    if (args != null) {
      keyword = args.getString("keyword");
      onSearchPatients(keyword);
    }
    
    dateTimeInput.setOnClickListener(v -> showDateTimePicker());
    
    Button searchButton = view.findViewById(R.id.search_button_action);
    searchButton.setOnClickListener(v -> searchPatients());
    
    Button biometricSearchButton = view.findViewById(R.id.biometric_search_button_action);
    biometricSearchButton.setOnClickListener(v -> searchPatientsBiometrically());
    
    Button backButton = view.findViewById(R.id.back_button_action);
    backButton.setOnClickListener(v -> showPatientList());
    
    Button validButton = view.findViewById(R.id.valid_rdv_button_action);
    validButton.setOnClickListener(v -> validateRdv());
    
    return view;
  }
  
  private void showDateTimePicker() {
    // Affiche le sélecteur de date
    DatePickerDialog datePickerDialog = new DatePickerDialog(
      getContext(),
      (view, year, month, dayOfMonth) -> {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        showTimePicker(); // Affiche ensuite le sélecteur d'heure
      },
      calendar.get(Calendar.YEAR),
      calendar.get(Calendar.MONTH),
      calendar.get(Calendar.DAY_OF_MONTH)
    );
    
    datePickerDialog.show();
  }
  
  private void showTimePicker() {
    // Affiche le sélecteur d'heure
    TimePickerDialog timePickerDialog = new TimePickerDialog(
      getContext(),
      (view, hourOfDay, minute) -> {
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        dateTimeInput.setText(dateFormat.format(calendar.getTime()));
      },
      calendar.get(Calendar.HOUR_OF_DAY),
      calendar.get(Calendar.MINUTE),
      true // Format 24 heures
    );
    
    timePickerDialog.show();
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
    agentList = agents;
    agentAdapter = new AgentAdapter(getContext(), agentList);
    doctorAutoCompleteTextView.setAdapter(agentAdapter);
    
    // Gestion du clic sur un item
    doctorAutoCompleteTextView.setOnItemClickListener((parent, view, position, id) -> {
      AgentEntity selectedAgent = agentAdapter.getItem(position);
      if (selectedAgent != null) {
        String fullNameWithId = buildFullName(selectedAgent) + " - " + selectedAgent.getId();
        ((AutoCompleteTextView) getView().findViewById(R.id.doctor)).setText(fullNameWithId);
      }
    });
  }
  
  private StringBuilder buildFullName(AgentEntity agent) {
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
    
    return new StringBuilder(fullName.toString().toUpperCase().trim()); // Enlever les espaces superflus
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
    
    patientId = patient.getId();
    StringBuilder patientFullName = new StringBuilder();
    if (patient.getName() != null && !patient.getName().isEmpty()) {
      patientFullName.append(patient.getName()).append(" ");
    }
    if (patient.getLastName() != null && !patient.getLastName().isEmpty()) {
      patientFullName.append(patient.getLastName()).append(" ");
    }
    if (patient.getFirstName() != null && !patient.getFirstName().isEmpty()) {
      patientFullName.append(patient.getFirstName()).append(" ");
    }
    
    applicant = patientFullName.toString().trim();
    
    // Remplir les détails du patient dans le formulaire si nécessaire
    ((TextView) getView().findViewById(R.id.id_detail)).setText(patient.getId());
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
    patientId = null;
    applicant = null;
    formCardView.setVisibility(View.GONE); // Masquer la carte du formulaire
    cardViewTable.setVisibility(View.GONE); // Masquer la carte des détails du patient
    cardViewPatientsTable.setVisibility(View.VISIBLE); // Afficher la carte des patients
  }
  
  private void validateRdv() {
    String selectedText = doctorAutoCompleteTextView.getText().toString().trim();
    String selectedId = "", agentValue = null;
    String selectedDateTime = dateTimeInput.getText().toString().trim();
    
    Button backButton = getView().findViewById(R.id.back_button_action);
    Button validButton = getView().findViewById(R.id.valid_rdv_button_action);
    
    backButton.setVisibility(View.GONE);
    validButton.setVisibility(View.GONE);
    
    // Trouver l'ID dans le texte
    for (AgentEntity agent : agentList) {
      String fullNameWithId = buildFullName(agent) + " - " + agent.getId();
      if (selectedText.equals(fullNameWithId)) {
        selectedId = String.valueOf(agent.getId());
        break;
      }
    }
    
    if (!selectedId.isEmpty()) {
      agentValue = "/api/agents/"+selectedId;
      RdvRequest rdvRequest = new RdvRequest();
      rdvRequest.setApplicant(applicant);
      rdvRequest.setPatient("/api/patients/"+patientId);
      rdvRequest.setAt(selectedDateTime);
      rdvRequest.setAgent(agentValue);
      rdvRequest.setReason(((EditText) getView().findViewById(R.id.reason)).getText().toString());
      
      if (selectedText.isEmpty() || selectedDateTime.isEmpty()) {
        Toast.makeText(getActivity(), "Veuillez remplir tous les champs.", Toast.LENGTH_SHORT).show();
        return;
      }
      
      setUiEnabled(false);
      ProgressBar progressBar = getView().findViewById(R.id.progress_bar_2);
      progressBar.setVisibility(View.VISIBLE);
      
      RdvRepository rdvRepository = new RdvRepository(getContext());
      rdvRepository.postNewRdv(rdvRequest, new RdvRepository.PostNewRdvCallback() {
        @Override
        public void onSuccess(String success) {
          Toast.makeText(
            getActivity(),
            "Fiche de réception créée avec succès.",
            Toast.LENGTH_SHORT).show();
          progressBar.setVisibility(View.GONE);
          backButton.setVisibility(View.VISIBLE);
          validButton.setVisibility(View.VISIBLE);
          navigateToHomeFragment();
        }
        
        @Override
        public void onError(String error) {
          backButton.setVisibility(View.VISIBLE);
          validButton.setVisibility(View.VISIBLE);
          Toast.makeText(getActivity(), "Erreur : " + error, Toast.LENGTH_SHORT).show();
        }
      });
    } else {
      patientId = null;
      applicant = null;
      Toast.makeText(getActivity(), "Aucun agent correspondant trouvé.", Toast.LENGTH_SHORT).show();
    }
  }
  
  private void navigateToHomeFragment() {
    NavController navController = Navigation.findNavController(
      requireActivity(), R.id.nav_host_fragment_content_main);
    navController.popBackStack(R.id.nav_home, false);
    setUiEnabled(true);
  }
  
  private void setUiEnabled(Boolean enabled) {
    Button backButton = getView().findViewById(R.id.back_button_action);
    Button validButton = getView().findViewById(R.id.valid_rdv_button_action);
    
    doctorAutoCompleteTextView.setEnabled(enabled);
    dateTimeInput.setEnabled(enabled);
    backButton.setEnabled(enabled);
    validButton.setEnabled(enabled);
  }
}
