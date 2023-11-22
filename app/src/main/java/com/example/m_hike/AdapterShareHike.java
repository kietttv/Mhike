package com.example.m_hike;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterShareHike extends RecyclerView.Adapter<AdapterShareHike.ShareHikeViewHolder>{

    private Context context;
    private ArrayList<ModelShare> shareHikeList;
    private DbHelper dbHelper;

    public AdapterShareHike(Context context, ArrayList<ModelShare> contactList) {
        this.context = context;
        this.shareHikeList = contactList;
        dbHelper = new DbHelper(context);
    }

    @NonNull
    @Override
    public AdapterShareHike.ShareHikeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_friend_hike,parent,false);
        AdapterShareHike.ShareHikeViewHolder vh = new AdapterShareHike.ShareHikeViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterShareHike.ShareHikeViewHolder holder, int position) {

        ModelShare modelShare = shareHikeList.get(position);

        ModelHike modelHike = dbHelper.getHikeById(modelShare.getHikeId());
        holder.shareHikeTv.setText(modelHike.getHike_name());

        dbHelper.updateShare( modelShare.User2, MainActivity.userID, modelHike.getHike_id(), modelShare.getShareId());

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, HikeShareDetail.class);
                intent.putExtra("HIKE_ID", modelHike.getHike_id());
                intent.putExtra("NAME", modelHike.getHike_name());
                intent.putExtra("LOCATION", modelHike.getHike_location());
                intent.putExtra("LENGTH", modelHike.getHike_length());
                intent.putExtra("DESCRIPTION", modelHike.getHike_description());
                intent.putExtra("PARKING", modelHike.getHike_parking());
                intent.putExtra("LEVEL", modelHike.getHike_level());

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return shareHikeList.size();
    }

    class ShareHikeViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout relativeLayout;
        TextView shareHikeTv;
        public ShareHikeViewHolder(@NonNull View itemView) {
            super(itemView);

            relativeLayout = itemView.findViewById(R.id.mainLayout);
            shareHikeTv = itemView.findViewById(R.id.shareHikeTv);
        }
    }
}
