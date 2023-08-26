package com.oho.oho.views.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.text.format.DateFormat;
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
import com.oho.oho.databinding.FragmentHomeBinding;
import com.oho.oho.interfaces.OnFullImageViewListener;
import com.oho.oho.interfaces.SwipeListener;
import com.oho.oho.models.Profile;
import com.oho.oho.models.PromptAnswer;
import com.oho.oho.models.Swipe;
import com.oho.oho.viewmodels.HomeViewModel;
import com.oho.oho.views.FullScreenImageViewActivity;
import com.oho.oho.views.settings.AvailabilitySettingsActivity;
import com.oho.oho.views.settings.PreferenceSettingsFragment;

import java.util.ArrayList;
import java.util.Date;

public class HomeFragment extends Fragment implements SwipeListener, View.OnClickListener, OnFullImageViewListener {

    FragmentHomeBinding binding;
    private Profile profile;
    MediaPlayer mp;
    private HomeViewModel homeViewModel;
    private ArrayList<Profile> recommendedProfiles;

    private int profileToShow = -1;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        recommendedProfiles = new ArrayList<>();

        profile = getProfile(requireContext());

        initHomeViewModel();

        changeUI();

        binding.openPreferenceSettingsButton.setOnClickListener(this);
        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    private void getProfileRecommendation(int id) {
        homeViewModel.getRecommendation(id);
        if (getView() != null) {
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
                    swipeProfile(profile.getId(), id, 0);
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
                    swipeProfile(profile.getId(), id, swipeDirection);

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

    public void setProfile(Profile user) {

        ArrayList<PromptAnswer> promptArrayList = (ArrayList<PromptAnswer>) user.getPromptAnswers();

//        User userProfile = new User(user.getAge(), user.getBio(), user.getBudget(), user.getCity(), user.getDob(), user.getEducation(), user.getEmail(), user.getHeight(), user.getId(), user.getLat(), user.getLon(), user.getName(), user.getOccupation(), user.getPhone(), user.getProfilePicture(), user.getPromptAnswers(), user.getRace(), user.getReligion(), user.getSex(), user.getState(), user.getVaccinated());

        ProfileDisplayAdapter adapter = new ProfileDisplayAdapter(user, promptArrayList, this, requireContext(), null,this);
        binding.recyclerviewPromptSection.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerviewPromptSection.setAdapter(adapter);
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
            if (isSuccessful) {
                profileToShow++;
                if (profileToShow < recommendedProfiles.size()) {
                    Profile user = recommendedProfiles.get(profileToShow);
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

    private void showNoRecommendationsDisclaimer() {
        binding.textTitleMessage.setText(R.string.no_recommendations);
        binding.textBodyMessage.setText(R.string.no_recommendations_disclaimer);
        binding.openPreferenceSettingsButton.setText(R.string.lets_set_some_preferences);
        binding.openPreferenceSettingsButton.setOnClickListener(this);
        binding.noRecommendationsAvailableLayout.setVisibility(View.VISIBLE);
        binding.progressLoadingRecommendations.setVisibility(View.GONE);
    }

    private void showNotAvailableDisclaimer() {
        binding.textTitleMessage.setText(R.string.not_available_disclaimer_title);
        binding.textBodyMessage.setText(R.string.availability_change_disclaimer);
        binding.openPreferenceSettingsButton.setText(R.string.yes_lets_start_matching);
        binding.openPreferenceSettingsButton.setOnClickListener(this);
        binding.progressLoadingRecommendations.setVisibility(View.GONE);
        binding.noRecommendationsAvailableLayout.setVisibility(View.VISIBLE);
    }

    private void showInDatingPhaseDisclaimer() {
        Log.d("HomeFragment", "inside showInDatingPhaseDisclaimer: YES");
        binding.textTitleMessage.setText(R.string.in_dating_phase);
        binding.textBodyMessage.setText(R.string.dating_phase_disclaimer);
        binding.openPreferenceSettingsButton.setVisibility(View.GONE);
        binding.progressLoadingRecommendations.setVisibility(View.GONE);
        binding.noRecommendationsAvailableLayout.setVisibility(View.VISIBLE);
    }

    private void alreadyReachedMaxNumberOfDates() {
        Log.d("HomeFragment", "inside showInDatingPhaseDisclaimer: YES");
        binding.textTitleMessage.setText(R.string.weekly_limit_reached);
        binding.textBodyMessage.setText(R.string.weekly_limit_reached_disclaimer);
        binding.openPreferenceSettingsButton.setVisibility(View.GONE);
        binding.progressLoadingRecommendations.setVisibility(View.GONE);
        binding.noRecommendationsAvailableLayout.setVisibility(View.VISIBLE);
    }

    private void showRecommendations() {
        Toast.makeText(getContext(), "Number of recommendations = " + recommendedProfiles.size(), Toast.LENGTH_LONG).show();
        binding.progressLoadingRecommendations.setVisibility(View.GONE);
        binding.progressLoadingRecommendations.setVisibility(View.GONE);
        binding.recommendationsAvailableLayout.setVisibility(View.VISIBLE);

        Profile user = recommendedProfiles.get(profileToShow);
        setProfile(user);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == binding.openPreferenceSettingsButton.getId()) {
            if (binding.openPreferenceSettingsButton.getText().equals(getString(R.string.lets_set_some_preferences)))
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, new PreferenceSettingsFragment())
                        .addToBackStack(null)
                        .commit();
            else
                startActivity(new Intent(requireActivity(), AvailabilitySettingsActivity.class));
        }
    }

    private void changeUI() {
        //finding which day of week is today in order to check if its the dating phase or matching phase. So that the appropriate UI can be shown based on that.
        Date date = new Date();
        CharSequence time = DateFormat.format("E", date.getTime()); // gives like (Wednesday)

        if (!String.valueOf(time).equals("Fri") && !String.valueOf(time).equals("Sat") && !String.valueOf(time).equals("Sun")) {
            getAvailabilityConsent();
        } else {
            showInDatingPhaseDisclaimer();
        }
    }

    private void getAvailabilityConsent() {
        Log.d("HomeFragment", "inside getAvailabilityConsent: YES");
        homeViewModel.checkIfAvailable(profile.getId());
        homeViewModel.isAvailable.observe(getViewLifecycleOwner(), isAvailable -> {
            if (isAvailable) {
                homeViewModel.getNumberOfDatesLeft(profile.getId());
                homeViewModel.numberOfDatesLeft.observe(getViewLifecycleOwner(), numberOfDatesLeft -> {
                    if (numberOfDatesLeft >= 1)
                        getProfileRecommendation(profile.getId());
                    if (numberOfDatesLeft == 0)
                        alreadyReachedMaxNumberOfDates();
                });

            } else
                showNotAvailableDisclaimer();
        });
    }
    public static Profile getProfile(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("ProfilePrefsFile", Context.MODE_PRIVATE);
        String jsonString = prefs.getString("PROFILE", null);
        if (jsonString != null) {
            return Profile.fromJsonString(jsonString);
        } else {
            // Return a default object or null if no object found in SharedPreferences
            return null;
        }
    }

    @Override
    public void onFullImageView(String imageUrl) {
        Intent intent = new Intent(requireContext(), FullScreenImageViewActivity.class);
        intent.putExtra("image_url",imageUrl);
        startActivity(intent);
    }
}