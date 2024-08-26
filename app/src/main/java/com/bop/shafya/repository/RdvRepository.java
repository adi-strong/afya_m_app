package com.bop.shafya.repository;

import android.content.Context;
import android.content.SharedPreferences;

import com.bop.shafya.services.ApiClientService;
import com.bop.shafya.services.ApiServiceInterface;
import com.bop.shafya.ui.home.RdvRequest;
import com.bop.shafya.ui.home.RdvResponse;

import retrofit2.Call;
import retrofit2.Response;

public class RdvRepository {
  
  private final ApiServiceInterface apiServiceInterface;
  
  public RdvRepository(Context context) {
    SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
    String token = sharedPreferences.getString("jwt_token", "");
    apiServiceInterface = ApiClientService.getClient(token).create(ApiServiceInterface.class);
  }
  
  public interface PostNewRdvCallback {
    void onSuccess(String success);
    void onError(String error);
  }
  
  public void postNewRdv(RdvRequest rdvRequest, PostNewRdvCallback callback) {
    Call<RdvResponse> call = apiServiceInterface.postRdv(rdvRequest);
    
    call.enqueue(new retrofit2.Callback<RdvResponse>() {
      @Override
      public void onResponse(Call<RdvResponse> call, Response<RdvResponse> response) {
        if (response.isSuccessful() && response.body() != null) {
          // Appel réussi
          callback.onSuccess("Rendez-vous créé avec succès.");
        } else {
          // Erreur serveur ou réponse invalide
          callback.onError("Erreur serveur : " + response.message());
        }
      }
      
      @Override
      public void onFailure(Call<RdvResponse> call, Throwable t) {
        // Erreur de communication ou problème réseau
        callback.onError("Erreur de communication : " + t.getMessage());
      }
    });
  }
  
}
