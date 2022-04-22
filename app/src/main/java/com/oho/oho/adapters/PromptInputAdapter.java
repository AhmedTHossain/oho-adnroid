package com.oho.oho.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.oho.oho.R;
import com.oho.oho.models.Prompt;

import java.util.ArrayList;

public class PromptInputAdapter extends RecyclerView.Adapter<PromptInputAdapter.PromptHolder> {

    private Context context;
    private ArrayList<Prompt> promptList;
    private int promptSelected = 0;

    public PromptInputAdapter(Context context, ArrayList<Prompt> promptList) {
        this.context = context;
        this.promptList = promptList;
    }

    @NonNull
    @Override
    public PromptHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_prompt, parent, false);
        return new PromptHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PromptHolder holder, int position) {
        holder.getTextView().setText(promptList.get(position).getName());
        if (promptList.get(position).isSelected()){
            holder.getTextView().setBackgroundResource(R.drawable.input_selected_background);
            holder.getTextView().setTextColor(Color.parseColor("#FFFFFF"));
        }
        else {
            holder.getTextView().setBackgroundColor(context.getResources().getColor(R.color.white,null));
            holder.getTextView().setTextColor(context.getResources().getColor(R.color.black,null));
        }
    }

    @Override
    public int getItemCount() {
        return promptList.size();
    }

    public class PromptHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        public PromptHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.text_prompt);

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (promptSelected == 0){
                        promptList.get(getAdapterPosition()).setSelected(true);
                        promptSelected = promptList.get(getAdapterPosition()).getPromptId();
                    }
                    else {
                        if (promptSelected == promptList.get(getAdapterPosition()).getPromptId()){
                            promptList.get(getAdapterPosition()).setSelected(false);
                            promptSelected = 0;
                        }
                        else
                            Toast.makeText(context, "Cannot select multiple.", Toast.LENGTH_SHORT).show();
                    }


                    SharedPreferences sharedPref = context.getSharedPreferences("prompt_selected", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putInt("id", promptSelected);
                    editor.putString("question",promptList.get(getAdapterPosition()).getName());
                    editor.apply();

                    notifyDataSetChanged();
                }
            });
        }
        public TextView getTextView() {
            return textView;
        }
    }
}
