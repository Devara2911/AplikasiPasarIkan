package com.example.aplikasipasarikan.CRUD;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.example.aplikasipasarikan.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DataHargaAdapter extends RecyclerView.Adapter<DataHargaAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<DataHargaModel> dataHargaModels;
    private ProgressDialog progressDialog;
    private ViewBinderHelper viewBinderHelper = new ViewBinderHelper();

    public DataHargaAdapter(Context mContext, ArrayList<DataHargaModel> dataHargaModels) {
        this.mContext = mContext;
        this.dataHargaModels = dataHargaModels;
    }

    @NonNull
    @Override
    public DataHargaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_tampil_data, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DataHargaAdapter.ViewHolder holder, int position) {
        DataHargaModel item = dataHargaModels.get(position);

        Glide.with(mContext)
                .load(item.getGambar())
                .into(holder.img_item_photo);
        holder.txtname.setText(item.getTitle_jenis_ikan());
        holder.txtstatus.setText(item.getNm_status());
        holder.txtharga.setText(item.getEceran());
        holder.txtkabkota.setText(item.getTitle_instansi());
        holder.txttgl.setText(item.getCreated_modified());

        holder.layoutEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent kirimData = new Intent(mContext,UpdateHarga.class);
                kirimData.putExtra("title_jenis_ikan", item.getTitle_jenis_ikan());
                kirimData.putExtra("title_instansi", item.getTitle_instansi());
                kirimData.putExtra("eceran", item.getEceran());
                kirimData.putExtra("created_modified", item.getCreated_modified());
                kirimData.putExtra("nm_status", item.getNm_status());
                mContext.startActivity(kirimData);
            }
        });


        holder.layoutDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialAlertDialogBuilder(mContext)
                        .setTitle("Konfirmasi Hapus")
                        .setMessage("Data akan dihapus, lanjutkan?")
                        .setPositiveButton("Lanjutkan", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                holder.hapusData();
                                dataHargaModels.remove(holder.getAdapterPosition());
                                notifyItemRemoved(holder.getAdapterPosition());

                            }
                        })
                        .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {

                            }
                        })
                        .show();
            }
        });


        holder.item = item;


    }

    @Override
    public int getItemCount() {
        return dataHargaModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_item_photo;
        TextView txtname,txtkabkota,txtharga,txttgl,txtstatus;
        LinearLayout layoutDelete,layoutEdit;
        DataHargaModel item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img_item_photo = itemView.findViewById(R.id.img_item_photo);
            txtname = itemView.findViewById(R.id.txtname);
            txtkabkota = itemView.findViewById(R.id.txtkabkota);
            txtharga = itemView.findViewById(R.id.txtharga);
            txttgl = itemView.findViewById(R.id.txttgl);
            txtstatus = itemView.findViewById(R.id.txtstatus);
            layoutDelete = itemView.findViewById(R.id.layoutDelete);
            layoutEdit = itemView.findViewById(R.id.layoutEdit);






        }

        public void hapusData(){
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            AndroidNetworking.post("https://testing.sumbarprov.go.id/pasarikan/api/delete_harga_ikan?id_harga_ikan=1831")
                    .addBodyParameter("id_harga_ikan", item.getEceran())
                    .setTag("test")
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                if(response.getString("status").equals("success")){
                                    Toast.makeText(mContext, "Data Berhasil Dihapus ", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }
                                else {
                                    Toast.makeText(mContext, "Data Gagal Dihapus ", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }

                            }catch (JSONException e){
                                Toast.makeText(mContext, "Data Gagal Dihapus " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        }
                        @Override
                        public void onError(ANError error) {
                            Toast.makeText(mContext, "Data tidak bisa Disimpan " + error.getMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    });

        }



    }




}
