package com.example.androidproject.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.androidproject.DatabaseUtility.UserSession;
import com.example.androidproject.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class SignUpActivity extends AppCompatActivity {

    private EditText uname, display_name, passwd, conf_passwd;
    private JSONObject responseJsonObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        this.uname = findViewById(R.id.uname_sign_up);
        this.display_name = findViewById(R.id.display_name_sigin_up);
        this.passwd = findViewById(R.id.password_sign_up);
        this.conf_passwd = findViewById(R.id.confirm_password_sign_up);
        RequestQueue queue = Volley.newRequestQueue(this);
    }

    public void handleLogin(View view) {
        startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
    }

    public void handleSignUp(View view) {

        if (this.uname.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Please fill the user name", Toast.LENGTH_SHORT).show();
            return;
        }
        if (this.passwd.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Please fill the password", Toast.LENGTH_SHORT).show();
            return;
        }
        if (this.conf_passwd.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Please fill the confirm password", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!this.passwd.getText().toString().equals(this.conf_passwd.getText().toString())) {
            Toast.makeText(this, "Not match password", Toast.LENGTH_SHORT).show();
            return;
        }
        this.handleSignUp(this.uname.getText().toString().trim(), this.passwd.getText().toString().trim(), this.display_name.getText().toString().trim());

    }

    private void handleSignUp(String uname, String passwd, String display_name) {
        final Handler handler = new Handler();
        handler.post(() -> {
            String url = "http://" + UserSession.IP_ADDRESS + "/MobileProject/new_customer_account.php";
            RequestQueue queue = Volley.newRequestQueue(SignUpActivity.this);
            StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        if (!response.isEmpty()) {
                            responseJsonObject = new JSONObject(response);
                            if (responseJsonObject.has("insert") && !responseJsonObject.isNull("insert")) {
                                JSONObject responseJsonObject2 = responseJsonObject.getJSONObject("insert");
                                if (responseJsonObject2.getString("error").equals("false")) {
                                    Toast.makeText(SignUpActivity.this, "new account was created successfully", Toast.LENGTH_SHORT).show();
                                    goToLoginActivity();
                                }
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }, error -> Toast.makeText(SignUpActivity.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show()) {
                @Override
                public String getBodyContentType() {

                    return "application/x-www-form-urlencoded; charset=UTF-8";
                }

                @Override
                protected Map<String, String> getParams() {

                    Map<String, String> params = new HashMap<>();
                    params.put("user_name", uname);
                    params.put("pass_word", passwd);
                    params.put("customer_name", display_name);

                    return params;
                }
            };

            queue.add(request);
        });

    }

    private void goToLoginActivity() {
        startActivity(new Intent(this, LoginActivity.class));
    }
}