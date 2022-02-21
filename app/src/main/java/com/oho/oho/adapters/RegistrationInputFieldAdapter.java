package com.oho.oho.adapters;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.oho.oho.R;
import com.oho.oho.models.RegistrationInput;
import com.oho.oho.views.listeners.OnInputSelectListener;

import java.util.ArrayList;

public class RegistrationInputFieldAdapter extends RecyclerView.Adapter<RegistrationInputFieldAdapter.InputHolder> {

    private ArrayList<RegistrationInput> localDataSet = new ArrayList<>();
    private static OnInputSelectListener onInputSelectListener;

    static int selectedItemPos = -1;
    static boolean multiSelectEnabled;

    public RegistrationInputFieldAdapter(ArrayList<RegistrationInput> dataSet, OnInputSelectListener onInputSelectListener, boolean multiSelectEnabled) {

        localDataSet.addAll(dataSet);
        Log.d("RegistrationInputFieldAdapter","education = "+localDataSet.size());

        this.onInputSelectListener = onInputSelectListener;
        RegistrationInputFieldAdapter.multiSelectEnabled = multiSelectEnabled;
    }

    @NonNull
    @Override
    public InputHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_input_registration, parent, false);

        return new InputHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InputHolder holder, int position) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        holder.getTextView().setText(localDataSet.get(position).getInput());
        if (localDataSet.get(position).isSelected()){
            holder.getTextView().setBackgroundResource(R.drawable.input_selected_background);
            holder.getTextView().setTextColor(Color.parseColor("#FFFFFF"));
            selectedItemPos = position;
        }
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

    public static class InputHolder extends RecyclerView.ViewHolder{
        private final TextView textView;
        public InputHolder(@NonNull View itemView) {
            super(itemView);
            // Define click listener for the ViewHolder's View

            textView = (TextView) itemView.findViewById(R.id.text_input);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!multiSelectEnabled) {
                        if (selectedItemPos == -1) {
                            textView.setBackgroundResource(R.drawable.input_selected_background);
                            textView.setTextColor(Color.parseColor("#FFFFFF"));
                            selectedItemPos = getAdapterPosition();
                            onInputSelectListener.onInputSelect(textView.getText().toString());
                        } else if (selectedItemPos == getAdapterPosition()) {
                            textView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                            textView.setTextColor(Color.parseColor("#AB3B61"));
                            selectedItemPos = -1;
                        } else {
                            Toast.makeText(v.getContext(), "Can only select one option!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (textView.getTag().equals("notselected")){
                            textView.setBackgroundResource(R.drawable.input_selected_background);
                            textView.setTextColor(Color.parseColor("#FFFFFF"));
                            textView.setTag("selected");
                        } else {
                            textView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                            textView.setTextColor(Color.parseColor("#AB3B61"));
                            textView.setTag("notselected");
                        }
                    }
                }
            });
        }
        public TextView getTextView() {
            return textView;
        }
    }
}
