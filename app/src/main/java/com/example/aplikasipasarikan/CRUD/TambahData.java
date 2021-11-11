package com.example.aplikasipasarikan.CRUD;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.aplikasipasarikan.R;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class TambahData extends AppCompatActivity {
    TextInputEditText harga,user;
    AutoCompleteTextView instansi,jenis,status;
    Button submit;
    ProgressDialog progressDialog;

    String idJenis,idStatus, idInstansi;

    ArrayAdapter<String> adapter1;
    ArrayAdapter<String> adapter;
    ArrayAdapter<String> adapter2;

    ArrayList<String> items = new ArrayList<>();
    ArrayList<String> kota = new ArrayList<>();
    HashMap<Integer, String> hashMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_data);

        user = findViewById(R.id.user);
        harga = findViewById(R.id.harga);
        instansi = findViewById(R.id.instansi);
        jenis = findViewById(R.id.jenis);
        status = findViewById(R.id.status);
        submit = findViewById(R.id.submit);

        AndroidNetworking.initialize(TambahData.this);

        // create dropdown for Jenis_ikan

        AndroidNetworking.get("https://testing.sumbarprov.go.id/pasarikan/api/jenis_ikan")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONObject jsonObject = new JSONObject();
                        try {
                            String status = response.getString("status");
                            if (status.equalsIgnoreCase("succes")){
                                // aksi sukses
                                JSONArray respo = response.getJSONArray("response");
                                for (int i = 0; i < respo.length(); i++){
                                    JSONObject obj = respo.getJSONObject(i);
                                    String id_jenis_ikan = obj.getString("id_jenis_ikan");
                                    String title_jenis_ikan = obj.getString("title_jenis_ikan");


                                    items.add(title_jenis_ikan);
                                    hashMap.put(i, id_jenis_ikan);
                                    Log.e("tes", "sukses" + i);
                                }
                                Log.e("tes", "sukses");
                                adapter = new ArrayAdapter<>(TambahData.this, R.layout.dropdown_jenis, items);
                                jenis.setAdapter(adapter);

                            }
                            else{
                                // aksi gagal
                                Toast.makeText(TambahData.this, "Gagal", Toast.LENGTH_SHORT).show();
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

        jenis.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                idJenis = hashMap.get(position);
                Toast.makeText(TambahData.this, idJenis, Toast.LENGTH_SHORT).show();
            }
        });


        // Create dropdown for Kota

        AndroidNetworking.get("https://testing.sumbarprov.go.id/pasarikan/api/produksi_ikan")
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
                                    String id_instansi = obj.getString("id_instansi");
                                    String title_instansi = obj.getString("title_instansi");


                                    kota.add(title_instansi);
                                    hashMap.put(i, id_instansi);
                                    Log.e("tes", "sukses" + i);
                                }
                                Log.e("tes", "sukses");
                                adapter2 = new ArrayAdapter<>(TambahData.this, R.layout.dropdown_kota, kota);
                                instansi.setAdapter(adapter2);

                            }
                            else{
                                // aksi gagal
                                Toast.makeText(TambahData.this, "Gagal", Toast.LENGTH_SHORT).show();
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

        instansi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                idInstansi = hashMap.get(position);
                Toast.makeText(TambahData.this, idInstansi, Toast.LENGTH_SHORT).show();
            }
        });


        // Dropdown for status

        String[] item2 = new String[]{"publish", "not Publish"};
        ArrayList<String> stat = new ArrayList<>();
        stat.add("publish");
        stat.add("not publish");
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(TambahData.this, R.layout.dropdown_status, stat);
        status.setAdapter(adapter1);

        status.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                idStatus = hashMap.get(position);
                Toast.makeText(TambahData.this, idStatus, Toast.LENGTH_SHORT).show();
            }
        });



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });
    }


    // Menginputkan/ menambahkan harga terbaru

    public void submit(){
        AndroidNetworking.post("https://testing.sumbarprov.go.id/pasarikan/api/input_harga_ikan")
                .addBodyParameter("id_user",user.getText().toString())
                .addBodyParameter("id_instansi",idInstansi)
                .addBodyParameter("id_jenis_ikan",idJenis)
                .addBodyParameter("harga",harga.getText().toString())
                .addBodyParameter("id_status",idStatus)
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(response.getString("status").equals("berhasil")){
                                Toast.makeText(getApplicationContext(), "Data Berhasil Disimpan ", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "Data Tidak dapat Disimpan ", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }

                        }catch (JSONException e){
                            Toast.makeText(getApplicationContext(), "Data Gagal Disimpan ! " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        Toast.makeText(getApplicationContext(), "Data tidak bisa Disimpan " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });


    }
}