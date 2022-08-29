package com.oho.oho.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.oho.oho.R;
import com.oho.oho.interfaces.OnProfilePromptClickListener;
import com.oho.oho.interfaces.OnProfilePromptDeleteListener;
import com.oho.oho.models.PromptAnswer;

import java.util.ArrayList;

public class ProfilePromptAdapter extends RecyclerView.Adapter<ProfilePromptAdapter.ProfilePromptHolder> {

    private Context context;
    private ArrayList<PromptAnswer> profilePromptsList;
    private OnProfilePromptClickListener clickListener;
    private OnProfilePromptDeleteListener deleteListener;

    public ProfilePromptAdapter(Context context, ArrayList<PromptAnswer> profilePromptsList, OnProfilePromptClickListener clickListener, OnProfilePromptDeleteListener deleteListener) {
        this.context = context;
        this.profilePromptsList = profilePromptsList;
        this.clickListener = clickListener;
        this.deleteListener = deleteListener;
    }

    @NonNull
    @Override
    public ProfilePromptHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_layout_profile_prompts,parent,false);
        return new ProfilePromptHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfilePromptHolder holder, int position) {
        holder.getPromptText().setText(profilePromptsList.get(position).getPrompt());
        holder.getAnswerText().setText(profilePromptsList.get(position).getAnswer());
        if (position>2)
            holder.getDeleteButton().setVisibility(View.VISIBLE);
        else
            holder.getDeleteButton().setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return profilePromptsList.size();
    }

    public class ProfilePromptHolder extends RecyclerView.ViewHolder {

        private TextView promptText,answerText;
        private LinearLayout linearLayout;
        private ImageView deleteButton;

        public ProfilePromptHolder(View view) {
            super(view);

            promptText = view.findViewById(R.id.text_promp_profile);
            answerText = view.findViewById(R.id.text_answer_profile);
            linearLayout = view.findViewById(R.id.linearlayout);
            deleteButton = view.findViewById(R.id.button_delete);

            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onProfilePromptClick();
                }
            });

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO: later when API is integrated send prompt answer id, now its hard coded
                    deleteListener.onProfilePromptDelete(1);
                }
            });

        }
        public TextView getPromptText(){
            return promptText;
        }
        public TextView getAnswerText(){
            return answerText;
        }
        public ImageView getDeleteButton(){
            return deleteButton;
        }
    }
}
