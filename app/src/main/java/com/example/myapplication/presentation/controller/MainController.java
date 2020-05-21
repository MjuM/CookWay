package com.example.myapplication.presentation.controller;

import android.content.SharedPreferences;

import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.myapplication.R;
import com.example.myapplication.Singletons;
import com.example.myapplication.presentation.model.Food;
import com.example.myapplication.presentation.model.RestFood;
import com.example.myapplication.presentation.view.MainActivity;
import com.example.myapplication.presentation.view.MyAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainController {

    private RecyclerView recyclerView;

    private MyAdapter mAdapter;

    private RecyclerView.LayoutManager layoutManager;

    private SwipeRefreshLayout mySwipeRefreshLayout;

    private SharedPreferences sharedPreferences;

    private Gson gson;

    private MainActivity view;
    public MainController(MainActivity mainActivity, Gson gson, SharedPreferences sharedPreferences){
        this.view = mainActivity;
        this.gson = gson;
        this.sharedPreferences = sharedPreferences;

    }
    public void onStart(){

        //showList();




        List<Food> foodList = HaveData();
        if(foodList != null){

            view.showList(foodList);
        }else {
            ApiCall();
        }
    }



    private void ApiCall(){



        Call<RestFood> call = Singletons.getPokeApi().getIngredientsResponse();
        call.enqueue(new Callback<RestFood>() {
            @Override
            public void onResponse(Call<RestFood> call, Response<RestFood> response) {
                if(response.isSuccessful() && response.body() != null){
                    List<Food> foodList = response.body().getIngredients();

                    saveList(foodList);
                    view.showList(foodList);
                }else{
                    view.showError();
                }
            }

            @Override
            public void onFailure(Call<RestFood> call, Throwable t) {
                view.showError();
            }
        });
    }

    private void saveList(List<Food> foodList) {
        String jsonString = gson.toJson(foodList);
        sharedPreferences
                .edit()
                .putString("jsonIngredientsList",jsonString)
                .apply();

    }

    private static final String BASE_URL = "https://raw.githubusercontent.com/MjuM/ProjectAndroid/master/";
    private List<Food> HaveData() {

        String jsonIngr = sharedPreferences.getString("jsonIngredientsList", null);
        if (jsonIngr == null) {
            return null;
        } else {
            Type listType = new TypeToken<List<Food>>() {
            }.getType();
            return gson.fromJson(jsonIngr, listType);
        }
    }
    public void onItemClick(Food food){
        view.navigateToDetails(food);
    }



}
