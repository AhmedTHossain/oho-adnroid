package com.oho.oho.views.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.oho.oho.MainActivity;
import com.oho.oho.R;
import com.oho.oho.adapters.LikeYouAdapter;
import com.oho.oho.databinding.FragmentLikeYouBinding;
import com.oho.oho.interfaces.OnProfileClickListener;
import com.oho.oho.models.Profile;
import com.oho.oho.viewmodels.LikeYouVIewModel;
import com.oho.oho.views.settings.PreferenceSettingsFragment;

import java.util.ArrayList;
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
        viewModel.getNumberOfDates(187);
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
        viewModel.getAllLikedByProfiles(187);
        viewModel.userList.observe(getViewLifecycleOwner(), userList -> {
//            Toast.makeText(requireContext(),"number of users = "+userList.size(),Toast.LENGTH_SHORT).show();
            if (userList != null) {
                Toast.makeText(requireContext(), "number of people liked profile = " + userList.size(), Toast.LENGTH_LONG).show();
                setRecyclerview(userList);
            }
            shimmerViewContainer.stopShimmerAnimation();
            shimmerViewContainer.setVisibility(View.GONE);
        });
    }

    public void setRecyclerview(List<Profile> userList) {
        ArrayList<Profile> userArrayList = new ArrayList<>(userList);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(), 2);
        binding.recyclerview.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView

        LikeYouAdapter adapter = new LikeYouAdapter(requireContext(), userArrayList, this);
        binding.recyclerview.setAdapter(adapter);
    }

    @Override
    public void onProfileClick(Profile user) {

        LikedByFragment fragment = new LikedByFragment();

        Bundle arguments = new Bundle();
        arguments.putString("USERTYPE", "liked");
        arguments.putParcelable("USERPROFILE", user);
        fragment.setArguments(arguments);

        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, fragment)
                .addToBackStack(null)
                .commit();
    }

    public void showMaxDatesReachedDialog() {
        LayoutInflater li = LayoutInflater.from(requireContext());
        View promptsView = li.inflate(R.layout.max_number_of_dates_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                requireContext());
        // set alert_dialog.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", null);
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                button.setTextColor(ContextCompat.getColor(requireContext(), R.color.indicatioractive));
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
//                        onBackPressed();
//                        finish();
//                        requireActivity().getSupportFragmentManager().beginTransaction()
//                                .replace(R.id.frame_layout, new HomeFragment())
//                                .addToBackStack(null)
//                                .commit();
                        startActivity(new Intent(requireActivity(), MainActivity.class));
                    }
                });
            }
        });
        // show it
        alertDialog.show();
    }
}