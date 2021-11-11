package com.example.aplikasipasarikan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.ViewHolder> {
    private Context context;
    private ArrayList<DashboardModel> dashboardModels;

    public DashboardAdapter(Context context, ArrayList<DashboardModel> dashboardModels) {
        this.context = context;
        this.dashboardModels = dashboardModels;
    }

    @NonNull
    @Override
    public DashboardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.fish_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DashboardAdapter.ViewHolder holder, int position) {
        final DashboardModel item = dashboardModels.get(position);

        Glide.with(context)
                .load(item.getGambar())
                .into(holder.fishImage);

        holder.txtname.setText(item.getTitle_jenis_ikan());
        holder.txtharga.setText(item.getEceran());
        holder.txttglupdt.setText(item.getCreated_modified());

        holder.item = item;

    }

    @Override
    public int getItemCount() {
        return dashboardModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView fishImage;
        TextView txtname,txtkabkota,txtharga,txttgl,txttglupdt,txtlogin;
        RecyclerView rv_update;
        RelativeLayout relativeLayout;
        DashboardModel item;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fishImage = itemView.findViewById(R.id.fishImage);
            txtname = itemView.findViewById(R.id.txtname);
            txtharga = itemView.findViewById(R.id.txtharga);
            txttglupdt = itemView.findViewById(R.id.txttglupdt);
            txttgl = itemView.findViewById(R.id.txttgl);
            relativeLayout = itemView.findViewById(R.id.relativeLayout);
            rv_update = itemView.findViewById(R.id.rv_update);
        }
    }
}
