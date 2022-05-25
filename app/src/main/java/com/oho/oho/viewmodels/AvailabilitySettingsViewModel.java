package com.oho.oho.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.oho.oho.repositories.AvailabilitySettingsRepository;

import java.util.ArrayList;

public class AvailabilitySettingsViewModel extends AndroidViewModel {

    private AvailabilitySettingsRepository availabilitySettingsRepository;
    public LiveData<ArrayList<String>> selectedTimeSlotsList;

    public AvailabilitySettingsViewModel(@NonNull Application application){
        super(application);
        availabilitySettingsRepository = new AvailabilitySettingsRepository();
    }

    public void addAvailableTimeSlots(int user_id,ArrayList<String> availableTimeSlotsList){
        selectedTimeSlotsList = availabilitySettingsRepository.updateUserAvailability(user_id, availableTimeSlotsList, getApplication().getApplicationContext());
    }

    public void getAvailableTimeSlots(int user_id){
        selectedTimeSlotsList = availabilitySettingsRepository.getUserAvailability(user_id, getApplication().getApplicationContext());
    }
}
