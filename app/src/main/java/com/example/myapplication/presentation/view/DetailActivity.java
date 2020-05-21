package com.example.myapplication.presentation.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.Singletons;
import com.example.myapplication.presentation.model.Food;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private TextView txtDetail;
    private TextView txtDetail2;
    private TextView txtDetail3;
    private TextView txtDetail4;
    private SharedPreferences sharedPreferences2;

    private Gson gson2;

    private DetailActivity view2;
    private Button bask;
    private Button button;
    private Button clear;
    public List<Food> panier = new ArrayList<>();

    private Food food;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences2 = Singletons.getSharedPreferencesInstance(getApplicationContext());
        gson2 = Singletons.getGson();
        setContentView(R.layout.detail_activity );
        button = findViewById(R.id.add_food);
        bask = findViewById(R.id.show_basket);
        clear = findViewById(R.id.clear_basket);
        txtDetail = findViewById(R.id.detail_txt);
        txtDetail2= findViewById(R.id.detail_txt2);
        txtDetail3 = findViewById(R.id.detail_txt3);
        txtDetail4 = findViewById(R.id.detail_txt4);

        Intent intent = getIntent();
        String IngrJson = intent.getStringExtra("FoodKey");

         food = Singletons.getGson().fromJson(IngrJson, Food.class);

        Context context = getApplicationContext();
        panier = HaveData();
        if(panier == null){
            panier = new ArrayList<>();
            Toast myToast = Toast.makeText(this, "Votre panier est vide", Toast.LENGTH_SHORT);
            myToast.show();
        }else{
            Toast myToast = Toast.makeText(this, "Vous avez des articles", Toast.LENGTH_SHORT);
            myToast.show();

        }
        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                ShowSmth(food);
            }
        });
        bask.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                navigateToBasket(panier);
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                removeFood(panier);
            }
        });


        showDetail(food);
    }
    public void ShowSmth(Food food){

        panier.add(food);
        saveBasket(panier);
        Toast myToast = Toast.makeText(this, " vous avez choisis "+ food.getName(), Toast.LENGTH_SHORT);
        myToast.show();

    }

    private void showDetail(Food food) {
        txtDetail.setText(food.getName());
        txtDetail2.setText(food.getIngredient1());
        txtDetail3.setText(food.getIngredient2());
        txtDetail4.setText(food.getIngredient3());

    }

    private void removeFood (List<Food> basketList){
        basketList.clear();
        saveBasket(basketList);
    }

    private void saveBasket(List<Food> foodList) {
        String jsonString2 = gson2.toJson(foodList);
        sharedPreferences2
                .edit()
                .putString("jsonBasketList",jsonString2)
                .apply();

    }

    private List<Food> HaveData() {

        String jsonIngr = sharedPreferences2.getString("jsonBasketList", null);
        if (jsonIngr == null) {
            return null;
        } else {
            Type listType = new TypeToken<List<Food>>() {
            }.getType();
            return gson2.fromJson(jsonIngr, listType);
        }
    }
    public void navigateToBasket(List<Food> foodList) {
        Intent myIntent = new Intent(DetailActivity.this, BasketActivity.class);
        List<String> test;
        List<String> costTest;
        test = new ArrayList<String>();
        costTest = new ArrayList<String>();
        int i = 0;
        for(i = 0 ; i < foodList.size() ; i++){
            test.add(foodList.get(i).getName());
            costTest.add(Integer.toString(foodList.get(i).getCost()));
        }
        myIntent.putStringArrayListExtra("BasketKey", (ArrayList<String>) test);
        myIntent.putStringArrayListExtra("BasketKey2", (ArrayList<String>) costTest);
        startActivity(myIntent);
      /*  Context context = getApplicationContext();


        Toast myToast = Toast.makeText(context, pokemon.getName(), Toast.LENGTH_SHORT);
        myToast.show();*/
    }
}
