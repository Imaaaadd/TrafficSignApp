package com.carassistant;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.carassistant.helpers.PhoneHelper;
import com.carassistant.helpers.StringHelper;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    EditText nom, email, password, tel;
    Button signupbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        nom = findViewById(R.id.nom);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        tel = findViewById(R.id.tel);

        signupbtn = findViewById(R.id.signupbtn);

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processFormFields();
            }
        });
    }

    public void goToHomeAct(View view) {
        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void goToSignInAct(View view) {
        Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
        startActivity(intent);
        finish();
    }

    public void processFormFields() {
        if (!validateNom() || !validateEmail() || !validatePassword() || !validateTel()) {
            return;
        }

        RequestQueue queue = Volley.newRequestQueue(SignUpActivity.this);
        String url = "http://172.20.10.2:9080/api/user/register";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equalsIgnoreCase("Succés")) {
                    nom.setText(null);
                    email.setText(null);
                    password.setText(null);
                    tel.setText(null);
                    Toast.makeText(SignUpActivity.this, "Création du compte avec succés", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                System.out.println(error.getMessage());
                Toast.makeText(SignUpActivity.this, "Création du compte a échoué", Toast.LENGTH_LONG).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("nom", nom.getText().toString());
                params.put("email", email.getText().toString());
                params.put("password", password.getText().toString());
                params.put("tel", tel.getText().toString());
                return params;
            }
        };

        queue.add(stringRequest);

    }

    public boolean validateNom() {
        String nom = this.nom.getText().toString();
        if (nom.isEmpty()) {
            this.nom.setError("Le nom ne doit pas etre vide!");
            return false;
        } else {
            this.nom.setError(null);
            return true;
        }
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

    public boolean validateTel() {
        String tel = this.tel.getText().toString();
        if (tel.isEmpty()) {
            this.tel.setError("Le numéro de téléphone ne doit pas etre vide!");
            return false;
        } else if (!PhoneHelper.regexPhoneValidationPattern(tel)) {
            this.tel.setError("Le numéro de téléphone doit contient 10 chiffres");
            return false;
        } else {
            this.tel.setError(null);
            return true;
        }
    }

}