package com.example.autandroidapp;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

/**
 * The class extends the recycler
 */
public class ChatViewHolder extends RecyclerView.ViewHolder {
    LinearLayout leftLayout, rightLayout;
    TextView leftText, rightText;

    /**
     * This method sets which layout is associated with which file
     * as well as which text layout is associated with which file
     */
    public ChatViewHolder(View itemView) {
        super(itemView);
        leftLayout = (LinearLayout) itemView.findViewById(R.id.left_msg_layout);
        rightLayout = (LinearLayout) itemView.findViewById(R.id.right_msg_layout);
        leftText = (TextView) itemView.findViewById(R.id.left_msg_textview);
        rightText = (TextView) itemView.findViewById(R.id.right_msg_textview);
    }
}
