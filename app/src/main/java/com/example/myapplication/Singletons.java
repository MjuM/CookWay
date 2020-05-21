package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.myapplication.data.CookApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Singletons {
    private static Gson gsonInstance;
    private static CookApi cookApiInstance;
    private static SharedPreferences sharedPreferencesInstance;
    public static Gson getGson(){
        if(gsonInstance == null){
            gsonInstance = new GsonBuilder()
                    .setLenient()
                    .create();
        }
        return gsonInstance;
    }
    private static final String BASE_URL = "https://raw.githubusercontent.com/MjuM/ProjectAndroid/master/";
    public static CookApi getPokeApi(){
        if(cookApiInstance == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(getGson()))
                    .build();

            cookApiInstance = retrofit.create(CookApi.class);

        }
        return cookApiInstance;
    }
    public static SharedPreferences getSharedPreferencesInstance(Context context){
        if(sharedPreferencesInstance == null){
            sharedPreferencesInstance = context.getSharedPreferences("app_Esiea", Context.MODE_PRIVATE);
        }
        return sharedPreferencesInstance;
    }
}
