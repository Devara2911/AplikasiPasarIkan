package com.example.aplikasipasarikan.harga;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.aplikasipasarikan.Dashboard;
import com.example.aplikasipasarikan.R;

import java.util.ArrayList;

public class HargaAdapter extends RecyclerView.Adapter<HargaAdapter.ViewHolder> implements Filterable {
    private Context mContext;
    private ArrayList<HargaModel> hargaModels;
    private ArrayList<HargaModel> hargaModelsFull;
    private HargaAdapter.OnItemClickListener mlistener;
    private int lastPosition = -1;

    public HargaAdapter(HargaActivity context, ArrayList<HargaModel> hargaModelArrayList) {
        mContext = context;
        hargaModels = hargaModelArrayList;
        hargaModelsFull = new ArrayList<>(hargaModelArrayList);

    }


    public void HargaAdapterFull(ArrayList<HargaModel> hargaModelArrayList) {
        hargaModelsFull.clear();
        hargaModelsFull = new ArrayList<>(hargaModelArrayList);
        notifyDataSetChanged();
    }

    public void OnItemClickListener (HargaAdapter.OnItemClickListener listener){
        mlistener = listener;
    }

    @Override
    public Filter getFilter() {
        return hargaFilter;
    }

    private Filter hargaFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<HargaModel> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0){
                filteredList.addAll(hargaModelsFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (HargaModel item : hargaModelsFull){
                    if (item.getTitle_jenis_ikan().toLowerCase().contains(filterPattern) || item.getTitle_instansi().toLowerCase().contains(filterPattern) || item.getTitle_instansi().toLowerCase().contains(filterPattern)){ //sesuaikan dengan field yang akan di cari, sesuai dengan getter di class item
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
            hargaModels.clear();
            hargaModels.addAll((ArrayList)results.values);
            notifyDataSetChanged();

        }
    };

    public interface OnItemClickListener{
        void onItemClick(String id, String nama);
    }

    @NonNull
    @Override
    public HargaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_list_harga, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HargaAdapter.ViewHolder holder, int position) {
        final HargaModel currentItem = hargaModels.get(position);

        String title_jenis_ikan = currentItem.getTitle_jenis_ikan();
        String title_instansi = currentItem.getTitle_instansi();
        String gambar = currentItem.getGambar();
        String eceran = currentItem.getEceran();
        String created_modified = currentItem.getCreated_modified();

        Glide.with(mContext)
                .load(currentItem.getGambar())
                .into(holder.img_item_photo);
        holder.txtname.setText(title_jenis_ikan);
        holder.txtharga.setText(eceran);
        holder.txtkabkota.setText(title_instansi);
        holder.txttgl.setText(created_modified);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                mListener.onItemClick(id, nama);
            }
        });

    }

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
        return hargaModels.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView
                img_item_photo;

        TextView
                txtname, txtharga, txtkabkota, txttgl;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtname = itemView.findViewById(R.id.txtname);
            txtharga = itemView.findViewById(R.id.txtharga);
            txtkabkota = itemView.findViewById(R.id.txtkabkota);
            txttgl = itemView.findViewById(R.id.txttgl);
            img_item_photo = itemView.findViewById(R.id.img_item_photo);
        }
    }


}
