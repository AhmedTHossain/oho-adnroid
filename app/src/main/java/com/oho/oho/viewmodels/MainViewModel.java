package com.oho.oho.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.oho.oho.R;
import com.oho.oho.models.CreateDeviceId;
import com.oho.oho.models.JWTTokenRequest;
import com.oho.oho.repositories.ChatRepository;
import com.oho.oho.repositories.MainRepository;
import com.oho.oho.responses.notification.StoreDeviceIdResponse;

public class MainViewModel extends AndroidViewModel {
    public LiveData<StoreDeviceIdResponse> storedIdResponse;
    private MainRepository repository;
    private ChatRepository chatRepository;
    public LiveData<String> jwtToken;

    public MainViewModel(@NonNull Application application) {
        super(application);
        repository = new MainRepository();
        chatRepository = new ChatRepository(getApplication().getApplicationContext());
    }

    public void storeDeviceId(CreateDeviceId createDeviceId) {
        storedIdResponse = repository.storeDeviceId(createDeviceId, getApplication().getApplicationContext());
    }

    public void updateDeviceId(CreateDeviceId createDeviceId) {
        storedIdResponse = repository.updateDeviceId(createDeviceId, getApplication().getApplicationContext());
    }

    public void replaceFragment(Fragment fragment, FragmentManager supportFragmentManager) {
        FragmentManager fragmentManager = supportFragmentManager;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    public void getJWTToken(JWTTokenRequest jwtTokenRequest) {
        jwtToken = chatRepository.getJwtToken(jwtTokenRequest);
    }
}
