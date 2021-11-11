package com.example.aplikasipasarikan.CRUD;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.aplikasipasarikan.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class UpdateHarga extends AppCompatActivity {
    TextInputEditText Updhrg;
    String harga,jenis,instansi;
    AutoCompleteTextView Updjenis, Updinstansi;
    MaterialButton btnUpdate;

    String idJenis;

    ArrayAdapter<String> adapter;
    ArrayList<String> items = new ArrayList<>();
    HashMap<Integer, String> hashMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_harga);

        Updhrg = findViewById(R.id.UPdharga);
        Updinstansi = findViewById(R.id.Updinstansi);
        Updjenis = findViewById(R.id.Updjenis);
        btnUpdate = findViewById(R.id.btnUpdate);

        AndroidNetworking.initialize(UpdateHarga.this);

        Intent data = getIntent();
        jenis = data.getStringExtra("title_jenis_ikan");
        instansi = data.getStringExtra("title_instansi");
        harga = data.getStringExtra("eceran");

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit();
            }
        });

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
                                adapter = new ArrayAdapter<>(UpdateHarga.this, R.layout.dropdown_jenis, items);
                                Updjenis.setAdapter(adapter);

                            }
                            else{
                                // aksi gagal
                                Toast.makeText(UpdateHarga.this, "Gagal", Toast.LENGTH_SHORT).show();
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

        Updjenis.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                idJenis = hashMap.get(position);
                Toast.makeText(UpdateHarga.this, idJenis, Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void edit() {
        if(Updhrg.getText().toString().equals("")|| Updinstansi.getText().toString().equals("")|| Updjenis.getText().toString().equals(""))
        {
            Toast.makeText(this, "Data Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
        }else
        {
            AndroidNetworking.post("https://testing.sumbarprov.go.id/pasarikan/api/edit_harga_ikan")
                    .addBodyParameter("id_harga_ikan",Updhrg.getText().toString())
                    .addBodyParameter("harga",Updhrg.getText().toString())
                    .addBodyParameter("id_jenis_ikan", Updjenis.getText().toString())
                    .setTag("test")
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                if(response.getString("status").equalsIgnoreCase("success")){
                                    Toast.makeText(getApplicationContext(), "Data Berhasil DiUpdate ", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(getApplicationContext(), "Data Gagal DiUpdate ", Toast.LENGTH_SHORT).show();
                                }

                            }catch (JSONException e){
                                Toast.makeText(getApplicationContext(), "Data Gagal DiUpdate " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onError(ANError error) {
                            Toast.makeText(getApplicationContext(), "Data Gagal DiUpdate " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }

    }
}