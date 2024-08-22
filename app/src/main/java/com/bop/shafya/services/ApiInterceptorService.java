package com.bop.shafya.services;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class ApiInterceptorService implements Interceptor {
 
 private final String authToken;
  
  public ApiInterceptorService(String authToken) {
    this.authToken = authToken;
  }
  
  @NonNull
  @Override
  public Response intercept(Chain chain) throws IOException {
    Request originalRequest = chain.request();
    Request.Builder requestBuilder = originalRequest.newBuilder()
      .header("Authorization", "Bearer "+authToken);
    
    Request request = requestBuilder.build();
    
    return chain.proceed(request);
  }
}
