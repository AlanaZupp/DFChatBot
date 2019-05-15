package com.example.autandroidapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatViewHolder> {

    private List<ChatMsgList> msgList = null;

    public ChatAdapter (List<ChatMsgList> msgList) {
        this.msgList = msgList;
    }

    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.chat_recycler_view, parent, false);
        return new  ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position)
    {
        ChatMsgList msgList2 = this.msgList.get(position);

        if(msgList2.Msg_rece.equals(msgList2.getMsgType()))
        {
            holder.leftLayout.setVisibility(LinearLayout.VISIBLE);
            holder.leftText.setText(msgList2.getMsgContent());
            holder.rightLayout.setVisibility(LinearLayout.GONE);
        }

        if(msgList2.Msg_sent.equals(msgList2.getMsgType()))
        {
            holder.rightLayout.setVisibility(LinearLayout.VISIBLE);
            holder.rightText.setText(msgList2.getMsgContent());
            holder.leftLayout.setVisibility(LinearLayout.GONE);
        }
    }

    @Override
    public int getItemCount() {
        if(msgList==null)
        {
            msgList = new ArrayList<ChatMsgList>();
        }
        return msgList.size();
    }
}

