package com.oho.oho.views.registration;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.oho.oho.R;
import com.oho.oho.models.Profile;
import com.oho.oho.viewmodels.RegistrationViewModel;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationInputFragment extends Fragment {
    private RegistrationViewModel viewModel;
    private Profile profileData = new Profile();

    private TextView textLocation;
    private CardView useLocationButton;

    private String city, state = "";

    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;

    FusedLocationProviderClient fusedLocationProviderClient;

    public LocationInputFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location_input, container, false);

        textLocation = view.findViewById(R.id.text_location);
        useLocationButton = view.findViewById(R.id.button_get_location);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext());

        useLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    getCurrentLocation();
                } else {
                    //when permission denied
                    ActivityCompat.requestPermissions(requireActivity(),
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                }
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(RegistrationViewModel.class);
        viewModel.getRegistrationFormData().observe(getViewLifecycleOwner(), new Observer<Profile>() {
            @Override
            public void onChanged(Profile profile) {
                profileData = profile;
                if (profileData.getCity() != null && profileData.getState() != null){
                    String locationText = city + ", " + state;
                    textLocation.setText(locationText);
                }
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        if (!TextUtils.isEmpty(textLocation.getText())){
            profileData.setCity(city);
            profileData.setState(state);
            viewModel.saveRegistrationFormData(profileData);
        }
    }

    public void getCurrentLocation() {
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if (location != null) {
                    try {
                        Geocoder geocoder = new Geocoder(requireContext(), Locale.getDefault());
                        List<Address> addresses = geocoder.getFromLocation(
                                location.getLatitude(), location.getLongitude(), 1
                        );

                        city = addresses.get(0).getLocality();
                        state = addresses.get(0).getAdminArea();

                        String locationText = city + ", " + state;
                        textLocation.setText(locationText);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}