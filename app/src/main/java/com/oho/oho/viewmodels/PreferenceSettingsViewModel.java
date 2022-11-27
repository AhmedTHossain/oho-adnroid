package com.oho.oho.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.oho.oho.repositories.PreferenceSettingsRepository;
import com.oho.oho.responses.PreferenceResponse;

public class PreferenceSettingsViewModel extends AndroidViewModel {

    private PreferenceSettingsRepository repository;
    public LiveData<PreferenceResponse> preferenceResponse;

    public LiveData<Boolean> ifUploaded;

    public PreferenceSettingsViewModel(@NonNull Application application){
        super(application);
        repository = new PreferenceSettingsRepository(getApplication().getApplicationContext());
    }

    public void getProfilePreference(String user_id){
        preferenceResponse = repository.getUserPreference(user_id);
    }
}
