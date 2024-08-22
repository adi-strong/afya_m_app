package com.bop.shafya.repository;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.bop.shafya.entity.PatientEntity;
import com.bop.shafya.services.ApiClientService;
import com.bop.shafya.services.ApiServiceInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PatientRepository {
  
  private final ApiServiceInterface apiServiceInterface;
  
  // Constructeur utilisant SharedPreferences pour obtenir le token
  public PatientRepository(Context context) {
    SharedPreferences sharedPreferences = context.getSharedPreferences(
      "MyPrefs",
      Context.MODE_PRIVATE
    );
    
    String token = sharedPreferences.getString("jwt_token", "");
    apiServiceInterface = ApiClientService.getClient(token).create(ApiServiceInterface.class);
  }
  
  // Constructeur utilisant directement ApiServiceInterface
  public PatientRepository(ApiServiceInterface apiServiceInterface) {
    this.apiServiceInterface = apiServiceInterface;
  }
  
  // Interface pour les callbacks
  public interface GetSearchPatientCallBack {
    void onSuccess(List<PatientEntity> patients);
    void onError(String error);
  }
  
  // Méthode pour obtenir des patients par mot-clé
  public void getSearchPatient(
    String keyword,
    GetSearchPatientCallBack callBack) {
    
    apiServiceInterface.getSearchPatient(keyword).enqueue(new Callback<List<PatientEntity>>() {
      @Override
      public void onResponse(
        @NonNull Call<List<PatientEntity>> call,
        @NonNull Response<List<PatientEntity>> response) {
        if (response.isSuccessful() && response.body() != null) {
          callBack.onSuccess(response.body()); // Utiliser la réponse obtenue de l'API
        } else {
          callBack.onError("Erreur de réponse : " + response.message());
        }
      }
      
      @Override
      public void onFailure(@NonNull Call<List<PatientEntity>> call, @NonNull Throwable t) {
        callBack.onError("Erreur de réseau : " + t.getMessage());
      }
    });
  }
}
