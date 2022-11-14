package com.oho.oho.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.oho.oho.R;
import com.oho.oho.interfaces.SwipeListener;
import com.oho.oho.models.PromptAnswer;
import com.oho.oho.models.User;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileDisplayAdapter extends RecyclerView.Adapter{

    private ArrayList<PromptAnswer> promptArrayList;
    private User userProfile;

    private SwipeListener listener;
    private Context context;

    private final int VIEW_TYPE_LEFT = -1;
    private final int VIEW_TYPE_INFO = 0;
    private final int VIEW_TYPE_RIGHT = 1;
    private final int VIEW_TYPE_SWIPE = 2;

    public ProfileDisplayAdapter(ArrayList<PromptAnswer> promptArrayList, User userProfile, SwipeListener listener, Context context) {
        this.promptArrayList = promptArrayList;
        this.context = context;
        this.userProfile = userProfile;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case VIEW_TYPE_INFO:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_info_profile, parent, false);
                return new Holder1(view);
            case VIEW_TYPE_LEFT:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_prompt_profile__left, parent, false);
                return new Holder(view);
            case VIEW_TYPE_RIGHT:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_prompt_profile_right, parent, false);
                return new Holder(view);
            case VIEW_TYPE_SWIPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_swipe_profile, parent, false);
                return new Holder2(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)){
            case VIEW_TYPE_INFO:
                String name_age = userProfile.getName()+", "+userProfile.getAge();

                ((Holder1) holder).name.setText(name_age);
                String location = userProfile.getCity() + ", " + userProfile.getState();
                ((Holder1) holder).location.setText(location);

                ((Holder1) holder).profession.setText(userProfile.getOccupation());
                ((Holder1) holder).gender.setText(userProfile.getSex());
                ((Holder1) holder).height.setText(String.valueOf(userProfile.getHeight()));
                ((Holder1) holder).race.setText(userProfile.getRace());
                ((Holder1) holder).religion.setText(userProfile.getReligion());
                ((Holder1) holder).vaccinated.setText(userProfile.getVaccinated());
//                ((Holder1) holder).distance.setText(userProfile.getDistance());
                Glide.with(context)
                        .load(userProfile.getProfilePicture())
                        .into( ((Holder1) holder).imageView);
                break;
            case VIEW_TYPE_LEFT:
            case VIEW_TYPE_RIGHT:
                ((Holder)holder).prompt.setText(promptArrayList.get(position).getPrompt());
                ((Holder)holder).answer.setText(promptArrayList.get(position).getAnswer());
                ((Holder)holder).caption.setText(promptArrayList.get(position).getCaption());
                Glide.with(context)
                        .load(promptArrayList.get(position).getImage())
                        .into(((Holder)holder).imageView);
                break;
            case VIEW_TYPE_SWIPE:
                ((Holder2) holder).seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                       listener.onSwipe(progress,seekBar);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });
                break;
        }
    }

    @Override
    public int getItemCount() {
        return promptArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return VIEW_TYPE_INFO;
        else if (position == promptArrayList.size()-1)
            return VIEW_TYPE_SWIPE;
        else {
            if (position % 2 != 0)
                return VIEW_TYPE_LEFT;
            else
                return VIEW_TYPE_RIGHT;
        }
    }

    public class Holder extends RecyclerView.ViewHolder {
        private TextView prompt, answer, caption;
        private CircleImageView imageView;

        public Holder(@NonNull View itemView) {
            super(itemView);

            prompt = itemView.findViewById(R.id.text_prompt);
            answer = itemView.findViewById(R.id.text_answer);
            caption = itemView.findViewById(R.id.text_caption);
            imageView = itemView.findViewById(R.id.image_view);
        }
    }

    public class Holder1 extends RecyclerView.ViewHolder {
        private TextView name, location, profession, gender, height, race, religion, vaccinated, distance;
        private CircleImageView imageView;

        public Holder1(@NonNull View itemView) {
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

    public class Holder2 extends RecyclerView.ViewHolder{
        private SeekBar seekBar;
        public Holder2(@NonNull View itemView) {
            super(itemView);
            seekBar = itemView.findViewById(R.id.seekbar);
        }
    }
}
