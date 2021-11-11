package com.example.aplikasipasarikan.produksi;

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
import com.example.aplikasipasarikan.R;

import java.util.ArrayList;

public class ProduksiAdapter extends RecyclerView.Adapter<ProduksiAdapter.ItemListVHProduksi> implements Filterable {
    private Context mContext;
    private ArrayList<ProduksiModel> modelData;
    private ArrayList<ProduksiModel> modelDataFull;
    private ProduksiAdapter.OnItemClickListener mListener;
    private int lastPosition = -1;

    public ProduksiAdapter(Context context, ArrayList<ProduksiModel> exampleList){
        mContext = context;
        modelData = exampleList;
        modelDataFull = new ArrayList<>(exampleList);
    }

    public void ProduksiAdapterrFull(ArrayList<ProduksiModel> exampleList){
        modelDataFull.clear();
        modelDataFull = new ArrayList<>(exampleList);
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(ProduksiAdapter.OnItemClickListener listener){
        mListener = listener;
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() { //method filter
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<ProduksiModel> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0){
                filteredList.addAll(modelDataFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (ProduksiModel item : modelDataFull){
                    if (item.getTitle_jenis_ikan().toLowerCase().contains(filterPattern) || item.getTitle_instansi().toLowerCase().contains(filterPattern) ){ //sesuaikan dengan field yang akan di cari, sesuai dengan getter di class item
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
            modelData.clear();
            modelData.addAll((ArrayList)results.values);
            notifyDataSetChanged();
        }
    };

    public interface OnItemClickListener{
        void onItemClick(String id, String nama);
    }


    @NonNull
    @Override
    public ProduksiAdapter.ItemListVHProduksi onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_list_produksi, viewGroup, false);
        return new ProduksiAdapter.ItemListVHProduksi(v);
    }

    @Override
    public void onBindViewHolder(final ProduksiAdapter.ItemListVHProduksi holder, final int i) {
        final ProduksiModel currentItem = modelData.get(i);

        String title_jenis_ikan = currentItem.getTitle_jenis_ikan();
        String title_instansi = currentItem.getTitle_instansi();
        String volume_kg = currentItem.getVolume_kg();
        String title_perikanan = currentItem.getTitle_perikanan();
        String gambar = currentItem.getGambar();
        String created_modified = currentItem.getCreated_modified();

        Glide.with(mContext)
                .load(currentItem.getGambar())
                .into(holder.img_item_photo);
        holder.txtname.setText(title_jenis_ikan);
        holder.txtvolume.setText(volume_kg);
        holder.txtjenis.setText(title_perikanan);
        holder.txtkabkota.setText(title_instansi);
        holder.txttgl.setText(created_modified);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                mListener.onItemClick(id, nama);
            }
        });

//        setAnimation(holder.itemView, i);
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


//    private void setAnimation(View viewToAnimate, int position)
//    {
//        // If the bound view wasn't previously displayed on screen, it's animated
//        {
//            Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.item_popup);
//            viewToAnimate.startAnimation(animation);
//            lastPosition = position;
//        }
//    }

    @Override
    public int getItemCount() {
        return modelData.size();
    }

    public class ItemListVHProduksi extends RecyclerView.ViewHolder {
        ImageView img_item_photo;
        TextView txtname, txtjenis, txttgl, txtvolume,txtkabkota;

        public ItemListVHProduksi(@NonNull View itemView) {
            super(itemView);
            txtname = itemView.findViewById(R.id.txtname);
            txtjenis = itemView.findViewById(R.id.txtjenis);
            txtvolume = itemView.findViewById(R.id.txtvolume);
            txtkabkota = itemView.findViewById(R.id.txtkabkota);
            txttgl = itemView.findViewById(R.id.txttgl);

            img_item_photo = itemView.findViewById(R.id.img_item_photo);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null){
//                        int position = getAdapterPosition();
//                        if (position != RecyclerView.NO_POSITION){
//                            mListener.onItemClick(position);
//                        }
                    }
                }
            });
        }
    }
}
