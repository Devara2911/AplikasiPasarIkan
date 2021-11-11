package com.example.aplikasipasarikan.produksi;

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

public class ProduksiActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    EditText searchview;
    ArrayList<ProduksiModel> mExampleList;
    ProduksiAdapter mExampleAdapter;
    SwipeRefreshLayout refreshLayout;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produksi);

        recyclerView = findViewById(R.id.rv_produksi);
        recyclerView.setHasFixedSize(true);
        searchview = findViewById(R.id.seacrhHarga);
        refreshLayout = findViewById(R.id.swipeRefresh);

        AndroidNetworking.initialize(ProduksiActivity.this);

        recyclerView = findViewById(R.id.rv_produksi);
        recyclerView.setHasFixedSize(true);
        mExampleList = new ArrayList<>();
        mExampleAdapter = new ProduksiAdapter(ProduksiActivity.this, mExampleList);
        recyclerView.setAdapter(mExampleAdapter);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(false);
                Collections.shuffle(mExampleList, new Random(System.currentTimeMillis()));
                GetData();

            }
        });

        searchview.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mExampleAdapter.getFilter().filter(s);
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
        AndroidNetworking.get("https://testing.sumbarprov.go.id/pasarikan/api/produksi_ikan")
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
                                    String title_jenis_ikan = obj.getString("title_jenis_ikan");
                                    String title_instansi = obj.getString("title_instansi");
                                    String gambar = obj.getString("gambar");
                                    String volume_kg = obj.getString("volume_kg");
                                    String title_perikanan = obj.getString("title_perikanan");
                                    String created_modified = obj.getString("created_modified");

                                    mExampleList.add(new ProduksiModel(
                                            title_jenis_ikan, title_instansi, gambar, volume_kg, created_modified, title_perikanan
                                    ));
                                }
                                mExampleAdapter.ProduksiAdapterrFull(mExampleList);
                                mExampleAdapter.notifyDataSetChanged();
                                progressDialog.dismiss();
                            }
                            else{
                                // aksi gagal
                                Toast.makeText(ProduksiActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
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