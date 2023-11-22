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

public class AdapterHike extends RecyclerView.Adapter<AdapterHike.HikeViewHolder>{

    private Context context;
    private ArrayList<ModelHike> hikeList;
    private DbHelper dbHelper;

    public AdapterHike(Context context, ArrayList<ModelHike> contactList) {
        this.context = context;
        this.hikeList = contactList;
        dbHelper = new DbHelper(context);
    }

    @NonNull
    @Override
    public AdapterHike.HikeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_hike_item,parent,false);
        HikeViewHolder vh = new HikeViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterHike.HikeViewHolder holder, int position) {

        ModelHike modelContact = hikeList.get(position);

        String hike_id = modelContact.getHike_id();
        String hike_name = modelContact.getHike_name();
        String hike_location = modelContact.getHike_location();
        String hike_date = modelContact.getHike_date();
        String hike_parking = modelContact.getHike_parking();
        String hike_length = modelContact.getHike_length();
        String hike_level = modelContact.getHike_level();
        String hike_description = modelContact.getHike_description();

        holder.nameHikeTv.setText(hike_name);
        holder.dateHikeTv.setText(hike_date);
        holder.lengthHikeTv.setText(hike_length);
        holder.locationTv.setText(hike_location);

        holder.detailIbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddConfirmDetailHike.class);

                intent.putExtra("CONFIRM_DETAIL", "DETAIL");
                intent.putExtra("ID", hike_id);
                intent.putExtra("NAME", hike_name);
                intent.putExtra("LOCATION", hike_location);
                intent.putExtra("LENGTH", hike_length);
                intent.putExtra("DESCRIPTION",hike_description);
                intent.putExtra("DATE", hike_date);
                intent.putExtra("PARKING", hike_parking);
                intent.putExtra("LEVEL", hike_level);
                intent.putExtra("DETAIL", true);

                context.startActivity(intent);
            }
        });

        holder.editIbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddEditHike.class);

                intent.putExtra("ID", hike_id);
                intent.putExtra("NAME", hike_name);
                intent.putExtra("LOCATION", hike_location);
                intent.putExtra("LENGTH", hike_length);
                intent.putExtra("DESCRIPTION",hike_description);
                intent.putExtra("DATE", hike_date);
                intent.putExtra("PARKING", hike_parking);
                intent.putExtra("LEVEL", hike_level);
                intent.putExtra("isEdit",true);

                context.startActivity(intent);
            }
        });

        holder.deleteIbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Confirm deletion");
                builder.setMessage("Are you sure you want to delete " + hike_name +"?");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dbHelper.deleteHikeById(hike_id);
                        ((MainActivity)context).onResume();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.create().show();
            }
        });

        holder.shareIbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupShare popupShare = new PopupShare();
                popupShare.showPopup(((MainActivity)context), hike_id);
            }
        });

    }

    @Override
    public int getItemCount() {
       return hikeList.size();
    }

    class HikeViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout relativeLayout;
        TextView nameHikeTv, locationTv, dateHikeTv, lengthHikeTv;
        ImageButton detailIbtn, editIbtn, deleteIbtn, shareIbtn;
        public HikeViewHolder(@NonNull View itemView) {
            super(itemView);

            relativeLayout = itemView.findViewById(R.id.mainLayout);
            nameHikeTv = (TextView) itemView.findViewById(R.id.nameHikeTv);
            locationTv = (TextView) itemView.findViewById(R.id.locationTv);
            dateHikeTv = (TextView) itemView.findViewById(R.id.dateHikeTv);
            lengthHikeTv = (TextView) itemView.findViewById(R.id.lengthHikeTv);
            detailIbtn = (ImageButton) itemView.findViewById(R.id.detailIbtn);
            editIbtn = (ImageButton) itemView.findViewById(R.id.editIbtn);
            deleteIbtn = (ImageButton) itemView.findViewById(R.id.deleteIbtn);
            shareIbtn = (ImageButton) itemView.findViewById(R.id.shareIbtn);
        }
    }
}
