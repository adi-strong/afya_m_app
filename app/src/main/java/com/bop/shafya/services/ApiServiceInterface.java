package com.bop.shafya.services;

import com.bop.shafya.entity.PatientEntity;
import com.bop.shafya.entity.UserEntity;
import com.bop.shafya.ui.home.AgentResponse;
import com.bop.shafya.ui.login.LoginRequest;
import com.bop.shafya.ui.login.LoginResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiServiceInterface {

  @Headers("Accept: application/json")
  @POST("auth")
  Call<LoginResponse> auth(@Body LoginRequest loginRequest);
  
  // API REQUESTS ************************************************************
  
  @Headers({
    "Accept: application/ld+json",
    "Content-Type: application/ld+json"
  })
  @POST("api/users")
  Call<UserEntity> registerNewAccount(@Body UserEntity userEntity);
  
  @GET("api/search_patient/{keyword}")
  Call<List<PatientEntity>> getSearchPatient(@Path("keyword") String keyword);
  
  @GET("/api/all_agents")
  Call<AgentResponse> getAllAgents();
  
  // END API REQUESTS ************************************************************

}
