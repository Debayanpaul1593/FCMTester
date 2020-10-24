package com.example.fcmtester;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.MyViewHolder> {

    private JsonArray data;
    private UserActivity context;

    public UsersAdapter(UserActivity context, JsonArray data) {
        this.context = context;
        this.data = data;
    }

    public void updateData(JsonArray data){
        this.data = data;
    }

    @NonNull
    @Override
    public UsersAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View parentView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_view, parent, false);
        MyViewHolder vh = new MyViewHolder(parentView);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull UsersAdapter.MyViewHolder holder, int position) {
        JsonObject jsonItem = data.get(position).getAsJsonObject();
        holder.itemName.setText("Name: " + jsonItem.get("name").getAsString());
        holder.itemEmail.setText("Email: " + jsonItem.get("email").getAsString());
        holder.llContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UsersAdapter.this.context.startActivity(new Intent(UsersAdapter.this.context, GraphActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView itemName;
        public TextView itemEmail;
        public LinearLayout llContainer;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.itemName);
            itemEmail = itemView.findViewById(R.id.itemEmail);
            llContainer = itemView.findViewById(R.id.llContainer);
        }
    }
}
