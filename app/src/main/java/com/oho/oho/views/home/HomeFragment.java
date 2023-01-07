package com.oho.oho.views.home;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.oho.oho.R;
import com.oho.oho.adapters.ProfileDisplayAdapter;
import com.oho.oho.databinding.FragmentHomeBinding;
import com.oho.oho.interfaces.SwipeListener;
import com.oho.oho.models.PromptAnswer;
import com.oho.oho.models.Swipe;
import com.oho.oho.models.User;
import com.oho.oho.viewmodels.HomeViewModel;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements SwipeListener {

    FragmentHomeBinding binding;
    ArrayList<PromptAnswer> promptArrayList = new ArrayList<>();
    User userProfile;
    MediaPlayer mp;
    private HomeViewModel homeViewModel;
    private int user_type;

    String mockProfileDisplayed = "female";

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);

        initHomeViewModel();

        Bundle arguments = getArguments();
        if (arguments != null) {
            user_type = arguments.getInt("USERTYPE");
            if (user_type > 0) {
                userProfile = arguments.getParcelable("USERPROFILE");

                promptArrayList.addAll(userProfile.getPromptAnswers());

                setProfile(promptArrayList, userProfile);

                binding.screentitle.setText("Liked Your Profile");
            }
        }

        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onSwipe(int swipeDirection, SeekBar seekBar) {
//        playSwipeRightSound();
        Log.d("Home", "swipe value = " + swipeDirection);
        if (swipeDirection == -1) {
            vibrate();
            seekBar.setThumb(getResources().getDrawable(R.drawable.thumb_seekbar_left));
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //TODO: show the next recommended profile from the API
                    swipeProfile(1, 2, 0);
                    if (user_type > 0) {
                        requireActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frame_layout, new LikeYouFragment())
                                .addToBackStack(null)
                                .commit();
                    } else {
//                        showMaleProfile();
                    }
                }
            }, 250);
        } else if (swipeDirection == 1) {
            vibrate();
            seekBar.setThumb(getResources().getDrawable(R.drawable.thumb_seekbar_right));
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //TODO: show the next recommended profile from the API
                    swipeProfile(1, 2, 1);

                    requireActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_layout, new MessagesFragment())
                            .addToBackStack(null)
                            .commit();
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

    public void setProfile(ArrayList<PromptAnswer> promptArrayList, User userProfile) {

        //so that all the prompts are shown without hiding the first and last one under the profile info view and the swipe view
        promptArrayList.add(0,null);
        promptArrayList.add(null);

        ProfileDisplayAdapter adapter = new ProfileDisplayAdapter(promptArrayList, userProfile, this, requireContext());
        binding.recyclerviewPromptSection.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerviewPromptSection.setAdapter(adapter);
    }

    private void initHomeViewModel() {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
    }

    private void swipeProfile(int userId, int profileShown, int direction) {
        Swipe swipeProfile = new Swipe();
        swipeProfile.setUserId(1);
        swipeProfile.setProfileShown(2);
        swipeProfile.setDirection(direction);
        homeViewModel.swipeUserProfile(swipeProfile);
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

}