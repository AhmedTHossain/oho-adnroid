package com.oho.oho.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.oho.oho.R;
import com.oho.oho.interfaces.RadioListItemClickListener;
import com.oho.oho.models.Cuisine;

import java.util.ArrayList;

public class RadioListItemAdapter extends RecyclerView.Adapter<RadioListItemAdapter.Holder> {

    private ArrayList<Cuisine> itemList;
    private Context context;
    private RadioListItemClickListener listener;

    public RadioListItemAdapter(ArrayList<Cuisine> itemList, Context context, RadioListItemClickListener listener) {
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
        if (itemList.get(position).getChecked())
            holder.radioButton.setChecked(true);
        else
            holder.radioButton.setChecked(false);

        holder.radioButton.setText(itemList.get(position).getName());
        holder.radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.radioListItemClick(position, !itemList.get(position).getChecked(),holder.radioButton.getText().toString());
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
