package com.oho.oho.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.oho.oho.R;

public class RegistrationInputFieldAdapter extends RecyclerView.Adapter<RegistrationInputFieldAdapter.InputHolder> {

    private String[] localDataSet;

    public RegistrationInputFieldAdapter(String[] dataSet) {
        localDataSet = dataSet;
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
        holder.getTextView().setText(localDataSet[position]);
    }

    @Override
    public int getItemCount() {
        return localDataSet.length;
    }

    public static class InputHolder extends RecyclerView.ViewHolder{
        private final TextView textView;
        public InputHolder(@NonNull View itemView) {
            super(itemView);
            // Define click listener for the ViewHolder's View

            textView = (TextView) itemView.findViewById(R.id.text_input);
        }
        public TextView getTextView() {
            return textView;
        }
    }
}
