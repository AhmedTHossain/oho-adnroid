package com.oho.oho.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.oho.oho.R;
import com.oho.oho.interfaces.QuickMessageClickListener;

import java.util.ArrayList;

public class QuickMessageAdapter extends RecyclerView.Adapter<QuickMessageAdapter.Holder> {

    private String[] quickMessageList;
    private Context context;
    private QuickMessageClickListener listener;

    public QuickMessageAdapter(String[] quickMessageList, Context context, QuickMessageClickListener listener) {
        this.quickMessageList = quickMessageList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_quick_message, parent, false);
        return new QuickMessageAdapter.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.getQuickMessageText().setText(quickMessageList[position]);
        holder.getQuickMessageText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onQuickMessageClick(quickMessageList[position]);
            }
        });
    }

    @Override
    public int getItemCount() {
        return quickMessageList.length-1;
    }

    public class Holder extends RecyclerView.ViewHolder {

        private TextView quickMessageText;

        public Holder(@NonNull View itemView) {
            super(itemView);
            quickMessageText = itemView.findViewById(R.id.textview_quickmessage);
        }

        public TextView getQuickMessageText() {
            return quickMessageText;
        }
    }
}
