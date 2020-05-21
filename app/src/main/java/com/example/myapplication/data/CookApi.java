package com.example.myapplication.data;

import com.example.myapplication.presentation.model.RestFood;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CookApi {
    @GET("dataFinal.json")
    Call<RestFood> getIngredientsResponse();
}