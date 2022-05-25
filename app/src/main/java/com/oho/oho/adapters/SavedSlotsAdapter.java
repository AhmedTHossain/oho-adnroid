package com.oho.oho.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.oho.oho.R;

import java.util.ArrayList;

public class SavedSlotsAdapter extends RecyclerView.Adapter<SavedSlotsAdapter.ViewHolder> {

    private ArrayList<String> selectedSlotsList;

    public SavedSlotsAdapter(ArrayList<String> slotsList){
        selectedSlotsList = slotsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_available_time_slots, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        String[] slotArray = selectedSlotsList.get(position).split(", ");

        viewHolder.getDayTextView().setText(slotArray[0]+":");
        viewHolder.getTimeTextView().setText(slotArray[1]);
    }

    @Override
    public int getItemCount() {
        return selectedSlotsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView dayTextView, timeTextView;

        public ViewHolder(@NonNull View view) {
            super(view);

            dayTextView = view.findViewById(R.id.day_text);
            timeTextView = view.findViewById(R.id.time_text);
        }

        public TextView getDayTextView(){
            return dayTextView;
        }

        public TextView getTimeTextView(){
            return timeTextView;
        }
    }
}
