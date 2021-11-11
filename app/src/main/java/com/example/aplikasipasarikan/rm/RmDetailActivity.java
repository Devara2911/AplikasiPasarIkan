package com.example.aplikasipasarikan.rm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.aplikasipasarikan.R;
import com.example.aplikasipasarikan.umkm.UmkmDetailActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RmDetailActivity extends AppCompatActivity {
    TextView nama,bahan,hasil,asal;
    String idnama,idbahan,idhasil,idasal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rm_detail);

        nama = findViewById(R.id.name);
        bahan = findViewById(R.id.bahan);
        hasil = findViewById(R.id.hasil);
        asal = findViewById(R.id.asal);

        Intent intent = getIntent();
        idnama = intent.getStringExtra("nama_umkm");
        idbahan = intent.getStringExtra("bahan_baku");
        idhasil = intent.getStringExtra("nama_hasil_produk");
        idasal = intent.getStringExtra("asal_bahan_baku");

        nama.setText(idnama);
        bahan.setText(idbahan);
        hasil.setText(idhasil);
        asal.setText(idasal);

        AndroidNetworking.initialize(RmDetailActivity.this);

        GetData();
    }

    private void GetData() {
        AndroidNetworking.get("https://testing.sumbarprov.go.id/pasarikan/api/produksi_rumah_makan?rumah_makan=37")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String status = response.getString("status");
                            if (status.equalsIgnoreCase("success")) {
                                // aksi sukses
                                JSONArray respo = response.getJSONArray("response");
                                for (int i = 0; i < respo.length(); i++) {
                                    JSONObject obj = respo.getJSONObject(i);
                                    String nama_umkm = obj.getString("nama_umkm");
                                    String bahan_baku = obj.getString("bahan_baku");
                                    String nama_hasil_produk = obj.getString("nama_hasil_produk");
                                    String produk_hasil_olahan = obj.getString("produk_hasil_olahan");
                                    String asal_bahan_baku = obj.getString("asal_bahan_baku");

                                    Intent intent = new Intent(getApplicationContext(), RmDetailActivity.class);
                                    intent.putExtra("nama_umkm",nama_umkm);
                                    intent.putExtra("bahan_baku",bahan_baku);
                                    intent.putExtra("nama_hasil_produk",nama_hasil_produk);
                                    intent.putExtra("produk_hasil_olahan",produk_hasil_olahan);
                                    intent.putExtra("asal_bahan_baku",asal_bahan_baku);
                                    startActivity(intent);


                                }

                            } else {
                                // aksi gagal
                                Toast.makeText(RmDetailActivity.this, "gagal", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        anError.printStackTrace();

                    }
                });
    }
}