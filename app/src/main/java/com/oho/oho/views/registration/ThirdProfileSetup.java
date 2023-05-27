package com.oho.oho.views.registration;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.oho.oho.R;
import com.oho.oho.databinding.FragmentThirdProfileSetupBinding;
import com.oho.oho.interfaces.OnProfileSetupScreenChange;
import com.oho.oho.models.Profile;
import com.oho.oho.viewmodels.ProfileSetupViewModel;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import nl.bryanderidder.themedtogglebuttongroup.ThemedButton;

public class ThirdProfileSetup extends Fragment {
    private OnProfileSetupScreenChange listener;
    FragmentThirdProfileSetupBinding binding;
    private ProfileSetupViewModel viewmodel;
    private String city, state, religion;
    private double lat,lon;

    public ThirdProfileSetup() {
        // Required empty public constructor
    }

    public ThirdProfileSetup(OnProfileSetupScreenChange listener) {
        this.listener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentThirdProfileSetupBinding.inflate(inflater, container, false);

        binding.buttonNextThird.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (city != null){
                    if (religion != null) {
                        viewmodel = new ViewModelProvider(requireActivity()).get(ProfileSetupViewModel.class);

                        Profile profile = viewmodel.getNewUserProfile().getValue();
                        profile.setCity(city);
                        profile.setState(state);
                        profile.setLon(lon);
                        profile.setLat(lat);
                        profile.setReligion(religion);

                        viewmodel.updateNewUserProfile(profile);
                        viewmodel.newUserProfile.observe(requireActivity(), newUserProfile -> {
                            Log.d("ThirdProfileSetup", "city stored in viewmodel: " + newUserProfile.getCity());
                        });

                        listener.onScreenChange("next", "third");
                    } else
                        Toast.makeText(requireContext(), "Please select your Religion first!", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(requireContext(), "Please enter your Location first!", Toast.LENGTH_SHORT).show();
            }
        });

        binding.textviewLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLocationInputDialog();
            }
        });

        binding.buttonGroupReligion.setOnSelectListener((ThemedButton btn) -> {
            religion = btn.getText();
            // handle selected button
            return kotlin.Unit.INSTANCE;
        });

        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    private void showLocationInputDialog() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext());
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location location = task.getResult();
                    if (location != null) {

                        lat = location.getLatitude();
                        lon = location.getLongitude();

                        try {
                            Geocoder geocoder = new Geocoder(requireContext(), Locale.ENGLISH);
                            List<Address> addresses = geocoder.getFromLocation(
                                    location.getLatitude(), location.getLongitude(), 2
                            );

                            city = addresses.get(1).getLocality();
                            state = addresses.get(1).getAdminArea();

                            String locationText = city + ", " + state;
                            binding.textviewLocation.setText(locationText);

                            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        } else {
            binding.textviewLocation.setText("Tap again to set your location");
            //when permission denied
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }
    }
}