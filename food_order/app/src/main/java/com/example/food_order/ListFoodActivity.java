package com.example.food_order;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.net.URL;
import java.util.ArrayList;

public class ListFoodActivity extends AppCompatActivity {

    ListView lvFood;
    ArrayList<Food> foodArrayList;
    FoodAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_food);

        init();

        adapter = new FoodAdapter(this, R.layout.food_row, foodArrayList);
        lvFood.setAdapter(adapter);

    }

    private void init(){
        lvFood = (ListView) findViewById(R.id.listview);
        foodArrayList = new ArrayList<>();

        foodArrayList.add(new Food("Chocolate Chip Cookies", R.drawable.chocolatechipcookies, "https://www.cookingchanneltv.com/recipes/kelsey-nixon/chocolate-chip-cookies-2106993", R.drawable.chocolatechipcookies2));
        foodArrayList.add(new Food("Tomato Soup", R.drawable.tomatosoup, "https://www.cookingchanneltv.com/recipes/tiffani-thiessen/homemade-roasted-tomato-soup-2756233", R.drawable.tomatosoup2));
        foodArrayList.add(new Food("Rosemary Chicken Noodle Soup", R.drawable.chickensoup, "https://www.gimmesomeoven.com/rosemary-chicken-noodle-soup-recipe/", R.drawable.chickensoup2));
        foodArrayList.add(new Food("Mexican Chocolate Cookies", R.drawable.mexicancookies, "https://www.cookingchanneltv.com/recipes/spicy-mexican-hot-chocolate-cookies-1962907", R.drawable.mexicancookies2));
        foodArrayList.add(new Food("Beef steak", R.drawable.beefsteak, "https://www.cookingchanneltv.com/recipes/alton-brown/reverse-sear-ribeye-steak-reloaded-5458694", R.drawable.beefsteak2));
        foodArrayList.add(new Food("Shrimp tacos", R.drawable.shrimptacos, "https://www.gimmesomeoven.com/shrimp-tacos-recipe/", R.drawable.shrimptacos2));
        foodArrayList.add(new Food("Lemon-Lime Cake", R.drawable.lemonlimecake, "https://www.bettycrocker.com/recipes/lemon-lime-checkerboard-cake/9ba32e15-746f-485d-a41a-3a66d3c8557b", R.drawable.lemonlimecake2));
        foodArrayList.add(new Food("Chocolate Cupcakes", R.drawable.cupcakes, "https://www.bettycrocker.com/recipes/chocolate-covered-valentines-day-stroopwafel-cupcakes/f2977402-b541-4a4b-a8a4-b2cc2c48b198", R.drawable.cupcakes2));

    }
}