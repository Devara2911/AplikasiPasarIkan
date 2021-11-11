package com.example.aplikasipasarikan.rm;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplikasipasarikan.R;

import java.util.ArrayList;

public class RmAdapter extends RecyclerView.Adapter<RmAdapter.ViewHolder> implements Filterable {
    private Context mContext;
    private ArrayList<RmModel> rmModels;
    private ArrayList<RmModel> rmModelFull;
    private RmAdapter.OnItemClickListener mlistener;
    private int lastPosition = -1;

    public RmAdapter(RmActivity context, ArrayList<RmModel> rmModelArrayList) {
        mContext = context;
        rmModels = rmModelArrayList;
        rmModelFull = new ArrayList<>(rmModelArrayList);
    }


    public void RmAdapterFull(ArrayList<RmModel> rmModelArrayList) {
        rmModelFull.clear();
        rmModelFull = new ArrayList<>(rmModelArrayList);
        notifyDataSetChanged();
    }

    public void OnItemClickListener(RmAdapter.OnItemClickListener listener) {
        mlistener = listener;

    }


    @Override
    public Filter getFilter() {
        return RmFilter;
    }

    private Filter RmFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<RmModel> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0){
                filteredList.addAll(rmModelFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (RmModel item : rmModelFull){
                    if (item.getNama_umkm().toLowerCase().contains(filterPattern) || item.getAlamat().toLowerCase().contains(filterPattern) || item.getTitle_kelurahan().toLowerCase().contains(filterPattern) || item.getTitle_kabkota().toLowerCase().contains(filterPattern)){ //sesuaikan dengan field yang akan di cari, sesuai dengan getter di class item
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            rmModels.clear();
            rmModels.addAll((ArrayList)results.values);
            notifyDataSetChanged();

        }
    };

    public interface OnItemClickListener{
        void onItemClick(String id, String nama);
    }



    @NonNull
    @Override
    public RmAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_list_rm, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RmAdapter.ViewHolder holder, int position) {
        final RmModel item = rmModels.get(position);

        String nama_umkm = item.getNama_umkm();
        String alamat = item.getAlamat();
        String title_kelurahan = item.getTitle_kelurahan();
        String title_kecamatan = item.getTitle_kecamatan();
        String title_kabkota =item.getTitle_kabkota();
        String jumlah_anggota = item.getJumlah_anggota();

        holder.txtname.setText(nama_umkm);
        holder.txtalamat.setText(alamat);
        holder.txtkel.setText(title_kelurahan);
        holder.txtkec.setText(title_kecamatan);
        holder.txtkabkota.setText(title_kabkota);
        holder.txtjumlah.setText(jumlah_anggota);

        holder.btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext.getApplicationContext(), RmDetailActivity.class);
                intent.putExtra("title", rmModels.get(position).getNama_umkm());
                mContext.startActivity(intent);
            }
        });

    }

    //caps sentence
    private String getCapsSentences(String text){
        if (text.equals("") || text.isEmpty()){
            return "";
        }
        else{
            String output = "";

            // Make array of word and cap
            String[] textArray = text.trim().split("\\s+");
            for (int i = 0; i < textArray.length; i++){
                textArray[i] = textArray[i].substring(0,1).toUpperCase() + textArray[i].substring(1);
            }

            // convert the array to string
            for(int i = 0; i < textArray.length; i++){
                output = output + textArray[i] + " ";
            }

            return output.trim();
        }
    }

    @Override
    public int getItemCount() {
        return rmModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_item_photo;
        TextView txtname, txtalamat, txtkel, txtkec,txtkabkota, txtjumlah;
        Button btnDetail;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtname = itemView.findViewById(R.id.txtname);
            txtalamat = itemView.findViewById(R.id.txtalamat);
            txtkel = itemView.findViewById(R.id.txtkel);
            txtkec = itemView.findViewById(R.id.txtkec);
            txtkabkota = itemView.findViewById(R.id.txtkabkota);
            txtjumlah = itemView.findViewById(R.id.txtjumlah);
            img_item_photo = itemView.findViewById(R.id.img_item_photo);
            btnDetail = itemView.findViewById(R.id.btnDetail);



        }
    }
}
