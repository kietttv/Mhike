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

public class AdapterObser extends RecyclerView.Adapter<AdapterObser.ObserViewHolder>{
    private Context context;
    private ArrayList<ModelObservation> obserList;
    private DbHelper dbHelper;

    public AdapterObser(Context context, ArrayList<ModelObservation> obserList) {
        this.context = context;
        this.obserList = obserList;
        dbHelper = new DbHelper(context);
    }

    @NonNull
    @Override
    public AdapterObser.ObserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_observation_item,parent,false);
        AdapterObser.ObserViewHolder vh = new AdapterObser.ObserViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterObser.ObserViewHolder holder, int position) {

        ModelObservation modelContact = obserList.get(position);

        String hike_id = modelContact.getHike_id();
        String obser_id = modelContact.getObser_id();
        String obser_name = modelContact.getObser_name();
        String obser_time = modelContact.getObser_time();
        String obser_comment = modelContact.getObser_comment();
        String obser_image = modelContact.getObser_image();

        holder.obserNameTv.setText(obser_name);
        holder.obserImageIv.setImageURI(Uri.parse(obser_image));

        holder.editObserIbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddEditObser.class);

                intent.putExtra("HIKE_ID", hike_id);
                intent.putExtra("ID", obser_id);
                intent.putExtra("NAME", obser_name);
                intent.putExtra("TIME", obser_time);
                intent.putExtra("COMMENT", obser_comment);
                intent.putExtra("IMAGE",obser_image);
                intent.putExtra("isEditObser",true);

                context.startActivity(intent);
            }
        });

        holder.viewObserIbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentDetail = new Intent(context, DetailObservation.class);

                intentDetail.putExtra("OBSER_ID", obser_id);

                context.startActivity(intentDetail);
            }
        });

        holder.deleteObserIbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Confirm deletion");
                builder.setMessage("Are you sure you want to delete " + obser_name + " Observation?");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dbHelper.deleteObserById(obser_id);
                        ((MainObservation)context).onResume();
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
    }

    @Override
    public int getItemCount() {
        return obserList.size();
    }

    class ObserViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout relativeLayout;
        TextView obserNameTv;
        ImageView obserImageIv;
        ImageButton deleteObserIbtn, editObserIbtn, viewObserIbtn;
        public ObserViewHolder(@NonNull View itemView) {
            super(itemView);

            relativeLayout = itemView.findViewById(R.id.mainLayout);
            obserNameTv = (TextView) itemView.findViewById(R.id.obserNameTv);
            obserImageIv = (ImageView) itemView.findViewById(R.id.obserImageIv);
            deleteObserIbtn = (ImageButton) itemView.findViewById(R.id.deleteObserIbtn);
            editObserIbtn = (ImageButton) itemView.findViewById(R.id.editObserIbtn);
            viewObserIbtn = (ImageButton) itemView.findViewById(R.id.viewObserIbtn);
        }
    }
}



