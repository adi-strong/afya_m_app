package com.bop.shafya.services;

import android.content.Context;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginRetrofitClientService {
  
  private static Retrofit retrofit = null;
  private static Context context;
  private static final String BASE_URL = "https://coapi.mdeservicesdrc.com";
  
  public static Retrofit getInstance(Context ctx) {
    if (retrofit == null) {
      context = ctx;
      OkHttpClient client = new OkHttpClient().newBuilder()
        .addInterceptor(new AuthInterceptorService(context))
        .build();
      
      retrofit = new Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build();
    }
    
    return retrofit;
  }
  
  public static String getBaseUrl() {
    return BASE_URL;
  }
}
