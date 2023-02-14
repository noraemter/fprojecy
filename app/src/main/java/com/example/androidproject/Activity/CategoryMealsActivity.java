package com.example.androidproject.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.androidproject.Adaptor.MealAdaptor;
import com.example.androidproject.DatabaseUtility.UserSession;
import com.example.androidproject.Domain.Meal;
import com.example.androidproject.R;

import org.json.JSONArray;

import org.json.JSONObject;

import java.util.ArrayList;

public class CategoryMealsActivity extends AppCompatActivity {
    private RecyclerView recyclerViewMealList;
    private ArrayList<Meal> categoryMealsArrayList;
    private String catId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_meals);
        Intent intent = getIntent();
        catId = intent.getStringExtra("CATEGORY_ID");
        this.categoryMealsArrayList = new ArrayList<>();

        recyclerViewPopular();
    }

    private void recyclerViewPopular() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        recyclerViewMealList = findViewById(R.id.rv_category_items);
        recyclerViewMealList.setLayoutManager(linearLayoutManager);
        this.loadMeals(catId);
    }

    private void loadMeals(String catId) {
        final Handler handler = new Handler();
        handler.post(() -> {
            String url = "http://" + UserSession.IP_ADDRESS + "/AndroidProject/get_meals_from_category.php?category_id=" + catId;
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    response -> {
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {

                                JSONObject obj = array.getJSONObject(i);
                                int mealId = Integer.parseInt(obj.getString("meal_id"));
                                String mealName = obj.getString("meal_name");
                                String mealDisc = obj.getString("meal_description");
                                Double sellingPrice = Double.parseDouble(obj.getString("meal_selling_price"));
                                String imgPath = obj.getString("meal_img_path");
                                Meal meal = new Meal(mealId, mealName, imgPath, mealDisc, sellingPrice);
                                categoryMealsArrayList.add(meal);
                            }
                        } catch (Exception e) {

                        }
                        MealAdaptor adapter = new MealAdaptor(CategoryMealsActivity.this, categoryMealsArrayList);
                        recyclerViewMealList.setAdapter(adapter);
                    }, error -> Toast.makeText(CategoryMealsActivity.this, error.toString(), Toast.LENGTH_LONG).show());

            Volley.newRequestQueue(CategoryMealsActivity.this).add(stringRequest);
        });

    }
}