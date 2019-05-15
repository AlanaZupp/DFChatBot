package com.example.autandroidapp;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;


public class ChatViewHolder extends RecyclerView.ViewHolder {
    LinearLayout leftLayout, rightLayout;
    TextView leftText, rightText;


    public ChatViewHolder(View itemView) {
        super(itemView);
        leftLayout = (LinearLayout) itemView.findViewById(R.id.left_msg_layout);
        rightLayout = (LinearLayout) itemView.findViewById(R.id.right_msg_layout);
        leftText = (TextView) itemView.findViewById(R.id.left_msg_textview);
        rightText = (TextView) itemView.findViewById(R.id.right_msg_textview);
    }
}
