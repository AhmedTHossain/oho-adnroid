package com.oho.oho.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.oho.oho.R;
import com.oho.oho.interfaces.OnChatRoomClickListener;
import com.oho.oho.models.UpcomingDate;
import com.oho.oho.responses.chat.ChatRoom;
import com.oho.oho.utils.HelperClass;

import java.util.ArrayList;

public class UpcomingDatesAdapter extends RecyclerView.Adapter<UpcomingDatesAdapter.ViewHolder> {
    private ArrayList<UpcomingDate> upcomingDateArrayList;
    private Context context;
    private OnChatRoomClickListener chatRoomClickListener;

    public UpcomingDatesAdapter(ArrayList<UpcomingDate> upcomingDateArrayList, Context context, OnChatRoomClickListener chatRoomClickListener) {
        this.upcomingDateArrayList = upcomingDateArrayList;
        this.context = context;
        this.chatRoomClickListener = chatRoomClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_upcoming_date, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getNameView().setText(upcomingDateArrayList.get(position).getFullName());
        if (upcomingDateArrayList.get(position).getAddress() == null)
            holder.getLocationView().setText("N/A");
        else
            holder.getLocationView().setText(upcomingDateArrayList.get(position).getRestaurantName());

        Log.d("UpcomingDatesAdapter", "date time = " + new HelperClass().convertEpochToCustomFormat(upcomingDateArrayList.get(position).getReservationTime()));
        if (upcomingDateArrayList.get(position).getReservationTime() == null)
            holder.getDayTimeView().setText("N/A");
        else
            holder.getDayTimeView().setText(new HelperClass().convertEpochToCustomFormat(upcomingDateArrayList.get(position).getReservationTime()));

        String photoUrl = upcomingDateArrayList.get(position).getProfilePhoto() + ".jpeg";
        Glide.with(context)
                .load(photoUrl)
                .placeholder(R.drawable.portrait_female)
                .into(holder.getPhotoView());
    }

    @Override
    public int getItemCount() {
        return upcomingDateArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nameView, dayTimeView, locationView, viewChatView;
        private ImageView photoView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.textview_name);
            dayTimeView = itemView.findViewById(R.id.textview_day_time);
            locationView = itemView.findViewById(R.id.textview_location);
            viewChatView = itemView.findViewById(R.id.textview_view_chat);
            photoView = itemView.findViewById(R.id.imageview);

            viewChatView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ChatRoom chatRoom = new ChatRoom();
                    chatRoom.setId(upcomingDateArrayList.get(getAbsoluteAdapterPosition()).getChatId());
                    chatRoom.setFullName(upcomingDateArrayList.get(getAbsoluteAdapterPosition()).getFullName());
                    chatRoom.setChannelName(upcomingDateArrayList.get(getAbsoluteAdapterPosition()).getChannelName());
                    chatRoom.setProfilePhoto(upcomingDateArrayList.get(getAbsoluteAdapterPosition()).getProfilePhoto());
                    String participants = new HelperClass().getProfile(context).getId()+","+upcomingDateArrayList.get(getAbsoluteAdapterPosition()).getUserId();
                    chatRoom.setParticipants(participants);
                    chatRoom.setStatus("active"); //TODO: later on once status is received in api response pass that instead of had coded data
                    chatRoomClickListener.onChatRoomClick(chatRoom);
                }
            });
        }

        public TextView getViewChatView() {
            return viewChatView;
        }

        public void setViewChatView(TextView viewChatView) {
            this.viewChatView = viewChatView;
        }

        public TextView getNameView() {
            return nameView;
        }

        public void setNameView(TextView nameView) {
            this.nameView = nameView;
        }

        public TextView getDayTimeView() {
            return dayTimeView;
        }

        public void setDayTimeView(TextView dayTimeView) {
            this.dayTimeView = dayTimeView;
        }

        public TextView getLocationView() {
            return locationView;
        }

        public void setLocationView(TextView locationView) {
            this.locationView = locationView;
        }

        public ImageView getPhotoView() {
            return photoView;
        }

        public void setPhotoView(ImageView photoView) {
            this.photoView = photoView;
        }
    }
}
