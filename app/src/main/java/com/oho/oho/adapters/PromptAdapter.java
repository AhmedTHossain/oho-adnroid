package com.oho.oho.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.oho.oho.R;
import com.oho.oho.interfaces.OnPromptQuestionSelectedListener;
import com.oho.oho.interfaces.OnPromptSelectListener;
import com.oho.oho.models.Prompt;
import com.oho.oho.models.SelectedPrompt;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class PromptAdapter extends RecyclerView.Adapter<PromptAdapter.Holder> {
    private Context context;
    private ArrayList<SelectedPrompt> promptList;
    private OnPromptSelectListener listener;

    public PromptAdapter(Context context, ArrayList<SelectedPrompt> promptList, OnPromptSelectListener listener) {
        this.context = context;
        this.promptList = promptList;
        this.listener = listener;

        Log.d("PromptAdapter", "number of prompts = " + promptList.size());
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_prompt_questions, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.getTextView().setText(promptList.get(holder.getAdapterPosition()).getPrompt());
        if (promptList.get(holder.getAdapterPosition()).getIsSelected())
            holder.getTextView().setBackground(ContextCompat.getDrawable(context, R.drawable.border_gradient_drawable));
        else
            holder.getTextView().setBackground(ContextCompat.getDrawable(context, R.drawable.border_drawable));
    }

    @Override
    public int getItemCount() {
        return promptList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        private TextView textView;

        public Holder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.text_prompt_question);

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onPromptSelect(promptList.get(getAdapterPosition()));
                }
            });
        }

        public TextView getTextView() {
            return textView;
        }
    }
}
