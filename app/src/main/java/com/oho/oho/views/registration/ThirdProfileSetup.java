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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.oho.oho.R;
import com.oho.oho.databinding.FragmentThirdProfileSetupBinding;
import com.oho.oho.interfaces.OnProfileSetupScreenChange;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class ThirdProfileSetup extends Fragment {
    private OnProfileSetupScreenChange listener;
    FragmentThirdProfileSetupBinding binding;
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
                listener.onScreenChange("next","third");
            }
        });

        binding.textviewLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLocationInputDialog();
            }
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

                        double lat = location.getLatitude();
                        double lon = location.getLongitude();

                        try {
                            Geocoder geocoder = new Geocoder(requireContext(), Locale.US);
                            List<Address> addresses = geocoder.getFromLocation(
                                    location.getLatitude(), location.getLongitude(), 1
                            );

                            String city = addresses.get(0).getLocality();
                            String state = addresses.get(0).getAdminArea();

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