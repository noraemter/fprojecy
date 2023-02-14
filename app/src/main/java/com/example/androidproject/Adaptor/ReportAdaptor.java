package com.example.androidproject.Adaptor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject.R;
import com.example.androidproject.helper.BillDetails;

import java.util.ArrayList;


// @Author :Abdallah
// 12-6-2022
public class ReportAdaptor extends RecyclerView.Adapter<ReportAdaptor.ViewHolder> {


    private final ArrayList<BillDetails> billDetailsList;

    public ReportAdaptor(ArrayList<BillDetails> billDetailsList) {
        this.billDetailsList = billDetailsList;
    }

    @NonNull
    @Override
    public ReportAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_report_cart, parent, false);
        return new ViewHolder(inflate);

    }

    @Override
    public void onBindViewHolder(@NonNull ReportAdaptor.ViewHolder holder, int position) {
        BillDetails billDetail = billDetailsList.get(position);
        holder.title.setText(billDetail.getMealName());
        holder.title.setText(billDetail.getMealName());
        holder.counter.setText(String.valueOf(billDetail.getQuantity()));
        holder.profit.setText(String.valueOf(billDetail.getPrice()));

    }

    @Override
    public int getItemCount() {
        return billDetailsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView title, counter, profit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleTxt);
            counter = itemView.findViewById(R.id.numberItemTxt);
            profit = itemView.findViewById(R.id.profitTxt);

        }
    }


}
