package com.oho.oho.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.oho.oho.R;
import com.oho.oho.interfaces.OnPromptQuestionSelectedListener;
import com.oho.oho.models.Prompt;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class PromptAdapter extends RecyclerView.Adapter<PromptAdapter.Holder> {
    private Context context;
    private ArrayList<String> promptList;
    private OnPromptQuestionSelectedListener listener;

    public PromptAdapter(Context context, ArrayList<String> promptList, OnPromptQuestionSelectedListener listener) {
        this.context = context;
        this.promptList = promptList;
        this.listener = listener;

        Log.d("PromptAdapter","number of prompts = "+promptList.size());
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_prompt_questions,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.getTextView().setText(promptList.get(position));
    }

    @Override
    public int getItemCount() {
        return promptList.size();
    }

    public class Holder extends RecyclerView.ViewHolder{
        private TextView textView;
        public Holder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.text_prompt_question);

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onPromptQuestionSelected(promptList.get(getAdapterPosition()));
                }
            });
        }
        public TextView getTextView(){
            return textView;
        }
    }
}
