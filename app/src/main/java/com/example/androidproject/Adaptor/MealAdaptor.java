package com.example.androidproject.Adaptor;

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
import com.example.androidproject.Activity.showDetailActivity;
import com.example.androidproject.DatabaseUtility.UserSession;
import com.example.androidproject.Domain.Meal;
import com.example.androidproject.R;

import java.util.ArrayList;

public class MealAdaptor extends RecyclerView.Adapter<MealAdaptor.ViewHolder> {


    private final ArrayList<Meal> meals;
    private final Context context;

    public MealAdaptor(Context context, ArrayList<Meal> categories) {
        this.context = context;
        this.meals = categories;
    }

    @NonNull
    @Override
    public MealAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_popular, parent, false);

        return new ViewHolder(inflate);

    }

    @Override
    public void onBindViewHolder(@NonNull MealAdaptor.ViewHolder holder, int position) {
        Meal meal = meals.get(position);
        holder.title.setText(meal.getTitle());
        holder.fee.setText(String.valueOf(meal.getSellingPrice()));

        String image_path = "http://" + UserSession.IP_ADDRESS + meal.getPic();
        Glide.with(holder.itemView.getContext()).load(image_path).into(holder.pic);

        holder.addBtn.setOnClickListener(view -> {
            Intent intent = new Intent(context, showDetailActivity.class);
            intent.putExtra("ID", meal.getId()+"");
            intent.putExtra("NAME", meal.getTitle()+"");
            intent.putExtra("DESCRIPTION", meal.getDescription()+"");
            intent.putExtra("SELLING", meal.getSellingPrice()+"");
            intent.putExtra("IMAGE", meal.getPic()+"");
            context.startActivity(intent);
        });

    }


    @Override
    public int getItemCount() {
        return meals.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView title, fee;
        ImageView pic;
        TextView addBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            fee = itemView.findViewById(R.id.fee);
            pic = itemView.findViewById(R.id.pic);
            addBtn = itemView.findViewById(R.id.addBtn);
        }
    }
}
