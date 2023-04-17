package com.oho.oho.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.oho.oho.R;
import com.oho.oho.responses.ChatRoom;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatRoomAdapter extends RecyclerView.Adapter<ChatRoomAdapter.ViewHolder>{

    private ArrayList<ChatRoom> chatRoomArrayList;
    private Context context;

    public ChatRoomAdapter(ArrayList<ChatRoom> chatRoomArrayList, Context context) {
        this.chatRoomArrayList = chatRoomArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ChatRoomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_chat_room, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatRoomAdapter.ViewHolder holder, int position) {
        holder.getSenderNameText().setText(chatRoomArrayList.get(position).getSenderFullName());
        holder.getLastMessageBodyText().setText(chatRoomArrayList.get(position).getLastMessage());
        Glide.with(context)
                .load(chatRoomArrayList.get(position).getSenderProfilePhotoUrl())
                .into(holder.getSenderImage());
    }

    @Override
    public int getItemCount() {
        return chatRoomArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView senderNameText,lastMessageBodyText;
        private CircleImageView senderImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            senderNameText = (TextView) itemView.findViewById(R.id.name_text);
            lastMessageBodyText = (TextView) itemView.findViewById(R.id.last_message_text);
            senderImage = (CircleImageView) itemView.findViewById(R.id.image);
        }

        public TextView getSenderNameText() {
            return senderNameText;
        }

        public void setSenderNameText(TextView senderNameText) {
            this.senderNameText = senderNameText;
        }

        public TextView getLastMessageBodyText() {
            return lastMessageBodyText;
        }

        public void setLastMessageBodyText(TextView lastMessageBodyText) {
            this.lastMessageBodyText = lastMessageBodyText;
        }

        public CircleImageView getSenderImage() {
            return senderImage;
        }

        public void setSenderImage(CircleImageView senderImage) {
            this.senderImage = senderImage;
        }
    }
}
