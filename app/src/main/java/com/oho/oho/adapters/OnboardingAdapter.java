package com.oho.oho.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;

import com.oho.oho.R;
import com.oho.oho.models.OnboardingItem;

import java.util.List;

public class OnboardingAdapter extends RecyclerView.Adapter<OnboardingAdapter.OnboardingViewHolder> {

    private List<OnboardingItem> onboardingItemList;
    private Context context;

    public OnboardingAdapter(List<OnboardingItem> onboardingItemList, Context context) {
        this.onboardingItemList = onboardingItemList;
        this.context = context;
    }

    @NonNull
    @Override
    public OnboardingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OnboardingViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.item_conatiner_boarding_one, parent, false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull OnboardingViewHolder holder, int position) {
        holder.setOnboardingData(onboardingItemList.get(position));
    }

    @Override
    public int getItemCount() {
        return onboardingItemList.size();
    }

    class OnboardingViewHolder extends RecyclerView.ViewHolder {

//        private TextView titleText;
//        private TextView descriptionText;
//        private ImageView imageView;

        private LinearLayout linearLayout;

        OnboardingViewHolder(@NonNull View itemView) {
            super(itemView);
//            titleText = itemView.findViewById(R.id.text_title);
//            descriptionText = itemView.findViewById(R.id.text_description);
//            imageView = itemView.findViewById(R.id.image);

            linearLayout = itemView.findViewById(R.id.linear_layout);
        }

        void setOnboardingData(OnboardingItem onboardingItem){
//            titleText.setText(onboardingItem.getTitle());
//            descriptionText.setText(onboardingItem.getDescription());
//            imageView.setImageResource(onboardingItem.getImage());
            if (onboardingItem.getTitle().equals("Start  Searching..."))
                linearLayout.setBackground(AppCompatResources.getDrawable(context,R.drawable.onboarding1));
            else if (onboardingItem.getTitle().equals("Match!"))
                linearLayout.setBackground(AppCompatResources.getDrawable(context,R.drawable.onboarding2));
            else
                linearLayout.setBackground(AppCompatResources.getDrawable(context,R.drawable.onboarding3));
        }
    }
}
