package com.bop.shafya.services;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptorService implements Interceptor {
  
  private static Context context;
  
  public AuthInterceptorService() { }
  
  public AuthInterceptorService(Context context) { AuthInterceptorService.context = context; }
  
  @Override
  public Response intercept(Chain chain) throws IOException {
    Request originalRequest = chain.request();
    SharedPreferences sharedPreferences = context.getSharedPreferences(
      "MyPrefs",
      Context.MODE_PRIVATE
    );
    
    String token = sharedPreferences.getString("MyPrefs", "");
    Request requestWithAuth = originalRequest.newBuilder()
      .header("Authorization", "Bearer ")
      .build();
    
    return chain.proceed(requestWithAuth);
  }
  
}
