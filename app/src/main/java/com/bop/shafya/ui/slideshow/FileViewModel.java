package com.bop.shafya.ui.slideshow;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bop.shafya.entity.AgentEntity;
import com.bop.shafya.entity.CFileEntity;
import com.bop.shafya.entity.PatientEntity;
import com.bop.shafya.repository.AgentRepository;
import com.bop.shafya.repository.CFileRepository;
import com.bop.shafya.repository.PatientRepository;

import java.util.List;

public class FileViewModel extends ViewModel {
  
  private PatientRepository patientRepository;
  private AgentRepository agentRepository;
  private CFileRepository cFileRepository;
  private final MutableLiveData<List<PatientEntity>> patientsLiveData = new MutableLiveData<>();
  private final MutableLiveData<String> errorLiveData = new MutableLiveData<>();
  private final MutableLiveData<List<AgentEntity>> agentsLiveData = new MutableLiveData<>();
  private final MutableLiveData<List<CFileEntity>> cFilesLiveData = new MutableLiveData<>();
  
  // Méthode pour injecter la dépendance après la création du ViewModel
  public void setPatientRepository(PatientRepository patientRepository) {
    this.patientRepository = patientRepository;
  }
  
  public void setAgentRepository(AgentRepository agentRepository) {
    this.agentRepository = agentRepository;
  }
  
  public void setCFileRepository(CFileRepository cFileRepository) {
    this.cFileRepository = cFileRepository;
  }
  
  public MutableLiveData<List<PatientEntity>> getPatientsLiveData() { return patientsLiveData; }
  public MutableLiveData<String> getErrorLiveData() { return errorLiveData; }
  public MutableLiveData<List<AgentEntity>> getAgentsLiveData() {
    return agentsLiveData;
  }
  public MutableLiveData<List<CFileEntity>> getCFilesLiveData() { return cFilesLiveData; }
  
  public void searchPatient(String keyword) {
    if (patientRepository != null) {
      patientRepository.getSearchPatient(keyword, new PatientRepository.GetSearchPatientCallBack() {
        @Override
        public void onSuccess(List<PatientEntity> patients) {
          patientsLiveData.setValue(patients);
        }
        
        @Override
        public void onError(String error) {
          errorLiveData.setValue(error);
        }
      });
    }
  }
  
  public void fetchAgents() {
    if (agentRepository != null) {
      agentRepository.fetchAllAgents(new AgentRepository.SearchAllAgentsCallback() {
        @Override
        public void onSuccess(List<AgentEntity> agents) {
          if (agents != null) {
            Log.d("NewRdvViewModel", "Agents fetched Ok: " + agents.size());
            agentsLiveData.setValue(agents);
          } else {
            Log.e("NewRdvViewModel", "Liste des agents est nulle");
            errorLiveData.setValue("Liste des agents est nulle");
          }
        }
        
        @Override
        public void onError(String error) {
          Log.e("NewRdvViewModel", "Error fetching agents: " + error);
          errorLiveData.setValue(error);
        }
      });
    }
  }
  
  public void fetchCFiles() {
    if (cFileRepository != null) {
      cFileRepository.fetchAllCFiles(new CFileRepository.SearchAllCFilesCallback() {
        @Override
        public void onSuccess(List<CFileEntity> files) {
          if (files != null) {
            Log.d("NewRdvViewModel", "Files fetched : " + files.size());
            cFilesLiveData.setValue(files);
          } else {
            Log.e("NewRdvViewModel", "Liste des agents est nulle");
            errorLiveData.setValue("Liste de types fiches est nulle");
          }
        }
        
        @Override
        public void onError(String error) {
          Log.e("NewRdvViewModel", "Error fetching files: " + error);
          errorLiveData.setValue(error);
        }
      });
    }
  }
  
}
