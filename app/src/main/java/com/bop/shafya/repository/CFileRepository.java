package com.bop.shafya.repository;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.bop.shafya.entity.CFileEntity;
import com.bop.shafya.services.ApiClientService;
import com.bop.shafya.services.ApiServiceInterface;
import com.bop.shafya.ui.slideshow.CFileResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CFileRepository {
  
  private final ApiServiceInterface apiServiceInterface;
  
  public CFileRepository(Context context) {
    SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
    String token = sharedPreferences.getString("jwt_token", "");
    apiServiceInterface = ApiClientService.getClient(token).create(ApiServiceInterface.class);
  }
  
  public interface SearchAllCFilesCallback {
    void onSuccess(List<CFileEntity> agents);
    void onError(String error);
  }
  
  public void fetchAllCFiles(CFileRepository.SearchAllCFilesCallback callback) {
    apiServiceInterface.getAllCFiles().enqueue(new Callback<CFileResponse>() {
      @Override
      public void onResponse(
        @NonNull Call<CFileResponse> call,
        @NonNull Response<CFileResponse> response) {
        if (response.isSuccessful() && response.body() != null) {
          List<CFileEntity> files = response.body().getHydraMember();
          if (files != null && !files.isEmpty()) {
            callback.onSuccess(files);
          } else {
            callback.onError("Erreur : Liste des agents vide");
          }
        } else {
          callback.onError("Erreur de réponse : " + response.message());
        }
      }
      
      @Override
      public void onFailure(@NonNull Call<CFileResponse> call, @NonNull Throwable t) {
        callback.onError("Erreur de réseau : " + t.getMessage());
      }
    });
  }
  
}
