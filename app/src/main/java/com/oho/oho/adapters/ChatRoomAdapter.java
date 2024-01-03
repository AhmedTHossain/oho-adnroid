package com.oho.oho.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.oho.oho.R;
import com.oho.oho.interfaces.OnChatRoomClickListener;
import com.oho.oho.interfaces.OnMessageOptionsMenu;
import com.oho.oho.responses.chat.ChatRoom;
import com.oho.oho.utils.HelperClass;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatRoomAdapter extends RecyclerView.Adapter<ChatRoomAdapter.ViewHolder> {

    private ArrayList<ChatRoom> chatRoomArrayList;

    private OnChatRoomClickListener chatRoomClickListener;
    private OnMessageOptionsMenu onMessageOptionsMenuListener;
    private HelperClass helperClass = new HelperClass();
    private Context context;

    public ChatRoomAdapter(ArrayList<ChatRoom> chatRoomArrayList, OnChatRoomClickListener chatRoomClickListener, OnMessageOptionsMenu onMessageOptionsMenuListener, Context context) {
        this.chatRoomArrayList = chatRoomArrayList;
        this.chatRoomClickListener = chatRoomClickListener;
        this.onMessageOptionsMenuListener = onMessageOptionsMenuListener;
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
        holder.getSenderNameText().setText(chatRoomArrayList.get(position).getFullName());
        holder.getLastMessageBodyText().setText(chatRoomArrayList.get(position).getLastMessage());

        if (chatRoomArrayList.get(position).getGender().equals("F")) {
            Glide.with(context)
                    .load(chatRoomArrayList.get(position).getProfilePhoto())
                    .placeholder(R.drawable.portrait_female)
                    .into(holder.getSenderImage());
        } else if (chatRoomArrayList.get(position).getGender().equals("M")) {
            Glide.with(context)
                    .load(chatRoomArrayList.get(position).getProfilePhoto())
                    .placeholder(R.drawable.portrait_male)
                    .into(holder.getSenderImage());
        } else {
            Glide.with(context)
                    .load(chatRoomArrayList.get(position).getProfilePhoto())
                    .placeholder(R.drawable.person_placeholder)
                    .into(holder.getSenderImage());
        }

        Log.d("ChatRoomAdapter", "message is blocked: " + chatRoomArrayList.get(position).getStatus());
        if (chatRoomArrayList.get(position).getStatus().equals("blocked")) {
            Log.d("ChatRoomAdapter","my id: "+helperClass.getProfile(context).getId() + "blocked by: "+chatRoomArrayList.get(position).getBlockedBy());
            if (chatRoomArrayList.get(position).getBlockedBy().equals(helperClass.getProfile(context).getId())) {
                String message = "You blocked this user";
                holder.blockedMessageText.setText(message);
                holder.blockedMessageText.setVisibility(View.VISIBLE);
            }
            else {
                String message = "This user has blocked you";
                holder.blockedMessageText.setText(message);
                holder.blockedMessageText.setVisibility(View.VISIBLE);
            }
        } else
            holder.blockedMessageText.setVisibility(View.GONE);

    }

    @Override
    public int getItemCount() {
        return chatRoomArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView senderNameText, lastMessageBodyText, blockedMessageText;
        private CircleImageView senderImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            senderNameText = (TextView) itemView.findViewById(R.id.name_text);
            lastMessageBodyText = (TextView) itemView.findViewById(R.id.last_message_text);
            senderImage = (CircleImageView) itemView.findViewById(R.id.image);
            blockedMessageText = (TextView) itemView.findViewById(R.id.message_blocked);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    chatRoomClickListener.onChatRoomClick(chatRoomArrayList.get(getAdapterPosition()));
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    String imageUrl = chatRoomArrayList.get(getAdapterPosition()).getProfilePhoto();
                    String nameText = chatRoomArrayList.get(getAdapterPosition()).getFullName();
                    onMessageOptionsMenuListener.openMenu(senderImage, imageUrl, nameText, getAbsoluteAdapterPosition());
                    return false;
                }
            });
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

        public TextView getBlockedMessageText() {
            return blockedMessageText;
        }

        public void setBlockedMessageText(TextView blockedMessageText) {
            this.blockedMessageText = blockedMessageText;
        }
    }
}
