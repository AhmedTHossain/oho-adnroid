package com.oho.oho.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.oho.oho.R;
import com.oho.oho.interfaces.OnPreferenceSelected;
import com.oho.oho.models.PreferenceInput;
import com.oho.oho.viewmodels.PreferenceSettingsViewModel;

import java.util.ArrayList;

public class PreferenceInputAdapter extends RecyclerView.Adapter<PreferenceInputAdapter.Holder> {
    private ArrayList<PreferenceInput> preferenceInputArrayList;
    private Context context;
    private PreferenceSettingsViewModel viewModel;
    private String preferenceFor;
    private OnPreferenceSelected listener;

    public PreferenceInputAdapter(ArrayList<PreferenceInput> preferenceInputArrayList, Context context, PreferenceSettingsViewModel viewModel, String preferenceFor, OnPreferenceSelected listener) {
        this.preferenceInputArrayList = preferenceInputArrayList;
        this.context = context;
        this.viewModel = viewModel;
        this.preferenceFor = preferenceFor;
        this.listener = listener;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_preference_input, parent, false);
        ;
        return new PreferenceInputAdapter.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.getPreferenceInputView().setText(preferenceInputArrayList.get(holder.getAbsoluteAdapterPosition()).getInputName());

        Log.d("PreferenceInputAdapter", "gender selected = " + preferenceInputArrayList.get(holder.getAbsoluteAdapterPosition()).getInputName());
        if (preferenceInputArrayList.get(holder.getAbsoluteAdapterPosition()).isSelected()) {
            holder.getPreferenceInputView().setTextColor(ResourcesCompat.getColor(context.getResources(), R.color.white, null));
            holder.getPreferenceInputView().setBackground(ResourcesCompat.getDrawable(context.getResources(), R.drawable.rounded_button_solid, null));
        } else {
            holder.getPreferenceInputView().setTextColor(ResourcesCompat.getColor(context.getResources(), R.color.black, null));
            holder.getPreferenceInputView().setBackground(ResourcesCompat.getDrawable(context.getResources(), R.drawable.border_corner_drawable, null));
        }
        holder.getPreferenceInputView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onSelect(preferenceInputArrayList.get(position).isSelected(), preferenceInputArrayList.get(position).getInputName(), preferenceFor);
            }
        });
    }

    @Override
    public int getItemCount() {
        return preferenceInputArrayList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
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
