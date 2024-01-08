package com.oho.oho.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.oho.oho.R;
import com.oho.oho.interfaces.OnProfileClickListener;
import com.oho.oho.models.Profile;
import com.oho.oho.models.User;

import java.util.ArrayList;

public class LikeYouAdapter extends RecyclerView.Adapter<LikeYouAdapter.Holder> {
    private Context context;
    private ArrayList<Profile> userArrayList;
    private OnProfileClickListener listener;

    public LikeYouAdapter(Context context, ArrayList<Profile> userArrayList, OnProfileClickListener listener) {
        this.context = context;
        this.userArrayList = userArrayList;
        this.listener = listener;
        Toast.makeText(context, "number of likes in adapter: = " + userArrayList.size(), Toast.LENGTH_LONG).show();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_liked_profile_thumbnail, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LikeYouAdapter.Holder holder, int position) {
        String url = userArrayList.get(position).getProfilePicture()+".jpeg";
        Glide
                .with(context)
                .load(url)
                .centerCrop()
                .placeholder(R.drawable.person)
                .into(holder.getImageView());
        holder.getNameTextView().setText(userArrayList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        private final ShapeableImageView imageView;
        private final TextView nameTextView;
        private final LinearLayout rootLayout;

        public Holder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.shapeable_image_view);
            nameTextView = itemView.findViewById(R.id.name_textview);
            rootLayout = itemView.findViewById(R.id.linear_layout_root);

            rootLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onProfileClick(userArrayList.get(getAdapterPosition()));
                }
            });

        }

        public ShapeableImageView getImageView() {
            return imageView;
        }

        public TextView getNameTextView() {
            return nameTextView;
        }
    }
}
