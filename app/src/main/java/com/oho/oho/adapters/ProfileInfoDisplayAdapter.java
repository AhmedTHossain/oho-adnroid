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
import com.oho.oho.models.ProfileDisplay;
import com.oho.oho.models.PromptDisplay;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileInfoDisplayAdapter extends RecyclerView.Adapter<ProfileInfoDisplayAdapter.Holder> {

    private ProfileDisplay profileDisplay;
    private Context context;

    public ProfileInfoDisplayAdapter(ProfileDisplay profileDisplay, Context context) {
        this.profileDisplay = profileDisplay;
        this.context = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_info_profile, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.name.setText(profileDisplay.getName());
        holder.location.setText(profileDisplay.getLocation());
        holder.profession.setText(profileDisplay.getProfession());
        holder.gender.setText(profileDisplay.getGender());
        holder.height.setText(profileDisplay.getHeight());
        holder.race.setText(profileDisplay.getRace());
        holder.religion.setText(profileDisplay.getReligion());
        holder.vaccinated.setText(profileDisplay.getVaccinated());
        holder.distance.setText(profileDisplay.getDistance());
        Glide.with(context)
                .load(profileDisplay.getImageUrl())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class Holder extends RecyclerView.ViewHolder {
        private TextView name, location, profession, gender, height, race, religion, vaccinated, distance;
        private CircleImageView imageView;
        public Holder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name_text);
            location = itemView.findViewById(R.id.textview_location);
            profession = itemView.findViewById(R.id.textview_profession);
            gender = itemView.findViewById(R.id.textview_gender);
            height = itemView.findViewById(R.id.textview_height);
            race = itemView.findViewById(R.id.textview_race);
            religion = itemView.findViewById(R.id.textview_religion);
            vaccinated = itemView.findViewById(R.id.textview_vaccinated);
            distance = itemView.findViewById(R.id.textview_distance);
            imageView = itemView.findViewById(R.id.profile_image_view);
        }
    }
}
