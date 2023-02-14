package com.example.androidproject.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.androidproject.Adaptor.CartListAdaptor;
import com.example.androidproject.DatabaseUtility.UserSession;
import com.example.androidproject.Domain.Meal;
import com.example.androidproject.R;
import com.example.androidproject.helper.ManagementCart;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CartListActivity extends AppCompatActivity {

    private RecyclerView recyclerViewList;
    private TextView totalFeeTxt, taxTxt, deliveryTxt, totalTxt, emptyTxt, buy, discounts;
    private ScrollView scrollView;
    private ManagementCart managementCart;
    private String billID;
    private boolean isGoldenCustomer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_list);
        this.managementCart = new ManagementCart(this);
        initView();
        initList();
        isCustomerGolden(String.valueOf(UserSession.USER_ID_IN_SESSION));
        bottomNavigation();
        handleBuyButton();
    }

    private void bottomNavigation() {
        FloatingActionButton floatingActionButton = findViewById(R.id.cartBtn2);
        LinearLayout homeBtn = findViewById(R.id.homeBtn);
        LinearLayout logout = findViewById(R.id.logoutBtn2);
        floatingActionButton.setOnClickListener(view -> startActivity(new Intent(CartListActivity.this, CartListActivity.class)));
        homeBtn.setOnClickListener(view -> startActivity(new Intent(CartListActivity.this, MainActivity.class)));
        logout.setOnClickListener(view -> {
            UserSession.USER_ID_IN_SESSION = -1;
            startActivity(new Intent(CartListActivity.this, LoginActivity.class));
        });

    }

    private void initView() {
        recyclerViewList = findViewById(R.id.cartView);
        taxTxt = findViewById(R.id.taxTxt);
        totalFeeTxt = findViewById(R.id.totalFeelTxt);
        deliveryTxt = findViewById(R.id.deliveryServiceTxt);
        totalTxt = findViewById(R.id.totalTxtProfit);
        emptyTxt = findViewById(R.id.empty_cart_text);
        scrollView = findViewById(R.id.scrollView3);
        buy = findViewById(R.id.buy_btn);
        discounts = findViewById(R.id.discountTxt);

    }

    private void handleBuyButton() {
        buy.setOnClickListener(v -> {
            AlertDialog.Builder alt = new AlertDialog.Builder(CartListActivity.this);
            alt.setMessage("Are you sure to buy the cart?").setCancelable(false)
                    .setPositiveButton("Yes", (dialog, which) -> buyCart(String.valueOf(UserSession.USER_ID_IN_SESSION),
                            totalTxt.getText().toString()))
                    .setNegativeButton("No", (dialog, which) -> dialog.cancel());

            AlertDialog alertDialog = alt.create();
            alertDialog.setTitle("Confirm the purchase of the cart");
            alertDialog.show();
        });
    }

    private void isCustomerGolden(String userID) {
        final Handler handler = new Handler();
        handler.post(() -> {
            String url = "http://" + UserSession.IP_ADDRESS + "/MobileProject/is_customer_golden.php";
            RequestQueue queue = Volley.newRequestQueue(CartListActivity.this);
            StringRequest request = new StringRequest(Request.Method.POST, url, response -> {
                try {
                    if (!response.isEmpty()) {
                        JSONObject responseJsonObject = new JSONObject(response);
                        if (responseJsonObject.has("golden_customer") && !responseJsonObject.isNull("golden_customer")) {
                            JSONObject responseJsonObject2 = responseJsonObject.getJSONObject("golden_customer");
                            if (responseJsonObject2.getString("isGolden").equals("1")) {
                                isGoldenCustomer = true;
                            }
                            CalculateCart();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }, error -> {
                Toast.makeText(CartListActivity.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/x-www-form-urlencoded; charset=UTF-8";
                }

                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("user_id", userID);
                    return params;
                }
            };

            queue.add(request);
        });

    }


    private void buyCart(String userID, String valueOfBill) {
        final Handler handler = new Handler();
        handler.post(() -> {
            String url = "http://" + UserSession.IP_ADDRESS + "/MobileProject/new_bill.php";
            RequestQueue queue = Volley.newRequestQueue(CartListActivity.this);
            StringRequest request = new StringRequest(Request.Method.POST, url, response -> {
                try {
                    if (!response.isEmpty()) {
                        JSONObject responseJsonObject = new JSONObject(response);
                        if (responseJsonObject.has("bill") && !responseJsonObject.isNull("bill")) {
                            JSONObject responseJsonObject2 = responseJsonObject.getJSONObject("bill");
                            if (responseJsonObject2.getString("error").equals("false")) {
                                billID = responseJsonObject2.getString("bill_id");
                                insertToBillDetails(this.billID);
                                Toast.makeText(CartListActivity.this, "Your request has been successfully received\nand it will be sent to you as soon as possible", Toast.LENGTH_LONG).show();
                                goToMainActivity();
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }, error -> Toast.makeText(CartListActivity.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show()) {
                @Override
                public String getBodyContentType() {
                    return "application/x-www-form-urlencoded; charset=UTF-8";
                }

                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("user_id", userID);
                    params.put("val_of_bill", valueOfBill);
                    return params;
                }
            };

            queue.add(request);
        });
    }


    private void insertToBillDetails(String billID) {
        final Handler handler = new Handler();
        handler.post(() -> {
            ArrayList<Meal> meals = ManagementCart.getListCart();
            for (Meal meal : meals) {
                sendBillDetailsToDB(billID, meal);
            }
        });
    }

    private void sendBillDetailsToDB(String billID, Meal meal) {
        final Handler handler = new Handler();
        handler.post(() -> {
            String url = "http://" + UserSession.IP_ADDRESS + "/AndroidProject/new_bill_details.php";
            RequestQueue queue = Volley.newRequestQueue(CartListActivity.this);
            StringRequest request = new StringRequest(Request.Method.POST,
                    url,
                    response -> {
                        try {
                            if (!response.isEmpty()) {
                                JSONObject responseJsonObject = new JSONObject(response);
                                if (responseJsonObject.has("bill_details") && !responseJsonObject.isNull("bill_details")) {
                                    JSONObject responseJsonObject2 = responseJsonObject.getJSONObject("bill_details");
                                    if (responseJsonObject2.getString("error").equals("true")) {
                                        Toast.makeText(CartListActivity.this, "Error in add bill details", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }, error -> {
                // method to handle errors.
                Toast.makeText(CartListActivity.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }) {
                @Override
                public String getBodyContentType() {

                    return "application/x-www-form-urlencoded; charset=UTF-8";
                }

                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("b_id", billID);
                    params.put("m_id", String.valueOf(meal.getId()));
                    params.put("quantity", String.valueOf(meal.getNumberInCart()));
                    return params;
                }
            };
            queue.add(request);
        });

    }

    private void initList() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewList.setLayoutManager(linearLayoutManager);
        CartListAdaptor adaptor = new CartListAdaptor(ManagementCart.getListCart(), this);
        recyclerViewList.setAdapter(adaptor);
        if (managementCart.isCartEmpty()) {
            emptyTxt.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.INVISIBLE);
        } else {
            scrollView.setVisibility(View.VISIBLE);
        }
    }

    private void CalculateCart() {
        double percentTax = 0.02;
        double delivery = 10;

        double tax = Math.round((managementCart.getTotalFee() * percentTax) * 100) / 100.0;
        double itemTotal = Math.round(managementCart.getTotalFee() * 100) / 100.0;
        double total = Math.round((managementCart.getTotalFee() + tax + delivery) * 100) / 100.0;

        totalFeeTxt.setText(String.valueOf(itemTotal));
        taxTxt.setText(String.valueOf(tax));
        deliveryTxt.setText(String.valueOf(delivery));

        if (isGoldenCustomer) {
            total -= (total * 0.05d);
            discounts.setText("5%");
        } else {
            discounts.setText("0.0%");
        }
        totalTxt.setText(String.valueOf(total));
    }

    private void goToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}