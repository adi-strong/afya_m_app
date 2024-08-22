package com.bop.shafya.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import com.bop.shafya.entity.AgentEntity;
import com.bop.shafya.services.ApiClientService;
import com.bop.shafya.services.ApiServiceInterface;
import com.bop.shafya.ui.home.AgentResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgentRepository {
  private final ApiServiceInterface apiServiceInterface;
  
  public AgentRepository(Context context) {
    SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
    String token = sharedPreferences.getString("jwt_token", "");
    apiServiceInterface = ApiClientService.getClient(token).create(ApiServiceInterface.class);
  }
  
  public interface SearchAllAgentsCallback {
    void onSuccess(List<AgentEntity> agents);
    void onError(String error);
  }
  
  public void fetchAllAgents(SearchAllAgentsCallback callback) {
    apiServiceInterface.getAllAgents().enqueue(new Callback<AgentResponse>() {
      @Override
      public void onResponse(
        @NonNull Call<AgentResponse> call,
        @NonNull Response<AgentResponse> response) {
        if (response.isSuccessful() && response.body() != null) {
          List<AgentEntity> agents = response.body().getHydraMember();
          if (agents != null && !agents.isEmpty()) {
            callback.onSuccess(agents);
          } else {
            callback.onError("Erreur : Liste des agents vide");
          }
        } else {
          callback.onError("Erreur de réponse : " + response.message());
        }
      }
      
      @Override
      public void onFailure(@NonNull Call<AgentResponse> call, @NonNull Throwable t) {
        callback.onError("Erreur de réseau : " + t.getMessage());
      }
    });
  }
  
  
}
