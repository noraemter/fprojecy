package com.example.androidproject.Adaptor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.androidproject.Activity.CartListActivity;
import com.example.androidproject.DatabaseUtility.UserSession;
import com.example.androidproject.Domain.Meal;
import com.example.androidproject.R;
import com.example.androidproject.helper.ManagementCart;

import java.util.ArrayList;

public class CartListAdaptor extends RecyclerView.Adapter<CartListAdaptor.ViewHolder> {
    private final ArrayList<Meal> meals;
    private final ManagementCart managementCart;

    private final Context context;

    public CartListAdaptor(ArrayList<Meal> meals, Context context) {
        this.meals = meals;
        this.managementCart = new ManagementCart(context);
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_cart, parent, false);
        return new ViewHolder(inflate);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.title.setText(meals.get(position).getTitle());
        holder.feeEachItem.setText(String.valueOf(meals.get(position).getSellingPrice()));
        holder.totalEachItem.setText(String.valueOf(Math.round((meals.get(position).getNumberInCart() * meals.get(position).getSellingPrice()) * 100) / 100));
        holder.num.setText(String.valueOf(meals.get(position).getNumberInCart()));

        String image_path = "http://" + UserSession.IP_ADDRESS + meals.get(position).getPic();
        Glide.with(holder.itemView.getContext()).load(image_path).into(holder.pic);

        holder.plusItem.setOnClickListener(v -> {
            managementCart.plusNumberFood(meals.get(position).getId());
            notifyDataSetChanged();
            refresh();
        });
        holder.minusItem.setOnClickListener(v -> {
            managementCart.minusNumberFood(meals.get(position).getId());
            notifyDataSetChanged();
            refresh();
        });
    }

    @Override
    public int getItemCount() {
        return meals.size();

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, feeEachItem;
        ImageView pic, plusItem, minusItem;
        TextView totalEachItem, num;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleTxt);
            feeEachItem = itemView.findViewById(R.id.feeEachItem);
            pic = itemView.findViewById(R.id.picCart);
            totalEachItem = itemView.findViewById(R.id.totalEachItem);
            num = itemView.findViewById(R.id.numberItemTxt);
            plusItem = itemView.findViewById(R.id.plusCardBtn);
            minusItem = itemView.findViewById(R.id.minusCartBtn);
        }
    }

    private void refresh() {
        context.startActivity(new Intent(context, CartListActivity.class));
    }
}
