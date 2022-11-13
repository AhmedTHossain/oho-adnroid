package com.oho.oho.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.oho.oho.R;
import com.oho.oho.models.User;

import java.util.ArrayList;

public class LikeYouAdapter extends RecyclerView.Adapter<LikeYouAdapter.Holder> {
    private Context context;
    private ArrayList<User> userArrayList;

    public LikeYouAdapter(Context context, ArrayList<User> userArrayList) {
        this.context = context;
        this.userArrayList = userArrayList;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_liked_profile_thumbnail,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LikeYouAdapter.Holder holder, int position) {
        Glide
                .with(context)
                .load(userArrayList.get(position).getProfilePicture())
                .centerCrop()
                .placeholder(R.drawable.person)
                .into(holder.getImageView());
        holder.getNameTextView().setText(userArrayList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    public class Holder extends RecyclerView.ViewHolder{
        private final ShapeableImageView imageView;
        private final TextView nameTextView;
        public Holder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.shapeable_image_view);
            nameTextView = itemView.findViewById(R.id.name_textview);
        }

        public ShapeableImageView getImageView() {
            return imageView;
        }

        public TextView getNameTextView() {
            return nameTextView;
        }
    }
}
