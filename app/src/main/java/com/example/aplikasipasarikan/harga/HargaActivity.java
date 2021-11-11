package com.example.aplikasipasarikan.harga;

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

public class HargaActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    EditText searchview;
    ArrayList<HargaModel> hargaModelArrayList;
    HargaAdapter hargaAdapter;
    SwipeRefreshLayout refreshLayout;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_harga);

        recyclerView = findViewById(R.id.rv_harga);
        recyclerView.setHasFixedSize(true);
        searchview = findViewById(R.id.seacrhHarga);
        refreshLayout = findViewById(R.id.swipeRefresh);

        AndroidNetworking.initialize(HargaActivity.this);


        hargaModelArrayList = new ArrayList<>();
        hargaAdapter = new HargaAdapter(HargaActivity.this,hargaModelArrayList);
        recyclerView.setAdapter(hargaAdapter);


        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(false);
                Collections.shuffle(hargaModelArrayList, new Random(System.currentTimeMillis()));
                GetData();

            }
        });

        searchview.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                hargaAdapter.getFilter().filter(s);

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
        AndroidNetworking.get("https://testing.sumbarprov.go.id/pasarikan/api/harga_ikan")
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


                                    hargaModelArrayList.add(new HargaModel(
                                            title_jenis_ikan, title_instansi, eceran, created_modified, gambar
                                    ));
                                }
                                hargaAdapter.HargaAdapterFull(hargaModelArrayList);
                                hargaAdapter.notifyDataSetChanged();
                                progressDialog.dismiss();
                            }
                            else{
                                // aksi gagal
                                Toast.makeText(HargaActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
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