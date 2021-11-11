package com.example.aplikasipasarikan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.aplikasipasarikan.auth.LoginActivity;
import com.example.aplikasipasarikan.harga.HargaActivity;
import com.example.aplikasipasarikan.nonkonsumsi.NonKonsumsiActivity;
import com.example.aplikasipasarikan.produksi.ProduksiActivity;
import com.example.aplikasipasarikan.rm.RmActivity;
import com.example.aplikasipasarikan.umkm.UmkmActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Dashboard extends AppCompatActivity {
    public static final String USER_EXTRA = "USER_EXTRA";
    BottomNavigationView bottomMenu;
    LinearLayout harga,produksi,umkm,rm,nonkonsumsi,info;
    RecyclerView recyclerView;
    ArrayList<DashboardModel> dashboardModelArrayList;
    RecyclerView.LayoutManager layoutManager;
    DashboardAdapter dashboardAdapter;
    Dialog about;
    Button btnclose;
    TextView tv;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        harga = findViewById(R.id.harga);
        produksi = findViewById(R.id.produksi);
        umkm = findViewById(R.id.umkm);
        rm = findViewById(R.id.rm);
        nonkonsumsi = findViewById(R.id.nonkonsumsi);
        info = findViewById(R.id.info);
        recyclerView = findViewById(R.id.rv_update);
        bottomMenu = findViewById(R.id.bottomMenu);
        tv = findViewById(R.id.tv);
        tv.setSelected(true);


        AndroidNetworking.initialize(Dashboard.this);

        layoutManager = new LinearLayoutManager(Dashboard.this,RecyclerView.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        dashboardModelArrayList = new ArrayList<>();
        dashboardAdapter = new DashboardAdapter(Dashboard.this,dashboardModelArrayList);
        recyclerView.setAdapter(dashboardAdapter);



        bottomMenu.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:

                    case R.id.akun:
                        startActivity(new Intent(Dashboard.this, LoginActivity.class));
                        break;
                }
                return true;
            }
        });

        harga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Dashboard.this, HargaActivity.class);
                startActivity(i);
            }
        });

        produksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Dashboard.this, ProduksiActivity.class);
                startActivity(i);
            }
        });


        umkm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Dashboard.this, UmkmActivity.class);
                startActivity(i);
            }
        });

        rm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Dashboard.this, RmActivity.class);
                startActivity(i);
            }
        });

        nonkonsumsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Dashboard.this, NonKonsumsiActivity.class);
                startActivity(i);
            }
        });

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                about = new Dialog(info.getContext());
                about.setContentView(R.layout.popup_about);
                btnclose = about.findViewById(R.id.btnclose);
                btnclose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getApplicationContext(),Dashboard.class));
                    }
                });

                about.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                about.show();
            }
        });


        GetData();


    }

    private void GetData() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        AndroidNetworking.post("https://testing.sumbarprov.go.id/pasarikan/api/harga_ikan")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONObject jsonObject = new JSONObject();
                        try {
                            String status = response.getString("status");
                            if (status.equalsIgnoreCase("success")){
                                // aksi sukses
                                JSONArray respo = response.getJSONArray("response");
                                for (int i = 0; i < respo.length(); i++){
                                    JSONObject obj = respo.getJSONObject(i);
                                    String title_jenis_ikan = obj.getString("title_jenis_ikan");
                                    String title_instansi = obj.getString("title_instansi");
                                    String eceran = obj.getString("eceran");
                                    String created_modified = obj.getString("created_modified");
                                    String gambar = obj.getString("gambar");

                                    dashboardModelArrayList.add(new DashboardModel(title_jenis_ikan,title_instansi,eceran,created_modified,gambar));


                                }
                                dashboardAdapter.notifyDataSetChanged();
                                progressDialog.dismiss();
                            }
                            else{
                                // aksi gagal
                                Toast.makeText(Dashboard.this, "Data gagal di load", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        anError.printStackTrace();

                    }
                });

    }

}