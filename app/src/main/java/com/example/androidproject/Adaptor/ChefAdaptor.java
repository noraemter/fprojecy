package com.example.androidproject.Adaptor;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.androidproject.Activity.ActivityChef;

import com.example.androidproject.DatabaseUtility.UserSession;
import com.example.androidproject.R;
import com.example.androidproject.helper.Bill;
import com.example.androidproject.helper.BillDetails;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChefAdaptor extends RecyclerView.Adapter<ChefAdaptor.ViewHolder> {
    private final ArrayList<Bill> chefOrderList;
    private final Context context;
    private JSONObject responseJsonObject;

    public ChefAdaptor(Context context, ArrayList<Bill> chefOrderList) {
        this.context = context;
        this.chefOrderList = chefOrderList;
    }

    @NonNull
    @Override
    public ChefAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_chef_cardview, parent, false);
        return new ChefAdaptor.ViewHolder(inflate);

    }

    @Override
    public void onBindViewHolder(@NonNull ChefAdaptor.ViewHolder holder, int position) {

        Bill bill = chefOrderList.get(position);

        ArrayAdapter<BillDetails> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, bill.getBillDetailsList());
        holder.listView.setAdapter(adapter);

        holder.sentButton.setOnClickListener(v -> updateStatus(bill.getBillId()));
    }

    private void updateStatus(String billId) {
        final Handler handler = new Handler();
        handler.post(() -> {
            String url = "http://" + UserSession.IP_ADDRESS + "/MobileProject/update_order_status.php";
            RequestQueue queue = Volley.newRequestQueue(context);
            StringRequest request = new StringRequest(Request.Method.POST, url, response -> {
                try {
                    if (!response.isEmpty()) {
                        responseJsonObject = new JSONObject(response);
                        if (responseJsonObject.has("update") && !responseJsonObject.isNull("update")) {
                            JSONObject responseJsonObject2 = responseJsonObject.getJSONObject("update");
                            if (responseJsonObject2.getString("error").equals("false")) {
                                Toast.makeText(context, "Order sent successfully", Toast.LENGTH_SHORT).show();
                                context.startActivity(new Intent(context, ActivityChef.class));
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }, error -> {
                // method to handle errors.
                Toast.makeText(context, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }) {
                @Override
                public String getBodyContentType() {

                    return "application/x-www-form-urlencoded; charset=UTF-8";
                }

                @Override
                protected Map<String, String> getParams() {

                    Map<String, String> params = new HashMap<>();

                    params.put("bill_id", billId);

                    return params;
                }
            };

            queue.add(request);
        });

    }

    @Override
    public int getItemCount() {
        return chefOrderList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ListView listView;
        Button sentButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            listView = itemView.findViewById(R.id.listviewChefOrderList);
            sentButton = itemView.findViewById(R.id.sentButtonChef);

        }
    }


}
