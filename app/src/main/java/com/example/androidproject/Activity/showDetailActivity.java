package com.example.androidproject.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.androidproject.DatabaseUtility.UserSession;
import com.example.androidproject.Domain.Meal;
import com.example.androidproject.R;
import com.example.androidproject.helper.ManagementCart;

public class showDetailActivity extends AppCompatActivity {
    private TextView addToChartBtn;
    private TextView titleTxt, feeTxt, descriptionTxt, numberOrderTxt;
    private ImageView plusBtn, minusBtn, picFood;
    private Meal meal;
    int numberOrder = 1;
    private ManagementCart managementCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);
        managementCart = new ManagementCart(this);
        int id = Integer.parseInt(getIntent().getStringExtra("ID"));
        String name = getIntent().getStringExtra("NAME");
        String desc = getIntent().getStringExtra("DESCRIPTION");
        Double price = Double.parseDouble(getIntent().getStringExtra("SELLING"));
        String image = getIntent().getStringExtra("IMAGE");
        meal = new Meal(id, name, image, desc, price);
        intView();
        getBundle();
    }

    private void getBundle() {

        String image_path = "http://" + UserSession.IP_ADDRESS + meal.getPic();
        Glide.with(this).load(image_path).into(picFood);

        titleTxt.setText(meal.getTitle());

        feeTxt.setText(String.valueOf(meal.getSellingPrice()));
        descriptionTxt.setText(meal.getDescription());
        numberOrderTxt.setText(String.valueOf(numberOrder));

        plusBtn.setOnClickListener(view -> {
            numberOrder++;
            numberOrderTxt.setText(String.valueOf(numberOrder));
        });

        minusBtn.setOnClickListener(view -> {
            if (numberOrder > 1)
                numberOrder--;
            numberOrderTxt.setText(String.valueOf(numberOrder));
        });
        addToChartBtn.setOnClickListener(view -> {
            meal.setNumberInCart(numberOrder);
            managementCart.insertFood(meal);
        });


    }

    private void intView() {
        addToChartBtn = findViewById(R.id.addToChartBtn);
        titleTxt = findViewById(R.id.titleText);
        feeTxt = findViewById(R.id.priceTxt);
        descriptionTxt = findViewById(R.id.descriptionTxt);
        numberOrderTxt = findViewById(R.id.numberOrderTxt);
        plusBtn = findViewById(R.id.plusbtn);
        minusBtn = findViewById(R.id.minusbtn);
        picFood = findViewById(R.id.picFood);

    }
}