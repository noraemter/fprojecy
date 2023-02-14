package com.example.androidproject.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.CheckBox;
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

public class LoginActivity extends AppCompatActivity {

    private EditText uname, passwd;
    private JSONObject responseJsonObject;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private CheckBox rememberMeCB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.uname = findViewById(R.id.uname);
        this.passwd = findViewById(R.id.password_sign_up);
        this.rememberMeCB = findViewById(R.id.remember_me);

        setupSharedPrefs();
        checkPrefs();

        RequestQueue queue = Volley.newRequestQueue(this);
    }

    private void checkPrefs() {
        boolean flag = sharedPreferences.getBoolean("FLAG", false);
        if (flag) {
            String name = sharedPreferences.getString("NAME", "");
            String password = sharedPreferences.getString("PASSWORD", "");
            this.uname.setText(name);
            this.passwd.setText(password);
            this.rememberMeCB.setChecked(true);

        }
    }

    private void setupSharedPrefs() {
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        this.editor = sharedPreferences.edit();
    }

    public void handleLogin(View view) {


        String name = this.uname.getText().toString();
        String pass = this.passwd.getText().toString();
        if (rememberMeCB.isChecked()) {
            this.editor.putString("NAME", name.trim());
            this.editor.putString("PASSWORD", pass.trim());
            this.editor.putBoolean("FLAG", true);
            this.editor.commit();
        }
        this.login(name.trim(), pass.trim());

    }

    private void login(String user_name, String pass_word) {
        final Handler handler = new Handler();
        handler.post(() -> {
            String url = "http://" + UserSession.IP_ADDRESS + "/AndroidProject/user_type.php";
            RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
            StringRequest request = new StringRequest(Request.Method.POST,
                    url,
                    response -> {
                        try {
                            if (!response.isEmpty()) {
                                responseJsonObject = new JSONObject(response);
                                if (responseJsonObject.has("user") && !responseJsonObject.isNull("user")) {
                                    JSONObject responseJsonObject2 = responseJsonObject.getJSONObject("user");
                                    UserSession.USER_ID_IN_SESSION = Integer.parseInt(responseJsonObject2.getString("id"));
                                    UserSession.USER_TYPE = Integer.parseInt(responseJsonObject2.getString("login_type"));
                                    if (UserSession.USER_TYPE == 1) {
                                        goToMainActivity();
                                    } else if (UserSession.USER_TYPE == 2) {
                                        goToMangerActivity();
                                    } else if (UserSession.USER_TYPE == 3) {
                                        goToChefActivity();
                                    }
                                }
                            } else {
                                Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }, error -> Toast.makeText(LoginActivity.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show()) {
                @Override
                public String getBodyContentType() {
                    return "application/x-www-form-urlencoded; charset=UTF-8";
                }

                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();

                    params.put("user_name", user_name);
                    params.put("pass_word", pass_word);

                    return params;
                }
            };

            queue.add(request);
        });

    }

    private void goToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void goToMangerActivity() {
        Intent intent = new Intent(this, MangerActivity.class);
        startActivity(intent);
    }

    private void goToChefActivity() {
        Intent intent = new Intent(this, ActivityChef.class);
        startActivity(intent);
    }

    public void handleSignUp(View view) {
        startActivity(new Intent(this, SignUpActivity.class));
    }

}