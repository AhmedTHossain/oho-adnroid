package com.oho.oho.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.oho.oho.models.CreateDeviceId;
import com.oho.oho.repositories.MainRepository;
import com.oho.oho.responses.StoreDeviceIdResponse;

public class MainViewModel extends AndroidViewModel {
    public LiveData<StoreDeviceIdResponse> storedIdResponse;
    private MainRepository repository;

    public MainViewModel(@NonNull Application application) {
        super(application);
        repository = new MainRepository();
    }

    public void storeDeviceId(CreateDeviceId createDeviceId) {
        storedIdResponse = repository.storeDeviceId(createDeviceId, getApplication().getApplicationContext());
    }

    public void updateDeviceId(CreateDeviceId createDeviceId) {
        storedIdResponse = repository.updateDeviceId(createDeviceId, getApplication().getApplicationContext());
    }
}
