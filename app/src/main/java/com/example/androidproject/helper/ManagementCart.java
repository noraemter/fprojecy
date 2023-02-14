package com.example.androidproject.helper;

import android.content.Context;
import android.widget.Toast;

import com.example.androidproject.Domain.Meal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

public class ManagementCart {
    private final Context context;
    private static final HashMap<Integer, Meal> listFood = new HashMap<>();

    public ManagementCart(Context context) {
        this.context = context;
    }

    public void insertFood(Meal item) {

        if (listFood.containsKey(item.getId())) {
            listFood.get(item.getId()).setNumberInCart(listFood.get(item.getId()).getNumberInCart() + item.getNumberInCart());
        } else {
            listFood.put(item.getId(), item);
        }

        Toast.makeText(context, "Add To Your Cart", Toast.LENGTH_SHORT).show();
    }

    public void plusNumberFood(int mealID) {
        listFood.get(mealID).setNumberInCart(listFood.get(mealID).getNumberInCart() + 1);
    }

    public void minusNumberFood(int mealID) {
        listFood.get(mealID).setNumberInCart(listFood.get(mealID).getNumberInCart() - 1);

    }

    public Double getTotalFee() {
        AtomicReference<Double> sum = new AtomicReference<>(0d);
        listFood.forEach((k, v) -> {
            sum.set(sum.get() + v.getNumberInCart() * v.getSellingPrice());
        });
        return sum.get();
    }


    public boolean isCartEmpty() {
        return listFood.size() == 0;
    }


    public static ArrayList<Meal> getListCart() {
        ArrayList<Meal> meals = new ArrayList<>();
        listFood.forEach((k, v) -> {
            meals.add(v);
        });
        return meals;
    }
}
