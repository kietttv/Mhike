package com.example.m_hike;

import android.content.Context;
import android.content.Intent;
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

public class AdapterAllFriend extends RecyclerView.Adapter<AdapterAllFriend.UserViewHolder>{
    private Context context;
    private ArrayList<ModelRelationship> users;
    private DbHelper dbHelper;
    private AllFriendFragment friendFragment;

    public AdapterAllFriend(Context context, ArrayList<ModelRelationship> users, AllFriendFragment friendFragment) {
        this.context = context;
        this.users = users;
        dbHelper = new DbHelper(context);
        this.friendFragment = friendFragment;
    }
    @NonNull
    @Override
    public AdapterAllFriend.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(context).inflate(R.layout.row_all_friend_item,parent,false);
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

        Integer count = dbHelper.countShare(MainActivity.userID, userID2);
        if(count > 0){
            holder.viewFriendIbtn.setText(count.toString());
            holder.viewFriendIbtn.setVisibility(View.VISIBLE);
        } else {
            holder.viewFriendIbtn.setText(count.toString());
            holder.viewFriendIbtn.setVisibility(View.GONE);
        }


        holder.friendNameTv.setText(user.getUser_name());
        Uri image = Uri.parse(user.getUser_image());
        holder.friendImageIv.setImageURI(image);

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Friend_Hike.class);
                intent.putExtra("idFriend", userID2);
                context.startActivity(intent);
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
        ImageView friendImageIv;
        TextView friendNameTv, viewFriendIbtn;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            relativeLayout = itemView.findViewById(R.id.mainLayout);
            friendImageIv = itemView.findViewById(R.id.friendImageIv);
            friendNameTv = itemView.findViewById(R.id.friendNameTv);
            viewFriendIbtn = itemView.findViewById(R.id.viewFriendIbtn);
        }
    }
}

