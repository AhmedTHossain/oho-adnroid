package com.oho.oho.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.oho.oho.R;
import com.oho.oho.interfaces.RadioListItemClickListener;

import java.util.ArrayList;

public class RadioListItemAdapter extends RecyclerView.Adapter<RadioListItemAdapter.Holder> {

    private ArrayList<String> itemList;
    private Context context;
    private RadioListItemClickListener listener;

    public RadioListItemAdapter(ArrayList<String> itemList, Context context, RadioListItemClickListener listener) {
        this.itemList = itemList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_list_radio_buttons, parent, false);
        return new RadioListItemAdapter.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.radioButton.setText(itemList.get(position));
        holder.radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                listener.radioListItemClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        private CheckBox radioButton;

        public Holder(@NonNull View itemView) {
            super(itemView);
            radioButton = itemView.findViewById(R.id.checkbox_item);
        }
    }
}
