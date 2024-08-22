package com.bop.shafya.services;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClientService {
  
  private static final String BASE_URL = "https://coapi.mdeservicesdrc.com";
  private static Retrofit retrofit = null;
  
  public static Retrofit getClient(String authToken) {
    OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
      .addInterceptor(new ApiInterceptorService(authToken))
      .build();
    
    if (retrofit == null) {
      retrofit = new Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build();
    }
    
    return retrofit;
  }
  
}
