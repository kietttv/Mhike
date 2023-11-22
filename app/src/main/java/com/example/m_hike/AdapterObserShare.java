package com.example.m_hike;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterObserShare extends RecyclerView.Adapter<AdapterObserShare.ObserViewHolder>{
    private Context context;
    private ArrayList<ModelObservation> obserList;
    private DbHelper dbHelper;

    public AdapterObserShare(Context context, ArrayList<ModelObservation> obserList) {
        this.context = context;
        this.obserList = obserList;
        dbHelper = new DbHelper(context);
    }

    @NonNull
    @Override
    public AdapterObserShare.ObserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_observation_share_item,parent,false);
        AdapterObserShare.ObserViewHolder vh = new AdapterObserShare.ObserViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterObserShare.ObserViewHolder holder, int position) {

        ModelObservation modelContact = obserList.get(position);

        String hike_id = modelContact.getHike_id();
        String obser_id = modelContact.getObser_id();
        String obser_name = modelContact.getObser_name();
        String obser_time = modelContact.getObser_time();
        String obser_comment = modelContact.getObser_comment();
        String obser_image = modelContact.getObser_image();

        holder.obserNameTv.setText(obser_name);
        holder.obserCommentTv.setText("Comment: " + obser_comment);
        holder.obserDateTv.setText("Time: " + obser_time);
        holder.obserImageIv.setImageURI(Uri.parse(obser_image));

    }

    @Override
    public int getItemCount() {
        return obserList.size();
    }

    class ObserViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout relativeLayout;
        TextView obserNameTv, obserCommentTv, obserDateTv;
        ImageView obserImageIv;
        public ObserViewHolder(@NonNull View itemView) {
            super(itemView);

            relativeLayout = itemView.findViewById(R.id.mainLayout);
            obserNameTv = (TextView) itemView.findViewById(R.id.obserNameTv);
            obserCommentTv = (TextView) itemView.findViewById(R.id.obserCommentTv);
            obserDateTv = (TextView) itemView.findViewById(R.id.obserDateTv);
            obserImageIv = (ImageView) itemView.findViewById(R.id.obserImageIv);

        }
    }
}



