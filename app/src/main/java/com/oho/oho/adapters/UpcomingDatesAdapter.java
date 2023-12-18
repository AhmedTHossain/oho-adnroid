package com.oho.oho.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.oho.oho.R;
import com.oho.oho.models.UpcomingDate;

import java.util.ArrayList;

public class UpcomingDatesAdapter extends RecyclerView.Adapter<UpcomingDatesAdapter.ViewHolder> {
    private ArrayList<UpcomingDate> upcomingDateArrayList;
    private Context context;

    public UpcomingDatesAdapter(ArrayList<UpcomingDate> upcomingDateArrayList, Context context) {
        this.upcomingDateArrayList = upcomingDateArrayList;
        this.context = context;
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
        holder.getDayTimeView().setText(upcomingDateArrayList.get(position).getAvailability());
        holder.getLocationView().setText(upcomingDateArrayList.get(position).getLocation());
        Glide.with(context)
                .load(upcomingDateArrayList.get(position).getProfilePhoto())
                .placeholder(R.drawable.portrait_female)
                .into(holder.getPhotoView());
    }

    @Override
    public int getItemCount() {
        return upcomingDateArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
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
