package com.example.autandroidapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * This adpater class is used to assign each message to the correct layout needed by the chat system
 */
public class ChatAdapter extends RecyclerView.Adapter<ChatViewHolder>
{
    private List<ChatMsgList> msgList = null;

    /**
     * Default constructor for the adapter
     */
    public ChatAdapter (List<ChatMsgList> msgList)
    {
        this.msgList = msgList;
    }

    /**
     * This method sets the view of the recycler
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        //Sets the chat view holder to the recycler
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.chat_recycler_view, parent, false);
        return new  ChatViewHolder(view);
    }

    /**
     * defines the layout of the chat bot messages, each new message gets the layout based on which method calls it
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position)
    {
        ChatMsgList msgList2 = this.msgList.get(position);

        if(msgList2.Msg_rece.equals(msgList2.getMsgType())) //if the message is from the chat bot set it to the left side layout
        {
            holder.leftLayout.setVisibility(LinearLayout.VISIBLE);
            holder.leftText.setText(msgList2.getMsgContent());
            holder.rightLayout.setVisibility(LinearLayout.GONE);
        }

        if(msgList2.Msg_sent.equals(msgList2.getMsgType())) //if the message if from the user set it to the right side layout
        {
            holder.rightLayout.setVisibility(LinearLayout.VISIBLE);
            holder.rightText.setText(msgList2.getMsgContent());
            holder.leftLayout.setVisibility(LinearLayout.GONE);
        }
    }

    /**
     * This method returns the size of the message list array
     * @return
     */
    @Override
    public int getItemCount() {
        if(msgList==null)
        {
            msgList = new ArrayList<ChatMsgList>();
        }
        return msgList.size();
    }
}

