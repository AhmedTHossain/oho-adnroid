package com.oho.oho.adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.oho.oho.R;
import com.oho.oho.interfaces.OnProfilePromptClickListener;
import com.oho.oho.interfaces.OnProfilePromptDeleteListener;
import com.oho.oho.models.PromptAnswer;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class CompleteProfileAdapter extends RecyclerView.Adapter {

    private final int VIEW_TYPE_PROFILE_INFO = 0;
    private final int VIEW_TYPE_PROFILE_PROMPT = 1;
    private ArrayList<PromptAnswer> profilePromptsList;
    private Context context;

    private OnProfilePromptClickListener promptClickListener;
    private OnProfilePromptDeleteListener deleteListener;

    private Animation animShow, animHide;

    public CompleteProfileAdapter(ArrayList<PromptAnswer> profilePromptsList, OnProfilePromptClickListener promptClickListener, OnProfilePromptDeleteListener deleteListener, Context context) {
        this.profilePromptsList = profilePromptsList;
        this.context = context;
        this.promptClickListener = promptClickListener;
        this.deleteListener = deleteListener;

        animShow = AnimationUtils.loadAnimation(context, R.anim.view_show);
        animHide = AnimationUtils.loadAnimation(context, R.anim.view_hide);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case VIEW_TYPE_PROFILE_INFO:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_add_profile_info, parent, false);
                return new ProfileInfoHolder(view);
            case VIEW_TYPE_PROFILE_PROMPT:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout_profile_prompts, parent, false);
                return new PromptInfoHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case VIEW_TYPE_PROFILE_INFO:
                ((ProfileInfoHolder) holder).aboutText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (s.length() > 0) {
                            if ( ((ProfileInfoHolder) holder).updateAboutButton.getVisibility() != View.VISIBLE) {
                                ((ProfileInfoHolder) holder).updateAboutButton.startAnimation(animShow);
                                ((ProfileInfoHolder) holder).updateAboutButton.setVisibility(View.VISIBLE);
                            }
                        } else {
                            ((ProfileInfoHolder) holder).updateAboutButton.setVisibility(View.GONE);
                            ((ProfileInfoHolder) holder).updateAboutButton.startAnimation(animHide);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                ((ProfileInfoHolder) holder).updateAboutButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO: call update profile API
                    }
                });
                ((ProfileInfoHolder) holder).addPromptButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO: add new prompt section
                    }
                });
                break;
            case VIEW_TYPE_PROFILE_PROMPT:
                if (profilePromptsList.get(position).getId() == 0){
                    ((PromptInfoHolder) holder).promptText.setText("Select your prompt question.");
                    ((PromptInfoHolder) holder).answerText.setVisibility(View.GONE);

                    if (profilePromptsList.get(position).getImage() != null) {
                        Glide.with(context)
                                .load(profilePromptsList.get(position).getImage())
                                .into((((PromptInfoHolder) holder).promptImage));
                    }
                }
                ((PromptInfoHolder) holder).editPromptButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        promptClickListener.onProfilePromptClick();
                    }
                });
                ((PromptInfoHolder) holder).deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteListener.onProfilePromptDelete(1);
                    }
                });
                break;
        }
    }

    @Override
    public int getItemCount() {
        return profilePromptsList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return VIEW_TYPE_PROFILE_INFO;
        else
            return VIEW_TYPE_PROFILE_PROMPT;
    }

    private class ProfileInfoHolder extends RecyclerView.ViewHolder {
        private RelativeLayout selectProfilePhotoButton;
        private EditText aboutText;
        private LinearLayout addPromptButton;
        private TextView updateAboutButton;

        public ProfileInfoHolder(View view) {
            super(view);
            selectProfilePhotoButton = view.findViewById(R.id.button_update_profile_photo);
            aboutText = view.findViewById(R.id.edittext_about);
            addPromptButton = view.findViewById(R.id.button_add_prompt);
            updateAboutButton = view.findViewById(R.id.button_update_about);
        }
    }

    public class PromptInfoHolder extends RecyclerView.ViewHolder {
        private TextView promptText,answerText;
        private LinearLayout editPromptButton;
        private ImageView deleteButton;
        private CircleImageView promptImage;
        public PromptInfoHolder(View view) {
            super(view);
            promptText = view.findViewById(R.id.text_promp_profile);
            answerText = view.findViewById(R.id.text_answer_profile);
            editPromptButton = view.findViewById(R.id.linearlayout);
            deleteButton = view.findViewById(R.id.button_delete);
            promptImage = view.findViewById(R.id.photo_image_view);
        }
    }
}
