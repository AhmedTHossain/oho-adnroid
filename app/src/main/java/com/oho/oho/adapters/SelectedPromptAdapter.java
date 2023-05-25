package com.oho.oho.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.ybq.android.spinkit.SpinKitView;
import com.oho.oho.R;
import com.oho.oho.interfaces.OnPhotoPickerPrompt;
import com.oho.oho.interfaces.OnPromptAnswerListener;
import com.oho.oho.models.SelectedPrompt;

import java.util.ArrayList;

public class SelectedPromptAdapter extends RecyclerView.Adapter<SelectedPromptAdapter.Holder> {
    private ArrayList<SelectedPrompt> selectedPrompts;
    private Context context;
    private OnPhotoPickerPrompt photoPickerListener;
    private OnPromptAnswerListener promptAnswerListener;

    public SelectedPromptAdapter(ArrayList<SelectedPrompt> selectedPrompts, Context context, OnPhotoPickerPrompt photoPickerListener, OnPromptAnswerListener promptAnswerListener) {
        this.selectedPrompts = selectedPrompts;
        this.context = context;
        this.photoPickerListener = photoPickerListener;
        this.promptAnswerListener = promptAnswerListener;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_anser_prompt, parent, false);
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

    public class Holder extends RecyclerView.ViewHolder {

        private TextView promptText, buttonNext;
        private EditText answerEditText, captionEditText;
        private ImageView imageVIew;
        private SpinKitView progressView;

        public Holder(@NonNull View itemView) {
            super(itemView);

            promptText = itemView.findViewById(R.id.text_prompt);
            answerEditText = itemView.findViewById(R.id.text_answer);
            captionEditText = itemView.findViewById(R.id.text_caption_image);
            imageVIew = itemView.findViewById(R.id.image_prompt);
            buttonNext = itemView.findViewById(R.id.button_next);
            progressView = itemView.findViewById(R.id.upload_prompt_progress);

            imageVIew.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    photoPickerListener.onPhotoPick(imageVIew);
                }
            });
            buttonNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!TextUtils.isEmpty(answerEditText.getText())) {
                        if (!TextUtils.isEmpty(captionEditText.getText())) {
                            promptAnswerListener.onPromptAnswer(promptText.getText().toString(),answerEditText.getText().toString(),captionEditText.getText().toString(),progressView);
                        } else
                            Toast.makeText(context, "Please enter your Answer first!", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(context, "Please enter a Caption first!", Toast.LENGTH_SHORT).show();
                }
            });

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
