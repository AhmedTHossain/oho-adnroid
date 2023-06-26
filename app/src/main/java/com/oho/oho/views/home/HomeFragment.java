package com.oho.oho.views.home;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.oho.oho.R;
import com.oho.oho.adapters.ProfileDisplayAdapter;
import com.oho.oho.database.LocalDatabase;
import com.oho.oho.databinding.FragmentHomeBinding;
import com.oho.oho.entities.UserProfile;
import com.oho.oho.interfaces.SwipeListener;
import com.oho.oho.models.PromptAnswer;
import com.oho.oho.models.Swipe;
import com.oho.oho.models.User;
import com.oho.oho.viewmodels.HomeViewModel;
import com.oho.oho.views.settings.PreferenceSettingsFragment;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements SwipeListener, View.OnClickListener {

    FragmentHomeBinding binding;


    MediaPlayer mp;
    private HomeViewModel homeViewModel;
    private String user_type = "other";
    private ArrayList<User> recommendedProfiles;

    private int profileToShow = -1;

    String mockProfileDisplayed = "female";

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        recommendedProfiles = new ArrayList<>();

        initHomeViewModel();


        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                LocalDatabase localDatabase = LocalDatabase.getInstance(requireContext());
                UserProfile userProfile = localDatabase.userProfileDao().getUserProfile(1);
                Log.d("HomeFragment","user id in Home Fragment = "+userProfile.id);
                // Insert Data
                getProfileRecommendation(userProfile.id);
            }
        });

        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    private void getProfileRecommendation(int id) {
        homeViewModel.getRecommendation(id);
        homeViewModel.recommendedProfiles.observe(getViewLifecycleOwner(), recommendedList -> {
            if (recommendedList != null) {
                if (recommendedList.size() != 0) {
                    profileToShow = homeViewModel.getProfileToShow();
                    recommendedProfiles.addAll(recommendedList);
                    showRecommendations();
                } else
                    showNoRecommendationsDisclaimer();
            }
        });
    }

    @Override
    public void onSwipe(int swipeDirection, SeekBar seekBar, int id) {
//        playSwipeRightSound();
        Log.d("Home", "swipe value = " + swipeDirection);
        if (swipeDirection == -1) {
            vibrate();
            seekBar.setThumb(getResources().getDrawable(R.drawable.thumb_seekbar_left));
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //TODO: set the userId of the currently logged in user
                    swipeProfile(99, id, swipeDirection);
//                    if (user_type > 0) {
//                        requireActivity().getSupportFragmentManager().beginTransaction()
//                                .replace(R.id.frame_layout, new LikeYouFragment())
//                                .addToBackStack(null)
//                                .commit();
//                    } else {
////                        showMaleProfile();
//                    }
                }
            }, 250);
        } else if (swipeDirection == 1) {
            vibrate();
            seekBar.setThumb(getResources().getDrawable(R.drawable.thumb_seekbar_right));
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //TODO: set the userId of the currently logged in user
                    swipeProfile(99, id, swipeDirection);

//                    requireActivity().getSupportFragmentManager().beginTransaction()
//                            .replace(R.id.frame_layout, new MessagesFragment())
//                            .addToBackStack(null)
//                            .commit();
                }
            }, 250);
        } else {
            seekBar.setThumb(getResources().getDrawable(R.drawable.thumb_seekbar));
//                    Toast.makeText(requireContext(),"Still deciding...",Toast.LENGTH_SHORT).show();
        }
    }

    public void vibrate() {
        // get the VIBRATOR_SERVICE system service
        final Vibrator vibrator = (Vibrator) requireContext().getSystemService(Context.VIBRATOR_SERVICE);
        // this type of vibration requires API 29
        final VibrationEffect vibrationEffect2;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {

            // create vibrator effect with the constant EFFECT_CLICK
            vibrationEffect2 = VibrationEffect.createPredefined(VibrationEffect.EFFECT_DOUBLE_CLICK);

            // it is safe to cancel other vibrations currently taking place
            vibrator.cancel();

            vibrator.vibrate(vibrationEffect2);
        }
    }

    public void setProfile(User user) {

        ArrayList<PromptAnswer> promptArrayList = new ArrayList<>(user.getPromptAnswers());


        //so that all the prompts are shown without hiding the first and last one under the profile info view and the swipe view
        promptArrayList.add(0,null);
        promptArrayList.add(null);

        User userProfile = new User(user.getAge(),user.getBio(),user.getBudget(),user.getCity(),user.getDob(),user.getEducation(),user.getEmail(),user.getHeight(),user.getId(),user.getLat(),user.getLon(),user.getName(),user.getOccupation(),user.getPhone(),user.getProfilePicture(),user.getPromptAnswers(),user.getRace(),user.getReligion(),user.getSex(),user.getState(),user.getVaccinated());

//        ProfileDisplayAdapter adapter = new ProfileDisplayAdapter(promptArrayList, userProfile, this, requireContext(), user_type);
//        binding.recyclerviewPromptSection.setLayoutManager(new LinearLayoutManager(requireContext()));
//        binding.recyclerviewPromptSection.setAdapter(adapter);
    }

    private void initHomeViewModel() {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
    }

    private void swipeProfile(int userId, int profileShown, int direction) {
        Swipe swipeProfile = new Swipe();
        swipeProfile.setUserId(userId);
        swipeProfile.setProfileShown(profileShown);
        swipeProfile.setDirection(direction);
        homeViewModel.swipeUserProfile(swipeProfile);
        homeViewModel.isSwipeSuccessful.observe(getViewLifecycleOwner(), isSuccessful -> {
            if (isSuccessful){
                profileToShow++;
                if (profileToShow < recommendedProfiles.size()){
                    User user = recommendedProfiles.get(profileToShow);
                    setProfile(user);
                } else
                    showNoRecommendationsDisclaimer();
            }
        });
    }

    private void playSwipeRightSound() {
        mp = MediaPlayer.create(requireContext(), R.raw.positive);
        mp.start();
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });
    }

    private void showNoRecommendationsDisclaimer(){
       binding.noRecommendationsAvailableLayout.setVisibility(View.VISIBLE);
       binding.openPreferenceSettingsButton.setOnClickListener(this);
    }

    private void noMoreRecommendationsDisclaimer(){
        binding.noMoreRecommendationsLayout.setVisibility(View.VISIBLE);
        binding.recommendationsAvailableLayout.setVisibility(View.GONE);
        binding.progressLoadingRecommendations.setVisibility(View.GONE);
    }

    private void showRecommendations() {
        Toast.makeText(getContext(),"Number of recommendations = "+recommendedProfiles.size(),Toast.LENGTH_LONG).show();
        binding.progressLoadingRecommendations.setVisibility(View.GONE);
        binding.recommendationsAvailableLayout.setVisibility(View.VISIBLE);

        User user = recommendedProfiles.get(profileToShow);
        setProfile(user);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == binding.openPreferenceSettingsButton.getId()){
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_layout, new PreferenceSettingsFragment())
                    .addToBackStack(null)
                    .commit();
        }
    }
}