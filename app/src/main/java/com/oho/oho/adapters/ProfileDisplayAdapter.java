package com.oho.oho.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.oho.oho.R;
import com.oho.oho.interfaces.SwipeListener;
import com.oho.oho.models.Profile;
import com.oho.oho.models.PromptAnswer;
import com.oho.oho.viewmodels.ProfileViewModel;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileDisplayAdapter extends RecyclerView.Adapter {

    private ArrayList<PromptAnswer> promptArrayList;
    private Profile userProfile;

    private SwipeListener listener;
    private Context context;

    private String user_type = "";

    private final int VIEW_TYPE_LEFT = -1;
    private final int VIEW_TYPE_INFO = 0;
    private final int VIEW_TYPE_RIGHT = 1;
    private final int VIEW_TYPE_SWIPE = 2;
    private ProfileViewModel viewModel;

    public ProfileDisplayAdapter(Profile userProfile, ArrayList<PromptAnswer> promptArrayList, SwipeListener listener, Context context, ProfileViewModel viewModel) {
        this.promptArrayList = promptArrayList;
        promptArrayList.add(0, new PromptAnswer());
        this.context = context;
        this.userProfile = userProfile;
        this.listener = listener;
        this.viewModel = viewModel;
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
                if (viewModel != null)
                    view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_add_prompt_profile, parent, false);
                else
                    view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_swipe_profile, parent, false);
                return new Holder2(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case VIEW_TYPE_INFO:
                String name_age = userProfile.getName() + ", " + userProfile.getAge();

                ((Holder1) holder).name.setText(name_age);
                String location = userProfile.getCity() + ", " + userProfile.getState();
                ((Holder1) holder).location.setText(location);

                ((Holder1) holder).profession.setText(userProfile.getOccupation());
                ((Holder1) holder).gender.setText(userProfile.getSex());
                ((Holder1) holder).height.setText(String.valueOf(userProfile.getHeight() + " cm"));
                ((Holder1) holder).race.setText(userProfile.getRace());
                ((Holder1) holder).religion.setText(userProfile.getReligion());
                ((Holder1) holder).education.setText(userProfile.getEducation());
                ((Holder1) holder).about.setText(userProfile.getBio());
                Glide.with(context)
                        .load(userProfile.getProfilePicture()).centerCrop()
                        .into(((Holder1) holder).imageView);
                ((Holder1) holder).editBioButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewModel.editBio();
                    }
                });
                ((Holder1) holder).editPhotoButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewModel.editPhoto();
                    }
                });

                if (viewModel == null) {
                    ((Holder1) holder).editPhotoButton.setVisibility(View.GONE);
                    ((Holder1) holder).editBioButton.setVisibility(View.GONE);
                }
                break;
            case VIEW_TYPE_LEFT:
            case VIEW_TYPE_RIGHT:
                if (position < promptArrayList.size()) {
                    ((Holder) holder).prompt.setText(promptArrayList.get(position).getPrompt());
                    ((Holder) holder).answer.setText(promptArrayList.get(position).getAnswer());
                    ((Holder) holder).caption.setText(promptArrayList.get(position).getCaption());
                    Glide.with(context)
                            .load(promptArrayList.get(position).getImage()).centerCrop()
                            .into(((Holder) holder).imageView);

                    ((Holder) holder).deleteButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(context, "Delete!", Toast.LENGTH_SHORT).show();
                            viewModel.deletePrompt(promptArrayList.get(position).getId());
                        }
                    });

                    if (viewModel == null)
                        ((Holder) holder).deleteButton.setVisibility(View.GONE);
                    break;
                }
            case VIEW_TYPE_SWIPE:

                if (viewModel != null) {
                    ((Holder2) holder).addButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            viewModel.addPrompt();
                        }
                    });
                } else {
                    ((Holder2) holder).seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                            listener.onSwipe(progress, seekBar, userProfile.getId());
                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {

                        }

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {

                        }
                    });
                }
        }
    }

    @Override
    public int getItemCount() {
        return promptArrayList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return VIEW_TYPE_INFO;
        else if (position == promptArrayList.size())
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
        private ImageView deleteButton;

        public Holder(@NonNull View itemView) {
            super(itemView);

            prompt = itemView.findViewById(R.id.text_prompt);
            answer = itemView.findViewById(R.id.text_answer);
            caption = itemView.findViewById(R.id.text_caption);
            imageView = itemView.findViewById(R.id.image_view);
            deleteButton = itemView.findViewById(R.id.button_delete);
        }
    }

    public class Holder1 extends RecyclerView.ViewHolder {
        private TextView name, location, profession, gender, height, race, religion, about, education;
        private CircleImageView imageView;
        private LinearLayout editBioButton;
        private ImageView editPhotoButton;

        public Holder1(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name_text);
            location = itemView.findViewById(R.id.textview_location);
            profession = itemView.findViewById(R.id.textview_profession);
            gender = itemView.findViewById(R.id.textview_gender);
            height = itemView.findViewById(R.id.textview_height);
            race = itemView.findViewById(R.id.textview_race);
            religion = itemView.findViewById(R.id.textview_religion);
            about = itemView.findViewById(R.id.textview_about);
            imageView = itemView.findViewById(R.id.profile_image_view);
            education = itemView.findViewById(R.id.textview_education);
            editBioButton = itemView.findViewById(R.id.button_edit_bio);
            editPhotoButton = itemView.findViewById(R.id.fab_edit_profile_photo);
        }
    }

    public class Holder2 extends RecyclerView.ViewHolder {
        private SeekBar seekBar;

        private TextView addButton;

        public Holder2(@NonNull View itemView) {
            super(itemView);

            if (viewModel != null)
                addButton = itemView.findViewById(R.id.button_add_prompt);
            else
                seekBar = itemView.findViewById(R.id.seekbar);
        }
    }
}
