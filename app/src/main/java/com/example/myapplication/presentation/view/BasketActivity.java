package com.example.myapplication.presentation.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.myapplication.R;
import com.example.myapplication.Singletons;
import com.example.myapplication.presentation.controller.MainController;
import com.example.myapplication.presentation.model.Food;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class BasketActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private StringAdaptor mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private SwipeRefreshLayout mySwipeRefreshLayout;
    private SharedPreferences sharedPreferences;
    private Gson gson;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        String IngrJson = intent.getStringExtra("BasketKey");
        String IngrJson2 = intent.getStringExtra("BasketKey2");
        ArrayList<String> test = getIntent().getStringArrayListExtra("BasketKey");
        ArrayList<String> test2 = getIntent().getStringArrayListExtra("BasketKey2");
        showList(test,test2);
        //List<Food> basketList = Singletons.getGson().fromJson(IngrJson, Food.class);







    }



    public void showList(final List<String> BasketList, final List<String> costList
    ) {
        BasketList.add("TOTAL COST  = ");
        int i;
        int total = 0;
        String stringTotal;
        for(i = 0 ; i < costList.size() ; i++){
            total += Integer.parseInt(costList.get(i));
        }
        stringTotal = Integer.toString(total);
        costList.add(stringTotal);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mySwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new StringAdaptor(BasketList, costList);

        recyclerView.setAdapter(mAdapter);

    }
}
