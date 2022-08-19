package com.oho.oho.views.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.Toast;

import com.oho.oho.R;
import com.oho.oho.databinding.FragmentHomeBinding;

import java.util.Calendar;

public class HomeFragment extends Fragment {

    FragmentHomeBinding binding;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);

        Calendar c = Calendar.getInstance();

        binding.seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress == -1) {
                    binding.seekbar.setThumb(getResources().getDrawable(R.drawable.thumb_seekbar_left));
//                    Toast.makeText(requireContext(),"Swiped Left :(",Toast.LENGTH_SHORT).show();
                }
                else if (progress == 1) {
                    binding.seekbar.setThumb(getResources().getDrawable(R.drawable.thumb_seekbar_right));
//                    Toast.makeText(requireContext(),"Swiped Right :)",Toast.LENGTH_SHORT).show();
                }
                else {
                    binding.seekbar.setThumb(getResources().getDrawable(R.drawable.thumb_seekbar));
//                    Toast.makeText(requireContext(),"Still deciding...",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        // Inflate the layout for this fragment
        return binding.getRoot();
    }
}