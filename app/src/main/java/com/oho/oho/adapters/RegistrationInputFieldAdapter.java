package com.oho.oho.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.oho.oho.R;
import com.oho.oho.models.RegistrationInput;
import com.oho.oho.views.listeners.OnInputDeselectListener;
import com.oho.oho.views.listeners.OnInputSelectListener;

import java.util.ArrayList;

public class RegistrationInputFieldAdapter extends RecyclerView.Adapter<RegistrationInputFieldAdapter.InputHolder> {

    private static ArrayList<RegistrationInput> localDataSet;
    private static OnInputSelectListener onInputSelectListener;
    private static OnInputDeselectListener onInputDeselectListener;
    private static Context context;

    static int selectedItemPos = -1;
    boolean multiSelectEnabled;
    static String singleInputSelected = "";

    public RegistrationInputFieldAdapter(Context context, ArrayList<RegistrationInput> dataSet, OnInputSelectListener onInputSelectListener, OnInputDeselectListener onInputDeselectListener, boolean multiSelectEnabled) {
        RegistrationInputFieldAdapter.context = context;
        localDataSet = dataSet;
        Log.d("RegistrationInputFieldAdapter","education = "+localDataSet.size());

        RegistrationInputFieldAdapter.onInputSelectListener = onInputSelectListener;
        RegistrationInputFieldAdapter.onInputDeselectListener = onInputDeselectListener;

        this.multiSelectEnabled = multiSelectEnabled;
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
    public void onBindViewHolder(@NonNull InputHolder holder, @SuppressLint("RecyclerView") int position) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        holder.getTextView().setText(localDataSet.get(position).getInput());
        Log.d("vm","selected = "+localDataSet.get(position).isSelected());
        if (localDataSet.get(position).isSelected()){
            holder.getTextView().setBackgroundResource(R.drawable.input_selected_background);
            holder.getTextView().setTextColor(Color.parseColor("#FFFFFF"));
            holder.getTextView().setTag("selected");
            selectedItemPos = position;
        }
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

    public class InputHolder extends RecyclerView.ViewHolder{
        private final TextView textView;
        public InputHolder(@NonNull View itemView) {
            super(itemView);
            // Define click listener for the ViewHolder's View

            textView = (TextView) itemView.findViewById(R.id.text_input);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (multiSelectEnabled) {
                        Log.d("RIFA","single input selected = "+singleInputSelected);
                        if (textView.getTag().equals("notselected")) {
                            textView.setBackgroundResource(R.drawable.input_selected_background);
                            textView.setTextColor(Color.parseColor("#FFFFFF"));
                            textView.setTag("selected");
                            onInputSelectListener.onInputSelect(textView.getText().toString());
                        } else {
                            textView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                            textView.setTextColor(Color.parseColor("#AB3B61"));
                            textView.setTag("notselected");
                            onInputDeselectListener.onInputDeselect(textView.getText().toString());
                        }
                    }
                    else {
//                        Log.d("RIFA","single input selected = "+singleInputSelected);
                        if (singleInputSelected.equals("")){
                            textView.setBackgroundResource(R.drawable.input_selected_background);
                            textView.setTextColor(Color.parseColor("#FFFFFF"));
                            textView.setTag("selected");

                            singleInputSelected = textView.getText().toString();
                            onInputSelectListener.onInputSelect(singleInputSelected);
                        }
                        else {
                            if (textView.getText().equals(singleInputSelected)){
                                textView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                                textView.setTextColor(Color.parseColor("#AB3B61"));
                                textView.setTag("notselected");

                                singleInputSelected = "";
                                onInputDeselectListener.onInputDeselect(textView.getText().toString());
                            }
                            else
                                Toast.makeText(context, "Cannot select multiple religion!", Toast.LENGTH_SHORT).show();
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
