package com.example.aplikasipasarikan.CRUD;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.aplikasipasarikan.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DataHarga extends AppCompatActivity {
    Button tambah;
    RecyclerView recyclerView;
    ArrayList<DataHargaModel> dataHargaModel;
    DataHargaAdapter dataHargaAdapter;
    RecyclerView.LayoutManager layoutManager;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_harga);

        tambah = findViewById(R.id.tambah);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        dataHargaModel = new ArrayList<>();

        layoutManager = new LinearLayoutManager(DataHarga.this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        dataHargaAdapter = new DataHargaAdapter(DataHarga.this,dataHargaModel);
        recyclerView.setAdapter(dataHargaAdapter);

        AndroidNetworking.initialize(DataHarga.this);

        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TambahData.class);
                startActivity(intent);
            }
        });

        LoadData();

    }
    public void LoadData(){
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
                                    String nm_status = obj.getString("nm_status");

                                    dataHargaModel.add(new DataHargaModel(title_jenis_ikan,title_instansi,eceran,created_modified,gambar,nm_status));



                                }
                                dataHargaAdapter.notifyDataSetChanged();
                                progressDialog.dismiss();
                            }
                            else{
                                // aksi gagal
                                Toast.makeText(DataHarga.this, "Data gagal di load", Toast.LENGTH_SHORT).show();
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