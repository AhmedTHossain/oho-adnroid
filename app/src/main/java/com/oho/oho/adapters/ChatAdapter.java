package com.oho.oho.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.oho.oho.R;
import com.oho.oho.responses.Chat;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    private ArrayList<Chat> chatList;
    private int userId;
    private Context context;

    private static int VIEW_TYPE_OWN = 1;
    private static int VIEW_TYPE_SENDER = 2;

    public ChatAdapter(ArrayList<Chat> chatList, int userId, Context context) {
        this.chatList = chatList;
        this.userId = userId;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        if (viewType == VIEW_TYPE_SENDER)
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_chat_message_sender, parent, false);
        else
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_chat_message_own, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getChatMessageView().setText(chatList.get(position).getBody());
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (chatList.get(position).getChatType().equals("delegate")) {
            if (chatList.get(position).getSender() != userId)
                return VIEW_TYPE_SENDER;
        } else {
            if (chatList.get(position).getSender() != userId)
                return VIEW_TYPE_SENDER;
            else
                return VIEW_TYPE_OWN;
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView chatMessageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            chatMessageView = (TextView) itemView.findViewById(R.id.textview_chat);
        }

        public TextView getChatMessageView() {
            return chatMessageView;
        }

        public void setChatMessageView(TextView chatMessageView) {
            this.chatMessageView = chatMessageView;
        }
    }
}
