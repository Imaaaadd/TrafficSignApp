package com.carassistant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.carassistant.helpers.StringHelper;
import com.carassistant.ui.activities.CameraActivity;
import com.carassistant.ui.activities.DetectorActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class SignInActivity extends AppCompatActivity {

    Button loginbtn;
    EditText email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        loginbtn = findViewById(R.id.loginbtn);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authenticateUser();
            }
        });
    }

    public void authenticateUser() {
        if (!validateEmail() || !validatePassword()) {
            return;
        }

        RequestQueue queue = Volley.newRequestQueue(SignInActivity.this);
        String url = "http://172.20.10.2:9080/api/user/login";

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("email", email.getText().toString());
        params.put("password", password.getText().toString());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String nom = (String) response.get("nom");
                            String email = (String) response.get("email");
                            String tel = (String) response.get("tel");

                            Intent goToCamera = new Intent(SignInActivity.this, DetectorActivity.class);
                            startActivity(goToCamera);
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            System.out.println(e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                System.out.println(error.getMessage());
                Toast.makeText(SignInActivity.this, "Login a échoué", Toast.LENGTH_LONG).show();
            }
        });

        queue.add(jsonObjectRequest);

    }

    public void goToHome(View view) {
        Intent intent = new Intent(SignInActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void goToSignUpAct(View view) {
        Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
        startActivity(intent);
        finish();
    }

    public boolean validateEmail() {
        String email = this.email.getText().toString();
        if (email.isEmpty()) {
            this.email.setError("L'email ne doit pas etre vide!");
            return false;
        } else if (!StringHelper.regexEmailValidationPattern(email)) {
            this.email.setError("Svp saisir un email valid");
            return false;
        } else {
            this.email.setError(null);
            return true;
        }
    }

    public boolean validatePassword() {
        String password = this.password.getText().toString();
        if (password.isEmpty()) {
            this.password.setError("Le mot de passe ne doit pas etre vide!");
            return false;
        } else {
            this.password.setError(null);
            return true;
        }
    }

}