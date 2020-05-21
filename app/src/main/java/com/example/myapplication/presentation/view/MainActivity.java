package com.example.myapplication.presentation.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.Singletons;
import com.example.myapplication.presentation.controller.MainController;
import com.example.myapplication.presentation.model.Food;
import com.google.gson.Gson;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MyAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private SwipeRefreshLayout mySwipeRefreshLayout;
    private SharedPreferences sharedPreferences;
    private Gson gson;
    public static String PACKAGE_NAME;
    private MainController controller;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PACKAGE_NAME = getApplicationContext().getPackageName();
        controller = new MainController(this, Singletons.getGson(),Singletons.getSharedPreferencesInstance(getApplicationContext()));
        controller.onStart();

    }



    public void showList(final List<Food> foodList
    ) {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mySwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new MyAdapter(foodList, new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Food item) {
                controller.onItemClick(item);
            }
        });
        recyclerView.setAdapter(mAdapter);
      /*ItemTouchHelper.SimpleCallback simpleItemTouchCallback =
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder
                            target) {
                        return false;
                    }
                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                        pokemonList.remove(viewHolder.getAdapterPosition());
                        mAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                        Context context = getApplicationContext();


                        Toast myToast = Toast.makeText(context, "SWP SUCCESS", Toast.LENGTH_SHORT);
                        myToast.show();
                    }

                };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);*/
    }

    private static final String BASE_URL = "https://raw.githubusercontent.com/MjuM/ProjectAndroid/master/";


    public void showError() {
        Context context = getApplicationContext();

        Toast myToast = Toast.makeText(context, "Error API", Toast.LENGTH_SHORT);
        myToast.show();
    }

    public void navigateToDetails(Food food) {
        Intent myIntent = new Intent(MainActivity.this, DetailActivity.class);

        myIntent.putExtra("FoodKey",Singletons.getGson().toJson(food));
       startActivity(myIntent);
      /*  Context context = getApplicationContext();


        Toast myToast = Toast.makeText(context, pokemon.getName(), Toast.LENGTH_SHORT);
        myToast.show();*/
    }
}
