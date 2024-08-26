package com.bop.shafya.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.bop.shafya.services.ApiClientService;
import com.bop.shafya.services.ApiServiceInterface;
import com.bop.shafya.ui.slideshow.FileRequest;
import com.bop.shafya.ui.slideshow.FileResponse;

import retrofit2.Call;
import retrofit2.Response;

public class ConsultationRepository {
  
  private final ApiServiceInterface apiServiceInterface;
  
  public ConsultationRepository(Context context) {
    SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
    String token = sharedPreferences.getString("jwt_token", "");
    apiServiceInterface = ApiClientService.getClient(token).create(ApiServiceInterface.class);
  }
  
  public interface PostNewFileCallback {
    void onSuccess(String success);
    void onError(String error);
  }
  
  public void postNewFile(FileRequest fileRequest, ConsultationRepository.PostNewFileCallback callback) {
    Call<FileResponse> call = apiServiceInterface.postNewFile(fileRequest);
    
    call.enqueue(new retrofit2.Callback<FileResponse>() {
      @Override
      public void onResponse(Call<FileResponse> call, Response<FileResponse> response) {
        if (response.isSuccessful() && response.body() != null) {
          // Appel réussi
          callback.onSuccess("Rendez-vous créé avec succès.");
        } else {
          // Erreur serveur ou réponse invalide
          callback.onError("Erreur serveur : " + response.message());
          Log.d("Erreur", "Consultation's Error : " + response.message());
        }
      }
      
      @Override
      public void onFailure(Call<FileResponse> call, Throwable t) {
        // Erreur de communication ou problème réseau
        callback.onError("Erreur de communication : " + t.getMessage());
        Log.d("Erreur", "Consultation's Error : " + t.getMessage());
      }
    });
  }
  
}
