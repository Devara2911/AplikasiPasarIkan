package com.example.aplikasipasarikan.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.aplikasipasarikan.CRUD.DataHarga;
import com.example.aplikasipasarikan.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    MaterialButton loginBtn, createAccountBtn;
    TextInputEditText Editusrn, Editpass;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBtn = (MaterialButton) findViewById(R.id.loginBtn);
        createAccountBtn = (MaterialButton) findViewById(R.id.createAccountBtn);
        Editusrn = (TextInputEditText) findViewById(R.id.Editusrn);
        Editpass = (TextInputEditText) findViewById(R.id.Editpass);

        createAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    public void Login(View view){
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
        String url = "https://testing.sumbarprov.go.id/pasarikan/api/login";

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        Log.d("APPLOG",response);
                        parseJson(response);

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Log.d("APPLOG",error.toString());
                    }
                }
        ){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String,String>();
                params.put("username",Editusrn.getText().toString().trim());
                params.put("password",Editpass.getText().toString().trim());
                return params;


            }
        };
        queue.add(postRequest);
        progressDialog.dismiss();
    }

    public void parseJson(String jsonString){
        JSONObject obj = null;
        try {
            obj = new JSONObject(jsonString);
            if (obj.get("status").equals("success")){
                Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, DataHarga.class);
                startActivity(intent);
            } else {
                Toast.makeText(LoginActivity.this, "Login Error", Toast.LENGTH_SHORT).show();
            }
        }
        catch (JSONException e){
            progressDialog.dismiss();

        }
    }
}