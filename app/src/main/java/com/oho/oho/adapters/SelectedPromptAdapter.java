package com.oho.oho.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.oho.oho.R;
import com.oho.oho.models.SelectedPrompt;

import java.util.ArrayList;

public class SelectedPromptAdapter extends RecyclerView.Adapter<SelectedPromptAdapter.Holder> {
    private ArrayList<SelectedPrompt> selectedPrompts;
    private Context context;

    public SelectedPromptAdapter(ArrayList<SelectedPrompt> selectedPrompts, Context context) {
        this.selectedPrompts = selectedPrompts;
        this.context = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_anser_prompt, parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.getPromptText().setText(selectedPrompts.get(position).getPrompt());
    }

    @Override
    public int getItemCount() {
        return selectedPrompts.size();
    }

    public class Holder extends RecyclerView.ViewHolder{

        private TextView promptText;
        private EditText answerEditText, captionEditText;
        private ImageView imageVIew;

        public Holder(@NonNull View itemView) {
            super(itemView);

            promptText = itemView.findViewById(R.id.text_prompt);
            answerEditText = itemView.findViewById(R.id.text_answer);
            captionEditText = itemView.findViewById(R.id.text_caption_image);
            imageVIew = itemView.findViewById(R.id.image_prompt);
        }

        public TextView getPromptText() {
            return promptText;
        }

        public EditText getAnswerEditText() {
            return answerEditText;
        }

        public EditText getCaptionEditText() {
            return captionEditText;
        }

        public ImageView getImageVIew() {
            return imageVIew;
        }
    }
}
