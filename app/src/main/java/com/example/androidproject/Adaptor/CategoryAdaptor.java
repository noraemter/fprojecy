package com.example.androidproject.Adaptor;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.androidproject.Activity.CategoryMealsActivity;
import com.example.androidproject.DatabaseUtility.UserSession;
import com.example.androidproject.Domain.CategoryDomain;
import com.example.androidproject.R;

import java.util.ArrayList;

public class CategoryAdaptor extends RecyclerView.Adapter<CategoryAdaptor.ViewHolder> {


    private final ArrayList<CategoryDomain> categories;
    private final Context context;

    public CategoryAdaptor(Context context, ArrayList<CategoryDomain> categories) {
        this.context = context;
        this.categories = categories;
    }

    @NonNull
    @Override
    public CategoryAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_category, parent, false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdaptor.ViewHolder holder, int position) {
        final CategoryDomain categoryDomain = categories.get(position);
        CardView cardView = holder.cardView;
        ImageView imageView = cardView.findViewById(R.id.category_image);
        String image_path = "http://" + UserSession.IP_ADDRESS + categoryDomain.getCategory_image_path();
        Glide.with(context).load(image_path).into(imageView);
        TextView txt = cardView.findViewById(R.id.txtName);
        txt.setText(categoryDomain.getTitle());
        cardView.setOnClickListener(v -> {
            Intent intent = new Intent(context, CategoryMealsActivity.class);
            intent.putExtra("CATEGORY_ID", "" + categoryDomain.getId());
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final CardView cardView;

        public ViewHolder(CardView cardView) {
            super(cardView);
            this.cardView = cardView;
        }

    }
}
