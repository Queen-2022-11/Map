package com.example.mymap.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymap.Model.Park;
import com.example.mymap.ParksFragment;
import com.example.mymap.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ParkRecycleViewAdapter extends RecyclerView.Adapter<ParkRecycleViewAdapter.ViewHolder> {
    private final List<Park> parkList;
    private final OnparkClickListener onparkClickListener;


    public ParkRecycleViewAdapter(List<Park> parkList, OnparkClickListener onparkClickListener) {
        this.parkList = parkList;
        this.onparkClickListener=onparkClickListener;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_parks,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Park park = parkList.get(position);
        holder.parkName.setText(park.getName());
        holder.parkType.setText(park.getDesignation());
        holder.parkState.setText(park.getStates());
        if(park.getImages().size()>0){
            Picasso.get().load(park.getImages().get(0).getUrl()).resize(400,400).placeholder(android.R.drawable.stat_sys_download).error(android.R.drawable.stat_notify_error).centerCrop().into(holder.parkImage);
        }

    }

    @Override
    public int getItemCount() {
        return parkList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView parkImage;
        public TextView parkName;
        public TextView parkType;
        public TextView parkState;
        OnparkClickListener parkClickListener;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parkImage=itemView.findViewById(R.id.imageView);
            parkName=itemView.findViewById(R.id.textView);
            parkType=itemView.findViewById(R.id.textView2);
            parkState=itemView.findViewById(R.id.textView3);
            this.parkClickListener=onparkClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
           Park currentPark = parkList.get(getAdapterPosition());
           parkClickListener.onParkClick(currentPark);
        }
    }
}
