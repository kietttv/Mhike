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

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterSearchFriend extends RecyclerView.Adapter<AdapterSearchFriend.SearchFriendViewHolder>{

    private Context context;
    private ArrayList<ModelUser> users;
    private DbHelper dbHelper;
    public AdapterSearchFriend(Context context, ArrayList<ModelUser> users) {
        this.context = context;
        this.users = users;
        dbHelper = new DbHelper(context);
    }

    @NonNull
    @Override
    public SearchFriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_search_friend,parent,false);
        SearchFriendViewHolder userViewHolder = new SearchFriendViewHolder(view);
        return userViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchFriendViewHolder holder, int position) {
        ModelUser modelUser = users.get(position);

        String userId = modelUser.getUser_id();
        String userName = modelUser.getUser_name();
        String userEmail = modelUser.getUser_email();
        String userImage = modelUser.getUser_image();

        holder.searchFriendNameTv.setText(userName);
        holder.searchFriendEmail.setText(userEmail);
        holder.searchFriendImageIv.setImageURI(Uri.parse(userImage));

        String relationshipStatus = dbHelper.getRelationshipStatus(MainActivity.userID, userId);


        if(relationshipStatus.equals("Waiting")){
            holder.addFriendBtn.setText("Waiting");
        } else if(relationshipStatus.equals("Friend")){
            holder.addFriendBtn.setText("Delete");
        } else if (relationshipStatus.equals("Request")) {
            holder.addFriendBtn.setText("Accept");
        } else {
            holder.addFriendBtn.setText("Add friend");
        }

        holder.addFriendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(holder.addFriendBtn.getText().equals("Add friend")){
                    holder.addFriendBtn.setText("Waiting");
                    dbHelper.insertRelationship(MainActivity.userID, userId, "Waiting");
                    dbHelper.insertRelationship(userId,MainActivity.userID, "Request");
                    ((Friend)context).onResume();
                }
                else if(holder.addFriendBtn.getText().equals("Waiting")){
                    holder.addFriendBtn.setText("Add friend");
                    dbHelper.deleteRelationship(MainActivity.userID, userId);
                    dbHelper.deleteRelationship(userId, MainActivity.userID);
                    ((Friend)context).onResume();
                }
                else if (holder.addFriendBtn.getText().equals("Accept")) {
                    holder.addFriendBtn.setText("Delete");

                    ModelRelationship friend1 = dbHelper.getRelationship(MainActivity.userID, userId);
                    dbHelper.updateRelationship(friend1.getRelationshipId(), friend1.getUser1(), friend1.getUser2(), "Friend");

                    ModelRelationship friend2 = dbHelper.getRelationship(userId, MainActivity.userID);
                    dbHelper.updateRelationship(friend2.getRelationshipId(), friend2.getUser1(), friend2.getUser2(), "Friend");
                    ((Friend)context).onResume();
                }
                else if(holder.addFriendBtn.getText().equals("Delete")){
                    holder.addFriendBtn.setText("Add friend");
                    dbHelper.deleteRelationship(MainActivity.userID, userId);
                    dbHelper.deleteRelationship(userId, MainActivity.userID);
                    ((Friend)context).onResume();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class SearchFriendViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout relativeLayout;
        ImageView searchFriendImageIv;
        TextView searchFriendNameTv, searchFriendEmail;
        Button addFriendBtn;
        public SearchFriendViewHolder(@NonNull View itemView) {
            super(itemView);
            relativeLayout = itemView.findViewById(R.id.mainLayout);
            searchFriendImageIv = itemView.findViewById(R.id.searchFriendImageIv);
            searchFriendNameTv = itemView.findViewById(R.id.searchFriendNameTv);
            searchFriendEmail = itemView.findViewById(R.id.searchFriendEmailTv);
            addFriendBtn = itemView.findViewById(R.id.addFriendBtn);
        }
    }
}
