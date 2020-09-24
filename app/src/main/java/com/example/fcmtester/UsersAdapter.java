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

/*
      Good Faith Statement & Confidentiality : The below code is part of IMPACTO Suite of products .
      Sirma Business Consulting India reserves all rights to this code . No part of this code should
      be copied, stored or transmitted in any form for whatsoever reason without prior written consent
      of Sirma Business Consulting (India).Employees or developers who have access to this code shall
      take all reasonable precautions to protect the source code and documentation, and preserve its
      confidential, proprietary and trade secret status in perpetuity.Any breach of the obligations
      to protect confidentiality of IMPACTO may cause immediate and irreparable harm to Sirma Business
      Consulting, which cannot be adequately compensated by monetary damages. Accordingly, any breach
      or threatened breach of confidentiality shall entitle Sirma Business Consulting to seek preliminary
      and permanent injunctive relief in addition to such remedies as may otherwise be available.

      //But by the grace of God We are what we are, and his grace to us was not without effect. No,
      //We worked harder than all of them--yet not We, but the grace of God that was with us.
      ----------------------------------------------------------------------------------------------
      |Version No  | Changed by | Date         | Change Tag  | Changes Done
      ----------------------------------------------------------------------------------------------
      |0.1 Beta    | Debayan     | Feb 2, 2018  | #DBP00001   | Initial writing
      ----------------------------------------------------------------------------------------------

*/public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.MyViewHolder> {

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
