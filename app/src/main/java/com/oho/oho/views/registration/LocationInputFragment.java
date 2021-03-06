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
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.oho.oho.R;
import com.oho.oho.models.Profile;
import com.oho.oho.viewmodels.RegistrationViewModel;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationInputFragment extends Fragment implements OnMapReadyCallback {
    private RegistrationViewModel viewModel;
    private Profile profileData = new Profile();

    private TextView textLocation;
    private CardView useLocationButton;
    private MapView mapView;

    private GoogleMap googleMap;

    private String city, state;
    private double lat, lon;

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
        mapView = view.findViewById(R.id.map_view);

        mapView.getMapAsync(this);
        mapView.onCreate(savedInstanceState);

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
                    city = profileData.getCity();
                    state = profileData.getState();
                    lat = profile.getLat();
                    lon = profile.getLon();
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
            profileData.setLat(lat);
            profileData.setLon(lon);
            viewModel.saveRegistrationFormData(profileData);
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }


    public void getCurrentLocation() {
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if (location != null) {

                    lat = location.getLatitude();
                    lon = location.getLongitude();

                    try {
                        Geocoder geocoder = new Geocoder(requireContext(), Locale.getDefault());
                        List<Address> addresses = geocoder.getFromLocation(
                                location.getLatitude(), location.getLongitude(), 1
                        );

                        city = addresses.get(0).getLocality();
                        state = addresses.get(0).getAdminArea();

                        String locationText = city + ", " + state;
                        textLocation.setText(locationText);

                        LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());

                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.title("My location");
                        markerOptions.position(latLng);


                        googleMap.addMarker(markerOptions);
                        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng,15);
                        googleMap.animateCamera(cameraUpdate);

                        useLocationButton.setVisibility(View.GONE);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}