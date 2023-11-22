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
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterFriendRequest extends RecyclerView.Adapter<AdapterFriendRequest.UserViewHolder>{
    private Context context;
    private ArrayList<ModelRelationship> users;
    private DbHelper dbHelper;
    private FriendRequestFragment friendRequestFragment;

    public AdapterFriendRequest(Context context, ArrayList<ModelRelationship> users, FriendRequestFragment friendRequestFragment) {
        this.context = context;
        this.users = users;
        dbHelper = new DbHelper(context);
        this.friendRequestFragment = friendRequestFragment;
    }
    @NonNull
    @Override
    public AdapterFriendRequest.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(context).inflate(R.layout.row_friend_request,parent,false);
        UserViewHolder userViewHolder = new UserViewHolder(view);
        return userViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        ModelRelationship ModelRelationship = users.get(position);

        String id = ModelRelationship.getRelationshipId();
        String userID1 = ModelRelationship.getUser1();
        String userID2 = ModelRelationship.getUser2();

        ModelUser user = dbHelper.getUserById(userID2);

        holder.friendRequestNameTv.setText(user.getUser_name());
        Uri image = Uri.parse(user.getUser_image());
        holder.friendRequestImageIv.setImageURI(image);

        holder.acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dbHelper.updateRelationship(id, userID1, userID2, "Friend");

                ModelRelationship friend = dbHelper.getRelationship(userID2, userID1);
                dbHelper.updateRelationship(friend.getRelationshipId(), friend.getUser1(), friend.getUser2(), "Friend");

                friendRequestFragment.onResume();
                ((Friend)context).onResume();
            }
        });

        holder.deleteIBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.deleteRelationship( userID1,userID2);
                dbHelper.deleteRelationship( userID2, userID1);
                friendRequestFragment.onResume();
                ((Friend)context).onResume();
            }
        });

    }

    @Override
    public int getItemCount() {
        return users.size();
    }
    public void setData(ArrayList<ModelRelationship> userList) {
        this.users = userList;
    }

    class UserViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout relativeLayout;
        ImageView friendRequestImageIv;
        TextView friendRequestNameTv;
        Button acceptBtn, deleteIBtn;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            relativeLayout = itemView.findViewById(R.id.mainLayout);
            friendRequestImageIv = itemView.findViewById(R.id.friendRequestImageIv);
            friendRequestNameTv = itemView.findViewById(R.id.friendRequestNameTv);
            acceptBtn = itemView.findViewById(R.id.acceptBtn);
            deleteIBtn = itemView.findViewById(R.id.deleteIBtn);
        }
    }


}

