package com.oho.oho.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.oho.oho.R;
import com.oho.oho.models.PromptAnswer;

import java.util.ArrayList;

public class ProfilePromptAdapter extends RecyclerView.Adapter<ProfilePromptAdapter.ProfilePromptHolder> {

    private Context context;
    private ArrayList<PromptAnswer> profilePromptsList;

    public ProfilePromptAdapter(Context context, ArrayList<PromptAnswer> profilePromptsList) {
        this.context = context;
        this.profilePromptsList = profilePromptsList;
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
        holder.getPromptText().setText(profilePromptsList.get(position).getPromptQuestion());
        holder.getAnswerText().setText(profilePromptsList.get(position).getAnswer());
    }

    @Override
    public int getItemCount() {
        return profilePromptsList.size();
    }

    public class ProfilePromptHolder extends RecyclerView.ViewHolder {
        private TextView promptText,answerText;
        public ProfilePromptHolder(View view) {
            super(view);

            promptText = view.findViewById(R.id.text_promp_profile);
            answerText = view.findViewById(R.id.text_answer_profile);
        }
        public TextView getPromptText(){
            return promptText;
        }
        public TextView getAnswerText(){
            return answerText;
        }
    }
}
