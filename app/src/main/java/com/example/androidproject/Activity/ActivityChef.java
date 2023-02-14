package com.example.androidproject.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.androidproject.Adaptor.ChefAdaptor;
import com.example.androidproject.DatabaseUtility.UserSession;
import com.example.androidproject.R;
import com.example.androidproject.helper.Bill;
import com.example.androidproject.helper.BillDetails;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

public class ActivityChef extends AppCompatActivity {
    private RecyclerView recyclerViewChefOrderList;
    private ArrayList<Bill> billOrderArrayList;
    private Bill bill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheif);
        this.recyclerViewChefOrderList = findViewById(R.id.orderListRecyclerView);
        this.recyclerViewChefOrderList.setLayoutManager(new LinearLayoutManager(ActivityChef.this, LinearLayoutManager.VERTICAL, false));
        this.billOrderArrayList = new ArrayList<>();
        loadOrderList();
    }


    private void loadOrderList() {
        final Handler handler = new Handler();
        handler.post(() -> {
            String url = "http://" + UserSession.IP_ADDRESS + "/AndroidProject/get_order.php";
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {
                try {
                    if (Objects.equals(response, "[]")) {
                        findViewById(R.id.empty_order_text).setVisibility(View.VISIBLE);
                        return;
                    }
                    findViewById(R.id.empty_order_text).setVisibility(View.INVISIBLE);
                    JSONObject array = new JSONObject(response);
                    Iterator<String> data = array.keys();
                    while (data.hasNext()) {
                        String k1 = data.next();
                        JSONArray jsonArray = array.getJSONArray(k1);
                        bill = new Bill();
                        for (int i = 0; i < jsonArray.length(); ++i) {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            bill.setBillId(obj.getString("bill_id"));
                            String meal_name = obj.getString("meal_name");
                            int quantity = Integer.parseInt(obj.getString("quantity"));
                            bill.getBillDetailsList().add(new BillDetails(meal_name, quantity));
                        }
                        billOrderArrayList.add(bill);
                    }

                } catch (Exception e) {
                    Toast.makeText(ActivityChef.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }

                ChefAdaptor chefAdaptor = new ChefAdaptor(ActivityChef.this, billOrderArrayList);
                recyclerViewChefOrderList.setAdapter(chefAdaptor);

            }, error -> Toast.makeText(ActivityChef.this, error.toString(), Toast.LENGTH_LONG).show());

            Volley.newRequestQueue(ActivityChef.this).add(stringRequest);
        });

    }
}