package com.oho.oho.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.oho.oho.R;
import com.oho.oho.models.PreferenceInput;

import java.util.ArrayList;

public class PreferenceInputAdapter extends RecyclerView.Adapter<PreferenceInputAdapter.Holder> {
    private ArrayList<PreferenceInput> preferenceInputArrayList;
    private Context context;

    public PreferenceInputAdapter(ArrayList<PreferenceInput> preferenceInputArrayList, Context context) {
        this.preferenceInputArrayList = preferenceInputArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_preference_input, parent, false);;
        return new PreferenceInputAdapter.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.getPreferenceInputView().setText(preferenceInputArrayList.get(position).getInputName());
    }

    @Override
    public int getItemCount() {
        return preferenceInputArrayList.size();
    }

    public class Holder extends RecyclerView.ViewHolder{
        private TextView preferenceInputView;
        public Holder(@NonNull View itemView) {
            super(itemView);
            preferenceInputView = itemView.findViewById(R.id.text_input);
        }

        public TextView getPreferenceInputView() {
            return preferenceInputView;
        }
    }
}
