package com.oho.oho.views.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.oho.oho.R;
import com.oho.oho.adapters.LikeYouAdapter;
import com.oho.oho.databinding.FragmentHomeBinding;
import com.oho.oho.databinding.FragmentLikeYouBinding;
import com.oho.oho.interfaces.OnProfileClickListener;
import com.oho.oho.models.User;
import com.oho.oho.viewmodels.LikeYouVIewModel;
import com.oho.oho.views.LoginActivity;
import com.oho.oho.views.settings.AccountSettingsActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LikeYouFragment extends Fragment implements OnProfileClickListener {

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
        //TODO: replace with logged in user's id
        viewModel.getNumberOfDates(99);
        viewModel.isDateAvailable.observe(getViewLifecycleOwner(), isAvailable -> {
            if (!isAvailable)
                showMaxDatesReachedDialog();
        });
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

    public void getAllLikedProfiles() {
        //TODO: later use logged in user's user_id instead of the following hard coded one
        viewModel.getAllLikedByProfiles(99);
        viewModel.userList.observe(getViewLifecycleOwner(), userList -> {
//            Toast.makeText(requireContext(),"number of users = "+userList.size(),Toast.LENGTH_SHORT).show();
            if (userList != null) {
                Toast.makeText(requireContext(),"number of people liked profile = "+userList.size(),Toast.LENGTH_LONG).show();
                setRecyclerview(userList);
            }
            shimmerViewContainer.stopShimmerAnimation();
            shimmerViewContainer.setVisibility(View.GONE);
        });
    }

    public void setRecyclerview(List<User> userList) {
        ArrayList<User> userArrayList = new ArrayList<>(userList);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(), 2);
        binding.recyclerview.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView

        LikeYouAdapter adapter = new LikeYouAdapter(requireContext(), userArrayList, this);
        binding.recyclerview.setAdapter(adapter);
    }

    @Override
    public void onProfileClick(User user) {

        LikedByFragment fragment = new LikedByFragment();

        Bundle arguments = new Bundle();
        arguments.putString( "USERTYPE" , "liked");
        arguments.putParcelable("USERPROFILE",user);
        fragment.setArguments(arguments);

        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, fragment)
                .addToBackStack(null)
                .commit();
    }

    public void showMaxDatesReachedDialog(){
        LayoutInflater li = LayoutInflater.from(requireContext());
        View promptsView = li.inflate(R.layout.max_number_of_dates_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                requireContext());
        // set alert_dialog.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);
        alertDialogBuilder.setCancelable(false);
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();
    }
}