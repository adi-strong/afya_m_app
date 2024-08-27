package com.bop.shafya.ui.slideshow;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bop.shafya.R;
import com.bop.shafya.entity.AgentEntity;
import com.bop.shafya.entity.CFileEntity;
import com.bop.shafya.entity.PatientEntity;
import com.bop.shafya.repository.AgentRepository;
import com.bop.shafya.repository.CFileRepository;
import com.bop.shafya.repository.ConsultationRepository;
import com.bop.shafya.repository.PatientRepository;
import com.bop.shafya.ui.home.AgentAdapter;
import com.bop.shafya.ui.home.PatientAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NewFileFragment extends Fragment {
  
  private FileViewModel viewModel;
  private String keyword;
  private PatientAdapter patientAdapter;
  private List<PatientEntity> patientList;
  private ProgressBar progressBar;
  private ProgressBar progressBar2;
  private View cardViewPatientsTable;
  private RecyclerView recyclerView;
  private View cardViewTable;
  private AutoCompleteTextView doctorAutoCompleteTextView;
  private AutoCompleteTextView cFileAutoCompleteTextView;
  private List<AgentEntity> agentList;
  private List<CFileEntity> cFileList;
  private AgentAdapter agentAdapter;
  private CFileAdapter cFileAdapter;
  private View formCardView;
  private String patientId;
  
  @Override
  public View onCreateView(
    @NonNull LayoutInflater inflater,
    ViewGroup container,
    Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_new_file, container, false);
    
    // Initialisation des vues
    recyclerView = view.findViewById(R.id.new_file_patient_list);
    progressBar = view.findViewById(R.id.new_file_progress_bar);
    progressBar2 = view.findViewById(R.id.progress_bar_2);
    cardViewPatientsTable = view.findViewById(R.id.new_file_card_view_patients_table);
    cardViewTable = view.findViewById(R.id.new_file_card_view_table);
    formCardView = view.findViewById(R.id.new_file_form_card_view);
    doctorAutoCompleteTextView = view.findViewById(R.id.new_file_doctor);
    cFileAutoCompleteTextView = view.findViewById(R.id.new_file_c_file);
    
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    
    patientList = new ArrayList<>();
    patientAdapter = new PatientAdapter(patientList, this::onPatientClick);
    recyclerView.setAdapter(patientAdapter);
    
    viewModel = new ViewModelProvider(this).get(FileViewModel.class);
    viewModel.setPatientRepository(new PatientRepository(getContext()));
    
    // Observer les données des patients
    viewModel.getPatientsLiveData().observe(getViewLifecycleOwner(), this::updatePatientList);
    viewModel.getErrorLiveData().observe(getViewLifecycleOwner(), error -> {
      // Gérer l'erreur ici
    });
    
    // Observer les données des agents
    viewModel.setAgentRepository(new AgentRepository(getContext()));
    viewModel.getAgentsLiveData().observe(getViewLifecycleOwner(), this::updateAgentList);
    viewModel.fetchAgents();
    
    // Observer les données des Fiches
    viewModel.setCFileRepository(new CFileRepository(getContext()));
    viewModel.getCFilesLiveData().observe(getViewLifecycleOwner(), this::updateCFileList);
    viewModel.fetchCFiles();
    
    // Utiliser View Binding pour accéder aux vues
    Bundle args = getArguments();
    if (args != null) {
      keyword = args.getString("keyword");
      onSearchPatients(keyword);
    }
    
    // Buttons events
    
    Button searchButton = view.findViewById(R.id.search_button_action);
    searchButton.setOnClickListener(v -> searchPatients());
    
    Button biometricSearchButton = view.findViewById(R.id.biometric_search_button_action);
    biometricSearchButton.setOnClickListener(v -> searchPatientsBiometrically());
    
    Button backButton = view.findViewById(R.id.back_button_action);
    backButton.setOnClickListener(v -> showPatientList());
    
    Button validRdvButton = view.findViewById(R.id.valid_rdv_button_action);
    validRdvButton.setOnClickListener(v -> onValidNewFile());
    
    // End Buttons events
    
    return view;
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
  
  private void updatePatientList(List<PatientEntity> patients) {
    if (patients != null) {
      progressBar.setVisibility(View.GONE); // Masquer le spinner de chargement
      recyclerView.setVisibility(View.VISIBLE); // Afficher la liste des patients
      patientList.clear();
      patientList.addAll(patients);
      patientAdapter.notifyDataSetChanged();
    }
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
    
    // applicant = patientFullName.toString().trim();
    
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
    // applicant = null;
    formCardView.setVisibility(View.GONE); // Masquer la carte du formulaire
    cardViewTable.setVisibility(View.GONE); // Masquer la carte des détails du patient
    cardViewPatientsTable.setVisibility(View.VISIBLE); // Afficher la carte des patients
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
        ((AutoCompleteTextView) getView().findViewById(R.id.new_file_doctor)).setText(fullNameWithId);
      }
    });
  }
  
  private void updateCFileList(List<CFileEntity> files) {
    cFileList = files;
    cFileAdapter = new CFileAdapter(getContext(), cFileList);
    cFileAutoCompleteTextView.setAdapter(cFileAdapter);
    
    // Gestion du clic sur un item
    cFileAutoCompleteTextView.setOnItemClickListener((parent, view, position, id) -> {
      CFileEntity selectedCFile = cFileAdapter.getItem(position);
      if (selectedCFile != null) {
        String fullNameWithId = buildCFileName(selectedCFile) + " - " + selectedCFile.getId();
        ((AutoCompleteTextView) getView().findViewById(R.id.new_file_c_file)).setText(fullNameWithId);
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
  
  private StringBuilder buildCFileName(CFileEntity cFile) {
    StringBuilder fullName = new StringBuilder();
    
    if (cFile.getName() != null && !cFile.getName().isEmpty()) {
      fullName.append(cFile.getName()).append(" ");
    }
    
    return new StringBuilder(fullName.toString().toUpperCase().trim()); // Enlever les espaces superflus
  }
  
  private void navigateToHomeFragment() {
    NavController navController = Navigation.findNavController(
      requireActivity(), R.id.nav_host_fragment_content_main);
    navController.popBackStack(R.id.nav_slideshow, false);
    setUiEnabled(true);
  }
  
  private void onValidNewFile() {
    String selectedAgentText = doctorAutoCompleteTextView.getText().toString().trim();
    String selectedCFileText = cFileAutoCompleteTextView.getText().toString().trim();
    String selectedAgentId = "", agentValue = null;
    String selectedCFileId = "", cFileValue = null;
    String patientValue = "/api/patients/"+patientId;
    
    Button backButton = getView().findViewById(R.id.back_button_action);
    Button validButton = getView().findViewById(R.id.valid_rdv_button_action);
    
    backButton.setVisibility(View.GONE);
    validButton.setVisibility(View.GONE);
    
    // Trouver l'ID dans le texte
    for (AgentEntity agent : agentList) {
      String fullNameWithId = buildFullName(agent) + " - " + agent.getId();
      if (selectedAgentText.equals(fullNameWithId)) {
        selectedAgentId = String.valueOf(agent.getId());
        break;
      }
    }
    
    // Trouver l'ID du CFile dans le texte
    for (CFileEntity file : cFileList) {
      String fullNameWithId = buildCFileName(file) + " - " + file.getId();
      if (selectedCFileText.equals(fullNameWithId)) {
        selectedCFileId = String.valueOf(file.getId());
        break;
      }
    }
    
    if (!selectedAgentId.isEmpty() && !selectedCFileId.isEmpty()) {
      agentValue = "/api/agents/"+selectedAgentId;
      cFileValue = "/api/c_files/"+selectedCFileId;
      FileRequest request = new FileRequest();
      request.setAgent(agentValue);
      request.setFile(cFileValue);
      request.setPatient(patientValue);
      
      String conclusion = ((EditText) getView().findViewById(R.id.conclusion)).getText().toString();
      String family = ((EditText) getView().findViewById(R.id.family)).getText().toString();
      String complement = ((EditText) getView().findViewById(R.id.complement)).getText().toString();
      String surgical = ((EditText) getView().findViewById(R.id.surgical)).getText().toString();
      String mentions = ((EditText) getView().findViewById(R.id.mentions)).getText().toString();
      String background = ((EditText) getView().findViewById(R.id.background)).getText().toString();
      
      String temperature = ((EditText) getView().findViewById(R.id.temperature)).getText().toString();
      String weight = ((EditText) getView().findViewById(R.id.weight)).getText().toString();
      String bloodTension = ((EditText) getView().findViewById(R.id.blood_tension)).getText().toString();
      String heartFrequency = ((EditText) getView().findViewById(R.id.heart_frequency)).getText().toString();
      String oxygenSaturation = ((EditText) getView().findViewById(R.id.oxygen_saturation)).getText().toString();
      String diagnostic = ((EditText) getView().findViewById(R.id.diagnostic)).getText().toString();
      String respiratoryRate = ((EditText) getView().findViewById(R.id.respiratory_rate)).getText().toString();
      
      HashMap<String, String> content = new HashMap<>();
      content.put("temperature", temperature);
      content.put("weight", weight);
      content.put("bloodPressure", bloodTension);
      content.put("cardiacFrequency", heartFrequency);
      content.put("diagnostic", diagnostic);
      content.put("oxygenSaturation", oxygenSaturation);
      content.put("respiratoryRate", respiratoryRate);
      request.setContent(content);
      
      String clinicalIntelligence = ((EditText) getView().findViewById(R.id.clinical_intelligence)).getText().toString();
      
      request.setConclusion(conclusion);
      request.setFamily(family);
      request.setComplement(complement);
      request.setSurgical(surgical);
      request.setParticularMentions(mentions);
      request.setBackground(background);
      request.setTotal("0");
      request.setTotalWithoutTax("0");
      request.setClinicAdvice(clinicalIntelligence);
      
      setUiEnabled(false);
      ProgressBar progressBar = getView().findViewById(R.id.progress_bar_2);
      progressBar.setVisibility(View.VISIBLE);
      
      ConsultationRepository repository = new ConsultationRepository(getContext());
      repository.postNewFile(request, new ConsultationRepository.PostNewFileCallback() {
        @Override
        public void onSuccess(String success) {
          Toast.makeText(
            getActivity(),
            "Fiche de consultation créée avec succès.",
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
          progressBar2.setVisibility(View.GONE);
          Toast.makeText(getActivity(), "Erreur : " + error, Toast.LENGTH_SHORT).show();
        }
      });
    } else {
      patientId = null;
      // applicant = null;
      Toast.makeText(getActivity(), "Aucun agent correspondant trouvé.", Toast.LENGTH_SHORT).show();
    }
    
  }
  
  private void setUiEnabled(Boolean enabled) {
    Button backButton = getView().findViewById(R.id.back_button_action);
    Button validButton = getView().findViewById(R.id.valid_rdv_button_action);
    
    doctorAutoCompleteTextView.setEnabled(enabled);
    cFileAutoCompleteTextView.setEnabled(enabled);
    backButton.setEnabled(enabled);
    validButton.setEnabled(enabled);
  }
  
}
