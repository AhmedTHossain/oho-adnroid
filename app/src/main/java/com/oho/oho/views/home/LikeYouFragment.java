package com.oho.oho.views.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.oho.oho.R;
import com.oho.oho.databinding.FragmentHomeBinding;
import com.oho.oho.databinding.FragmentLikeYouBinding;
import com.oho.oho.viewmodels.LikeYouVIewModel;

public class LikeYouFragment extends Fragment {

    FragmentLikeYouBinding binding;
    private LikeYouVIewModel viewModel;
    private ShimmerFrameLayout shimmerViewContainer;

    public LikeYouFragment() {
        // Required empty public constructor
    }

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentLikeYouBinding.inflate(inflater, container, false);
        shimmerViewContainer = binding.shimmerViewContainer;

        initLikeYouViewModel();
        getAllLikedProfiles();

        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    private void initLikeYouViewModel() {
        viewModel = new ViewModelProvider(this).get(LikeYouVIewModel.class);
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.shimmerViewContainer.startShimmerAnimation();
    }

    @Override
    public void onPause() {
        binding.shimmerViewContainer.stopShimmerAnimation();
        super.onPause();
    }

    public void getAllLikedProfiles(){
        //TODO: later use logged in user's user_id instead of the following hard coded one
        viewModel.getAllLikedByProfiles(18);
        viewModel.userList.observe(getViewLifecycleOwner(), userList -> {
//            Toast.makeText(requireContext(),"number of users = "+userList.size(),Toast.LENGTH_SHORT).show();

            shimmerViewContainer.stopShimmerAnimation();
            shimmerViewContainer.setVisibility(View.GONE);
        });
    }
}