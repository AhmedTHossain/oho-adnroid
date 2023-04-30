package com.oho.oho.views.home;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.oho.oho.R;
import com.oho.oho.adapters.ProfileDisplayAdapter;
import com.oho.oho.databinding.FragmentHomeBinding;
import com.oho.oho.databinding.FragmentLikedByBinding;
import com.oho.oho.interfaces.SwipeListener;
import com.oho.oho.models.PromptAnswer;
import com.oho.oho.models.Swipe;
import com.oho.oho.models.User;
import com.oho.oho.viewmodels.HomeViewModel;
import com.oho.oho.viewmodels.LikedByViewModel;
import com.oho.oho.views.LoginActivity;
import com.oho.oho.views.settings.AccountSettingsActivity;
import com.oho.oho.views.settings.PreferenceSettingsFragment;

import java.util.ArrayList;
import java.util.List;

public class LikedByFragment extends Fragment implements SwipeListener{

    FragmentLikedByBinding binding;
    private LikedByViewModel likedByViewModel;

    public LikedByFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentLikedByBinding.inflate(inflater, container, false);
        initLikedByViewModel();

        Bundle arguments = getArguments();
        if (arguments != null) {
            setProfile(arguments.getParcelable("USERPROFILE"));
        }

        return binding.getRoot();
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

        ProfileDisplayAdapter adapter = new ProfileDisplayAdapter(promptArrayList, userProfile, this, requireContext(),"other");
        binding.recyclerviewPromptSection.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerviewPromptSection.setAdapter(adapter);
    }

    private void initLikedByViewModel() {
        likedByViewModel = new ViewModelProvider(this).get(LikedByViewModel.class);
    }

    private void swipeProfile(int userId, int profileShown, int direction) {
        Swipe swipeProfile = new Swipe();
        swipeProfile.setUserId(userId);
        swipeProfile.setProfileShown(profileShown);
        swipeProfile.setDirection(direction);
        likedByViewModel.swipeUserProfile(swipeProfile);
        likedByViewModel.isSwipeSuccessful.observe(getViewLifecycleOwner(), isSuccessful -> {
            if (isSuccessful)
                backToLikeYouScreen();
        });
    }

    private void backToLikeYouScreen(){
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, new LikeYouFragment())
                .addToBackStack(null)
                .commit();
    }
}