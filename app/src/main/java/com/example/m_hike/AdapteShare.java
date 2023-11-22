package com.example.m_hike;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapteShare extends RecyclerView.Adapter<AdapteShare.ShareViewHolder>{

    private Context context;
    private ArrayList<ModelRelationship> users;
    private DbHelper dbHelper;
    private String idHike;
    public AdapteShare(Context context, ArrayList<ModelRelationship> users, String idHike) {
        this.context = context;
        this.users = users;
        dbHelper = new DbHelper(context);
        this.idHike = idHike;
    }

    @NonNull
    @Override
    public AdapteShare.ShareViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_share,parent,false);
        AdapteShare.ShareViewHolder userViewHolder = new AdapteShare.ShareViewHolder(view);
        return userViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapteShare.ShareViewHolder holder, int position) {
        ModelRelationship modelRelationship = users.get(position);

        String id = modelRelationship.getUser2();

        ModelUser modelUser = dbHelper.getUserById(id);

        String userId = modelUser.getUser_id();
        String userName = modelUser.getUser_name();
        String userEmail = modelUser.getUser_email();
        String userImage = modelUser.getUser_image();

        holder.searchFriendNameTv.setText(userName);
        holder.searchFriendEmail.setText(userEmail);
        holder.searchFriendImageIv.setImageURI(Uri.parse(userImage));

        holder.addFriendBtn.setText("Share");

        holder.addFriendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Shared successful", Toast.LENGTH_SHORT).show();
                dbHelper.insertShare(MainActivity.userID, userId, idHike);
                dbHelper.insertShare(userId, MainActivity.userID, idHike);
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class ShareViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout relativeLayout;
        ImageView searchFriendImageIv;
        TextView searchFriendNameTv, searchFriendEmail;
        Button addFriendBtn;
        public ShareViewHolder(@NonNull View itemView) {
            super(itemView);
            relativeLayout = itemView.findViewById(R.id.mainLayout);
            searchFriendImageIv = itemView.findViewById(R.id.searchFriendImageIv);
            searchFriendNameTv = itemView.findViewById(R.id.searchFriendNameTv);
            searchFriendEmail = itemView.findViewById(R.id.searchFriendEmailTv);
            addFriendBtn = itemView.findViewById(R.id.addFriendBtn);
        }
    }
}