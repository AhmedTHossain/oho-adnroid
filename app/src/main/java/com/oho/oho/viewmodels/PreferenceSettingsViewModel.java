package com.oho.oho.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.oho.oho.repositories.PreferenceSettingsRepository;
import com.oho.oho.responses.preference.PreferenceResponse;

public class PreferenceSettingsViewModel extends AndroidViewModel {

    private PreferenceSettingsRepository repository;
    public LiveData<PreferenceResponse> preferenceResponse;
    public LiveData<String> userLocation;
    public LiveData<Boolean> ifPreferenceUpdated;

    public LiveData<Boolean> ifUploaded;
    public MutableLiveData<Boolean> isInputSelected = new MutableLiveData<>();

    public String selectedInputFor, selectedInput;

    public PreferenceSettingsViewModel(@NonNull Application application) {
        super(application);
        repository = new PreferenceSettingsRepository(getApplication().getApplicationContext());
    }

    public void getProfilePreference() {
        preferenceResponse = repository.getUserPreference();
    }

    public void getStoredUserLocation(int user_id) {
        userLocation = repository.getUserLocation(user_id);
    }

    public void updatePreference(PreferenceResponse preferences) {
        ifPreferenceUpdated = repository.updatePreference(preferences);
    }

    public void setIsInputSelected(boolean isInputSelected, String selectedInputFor, String selectedInput) {
        if (selectedInputFor.equals("gender")) {
            this.isInputSelected.setValue(isInputSelected);
            this.selectedInputFor = selectedInputFor;
            this.selectedInput = selectedInput;
        }
    }
}
