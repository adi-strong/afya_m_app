package com.bop.shafya.ui.login;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.bop.shafya.services.ApiServiceInterface;
import com.bop.shafya.services.LoginRetrofitClientService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends AndroidViewModel {
  
  private final MutableLiveData<Boolean> loginSuccess = new MutableLiveData<>();
  private final MutableLiveData<String> errorMessage = new MutableLiveData<>();
  private Context context;
  
  public LoginViewModel(@NonNull Application application) {
    super(application);
    this.context = application.getApplicationContext();
  }
  
  public MutableLiveData<Boolean> getLoginSuccess() {
    return loginSuccess;
  }
  
  public MutableLiveData<String> getErrorMessage() {
    return errorMessage;
  }
  
  public void login(String username, String password) {
    ApiServiceInterface apiService = LoginRetrofitClientService
      .getInstance(context)
      .create(ApiServiceInterface.class);
    
    LoginRequest loginRequest = new LoginRequest(username, password);
    Call<LoginResponse> callResponse = apiService.auth(loginRequest);
    
    callResponse.enqueue(new Callback<LoginResponse>() {
      @Override
      public void onResponse(
        @NonNull Call<LoginResponse> call,
        @NonNull Response<LoginResponse> response) {
        if (response.isSuccessful() && response.body() != null) {
          LoginResponse loginResponse = response.body();
          if (loginResponse.getToken() != null) {
            context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
              .edit()
              .putString("jwt_token", loginResponse.getToken())
              .apply();
            
            loginSuccess.setValue(true);
          }
          else { errorMessage.setValue("No token received."); }
        }
        else { errorMessage.setValue("Login failed.\nCode : "+response.code()); }
      }
      
      @Override
      public void onFailure(Call<LoginResponse> call, Throwable t) {
        errorMessage.setValue(t.getMessage());
      }
    });
  }
}
