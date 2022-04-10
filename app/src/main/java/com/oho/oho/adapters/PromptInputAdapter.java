package com.oho.oho.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.oho.oho.R;

import java.util.ArrayList;

public class PromptInputAdapter extends RecyclerView.Adapter<PromptInputAdapter.PromptHolder> {

    private Context context;
    private ArrayList<String> promptList;
    private String promptSelected = "";

    public PromptInputAdapter(Context context, ArrayList<String> promptList) {
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
        holder.getTextView().setText(promptList.get(position));
    }

    @Override
    public int getItemCount() {
        return promptList.size();
    }

    public class PromptHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        public PromptHolder(@NonNull View itemView) {
            super(itemView);

            textView = (TextView) itemView.findViewById(R.id.text_prompt);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                        Log.d("RIFA","single input selected = "+promptSelected);
                    if (promptSelected.equals("")) {
                        textView.setBackgroundResource(R.drawable.input_selected_background);
                        textView.setTextColor(Color.parseColor("#FFFFFF"));
                        textView.setTag("selected");

                        promptSelected = textView.getText().toString();
//                        onInputSelectListener.onInputSelect(promptSelected);
                    } else {
                        if (textView.getText().equals(promptSelected)) {
                            textView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                            textView.setTextColor(Color.parseColor("#000000"));
                            textView.setTag("notselected");

                            promptSelected = "";
//                            onInputDeselectListener.onInputDeselect(textView.getText().toString());
                        } else
                            Toast.makeText(context, "Cannot select multiple religion!", Toast.LENGTH_SHORT).show();
                    }
                }

            });
        }
        public TextView getTextView() {
            return textView;
        }
    }
}
