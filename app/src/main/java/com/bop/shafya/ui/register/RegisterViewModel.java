package com.bop.shafya.ui.register;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.bop.shafya.entity.UserEntity;
import com.bop.shafya.services.ApiServiceInterface;
import com.bop.shafya.services.LoginRetrofitClientService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterViewModel extends AndroidViewModel {
  
  private final MutableLiveData<Boolean> registerSuccess = new MutableLiveData<>();
  private final MutableLiveData<String> errorMessage = new MutableLiveData<>();
  private Context context;
  
  public RegisterViewModel(@NonNull Application application) {
    super(application);
    this.context = application.getApplicationContext();
  }
  
  public MutableLiveData<Boolean> getRegisterSuccess() {
    return registerSuccess;
  }
  
  public MutableLiveData<String> getErrorMessage() {
    return errorMessage;
  }
  
  public void register(
    String username,
    String password,
    String phone,
    String email,
    String fullName,
    String biometricData
  ) {
    ApiServiceInterface apiService = LoginRetrofitClientService
      .getInstance(context)
      .create(ApiServiceInterface.class);
    
    UserEntity user = new UserEntity(username, password, email, fullName, phone, biometricData);
    Call<UserEntity> callResponse = apiService.registerNewAccount(user);
    
    callResponse.enqueue(new Callback<UserEntity>() {
      @Override
      public void onResponse(Call<UserEntity> call, Response<UserEntity> response) {
        if (response.isSuccessful() && response.body() != null) {
          registerSuccess.setValue(true);
        } else { errorMessage.setValue("An error occured : "+response.code()); }
      }
      
      @Override
      public void onFailure(Call<UserEntity> call, Throwable t) {
        errorMessage.setValue(t.getMessage());
      }
    });
  }
}
