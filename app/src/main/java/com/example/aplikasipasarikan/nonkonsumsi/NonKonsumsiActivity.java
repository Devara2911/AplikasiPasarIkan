package com.example.aplikasipasarikan.nonkonsumsi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.aplikasipasarikan.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class NonKonsumsiActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    EditText searchview;
    ArrayList<NonKonsumsiModel> nonKonsumsiModelArrayList;
    NonKonsumsiAdapter nonKonsumsiAdapter;
    SwipeRefreshLayout refreshLayout;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_non_konsumsi);

        recyclerView = findViewById(R.id.rv_nonkonsumsi);
        recyclerView.setHasFixedSize(true);
        searchview = findViewById(R.id.seacrhHarga);
        refreshLayout = findViewById(R.id.swipeRefresh);

        AndroidNetworking.initialize(NonKonsumsiActivity.this);

        recyclerView = findViewById(R.id.rv_nonkonsumsi);
        recyclerView.setHasFixedSize(true);
        nonKonsumsiModelArrayList = new ArrayList<>();
        nonKonsumsiAdapter = new NonKonsumsiAdapter(NonKonsumsiActivity.this, nonKonsumsiModelArrayList);
        recyclerView.setAdapter(nonKonsumsiAdapter);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(false);
                Collections.shuffle(nonKonsumsiModelArrayList, new Random(System.currentTimeMillis()));
                GetData();

            }
        });

        searchview.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                nonKonsumsiAdapter.getFilter().filter(s);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        GetData();
    }

    private void GetData() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        AndroidNetworking.get("https://testing.sumbarprov.go.id/pasarikan/api/non_konsumsi")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String status = response.getString("status");
                            if (status.equalsIgnoreCase("success")){
                                // aksi sukses
                                JSONArray respo = response.getJSONArray("response");
                                for (int i = 0; i < respo.length(); i++){
                                    JSONObject obj = respo.getJSONObject(i);
                                    String nama_nonkonsumsi = obj.getString("nama_nonkonsumsi");
                                    String alamat = obj.getString("alamat");
                                    String title_kelurahan = obj.getString("title_kelurahan");
                                    String title_kecamatan = obj.getString("title_kecamatan");
                                    String title_kabkota = obj.getString("title_kabkota");
                                    String jumlah_anggota = obj.getString("jumlah_anggota");

                                    nonKonsumsiModelArrayList.add(new NonKonsumsiModel(nama_nonkonsumsi, alamat, title_kelurahan, title_kecamatan, title_kabkota, jumlah_anggota));


                                }
                                nonKonsumsiAdapter.NonKonsumsiAdapterFull(nonKonsumsiModelArrayList);
                                nonKonsumsiAdapter.notifyDataSetChanged();
                                progressDialog.dismiss();
                            }
                            else{
                                // aksi gagal
                                Toast.makeText(NonKonsumsiActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
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