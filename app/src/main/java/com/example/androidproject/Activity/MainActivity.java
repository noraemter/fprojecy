package com.example.androidproject.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.androidproject.Adaptor.CategoryAdaptor;
import com.example.androidproject.DatabaseUtility.UserSession;
import com.example.androidproject.Domain.CategoryDomain;
import com.example.androidproject.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewCategoryList;
    private ArrayList<CategoryDomain> categoryDomainArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.categoryDomainArrayList = new ArrayList<>();
        this.recyclerViewCategoryList = findViewById(R.id.rv_category_items);
        this.recyclerViewCategoryList.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
        this.loadCategories();
        this.bottomNavigation();
    }

    private void bottomNavigation() {
        FloatingActionButton floatingActionButton = findViewById(R.id.cartBtn1);
        LinearLayout homeBtn = findViewById(R.id.homeBtn);
        LinearLayout logout = findViewById(R.id.logoutBtn);
        floatingActionButton.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, CartListActivity.class)));
        homeBtn.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, MainActivity.class)));
        logout.setOnClickListener(view -> {
            UserSession.USER_ID_IN_SESSION = -1;
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        });

    }


    private void loadCategories() {
        final Handler handler = new Handler();
        handler.post(() -> {
            String url = "http://" + UserSession.IP_ADDRESS + "/AndroidProject/get_categories.php";
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    response -> {
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {

                                JSONObject obj = array.getJSONObject(i);

                                int id = Integer.parseInt(obj.getString("category_id"));
                                String category_name = obj.getString("category_name");
                                String category_image_path = obj.getString("category_image_path");
                                CategoryDomain categoryDomain = new CategoryDomain(id, category_name, category_image_path);
                                categoryDomainArrayList.add(categoryDomain);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();

                        }

                        CategoryAdaptor adapter = new CategoryAdaptor(MainActivity.this, categoryDomainArrayList);
                        recyclerViewCategoryList.setAdapter(adapter);

                    }, error -> Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show());

            Volley.newRequestQueue(MainActivity.this).add(stringRequest);
        });

    }

}