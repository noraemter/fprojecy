package com.example.androidproject.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.androidproject.Adaptor.ReportAdaptor;
import com.example.androidproject.DatabaseUtility.UserSession;
import com.example.androidproject.R;
import com.example.androidproject.helper.BillDetails;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class GenerateReportActivity extends AppCompatActivity {

    private RecyclerView recyclerViewGenerateReport;
    private ArrayList<BillDetails> billDetailsArrayList;
    private TextView totalProfitTxt;
    private double totalProfit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_report);
        this.billDetailsArrayList = new ArrayList<>();
        this.recyclerViewGenerateReport = findViewById(R.id.GenerateReportView);
        this.recyclerViewGenerateReport.setLayoutManager(new LinearLayoutManager(GenerateReportActivity.this, LinearLayoutManager.VERTICAL, false));
        generateReport();
        this.totalProfitTxt = findViewById(R.id.totalTxtProfit);
    }

    private void generateReport() {
        final Handler handler = new Handler();
        handler.post(() -> {
            String url = "http://" + UserSession.IP_ADDRESS + "/AndroidProject/generate_reports.php";
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {

                        JSONObject obj = array.getJSONObject(i);

                        String male_name = obj.getString("meal_name");
                        double male_profit = Double.parseDouble(obj.getString("profit"));
                        totalProfit += male_profit;
                        int male_quantity = Integer.parseInt(obj.getString("sum_of_quantity"));
                        BillDetails billDetails = new BillDetails(male_name, male_profit, male_quantity);
                        billDetailsArrayList.add(billDetails);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                ReportAdaptor adapter = new ReportAdaptor(billDetailsArrayList);
                recyclerViewGenerateReport.setAdapter(adapter);
                totalProfitTxt.setText(String.valueOf(totalProfit));

            }, error -> Toast.makeText(GenerateReportActivity.this, error.toString(), Toast.LENGTH_LONG).show());

            Volley.newRequestQueue(GenerateReportActivity.this).add(stringRequest);
        });
    }

}