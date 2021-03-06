package com.oho.oho.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.oho.oho.models.Profile;
import com.oho.oho.models.RegistrationInput;
import com.oho.oho.repositories.RegistrationRepository;

import java.util.ArrayList;

public class RegistrationViewModel extends AndroidViewModel {

    private RegistrationRepository registrationRepository;
    public LiveData<Profile> registeredUserProfileData;

    //this object is going to store user's inputs during the registration process
    private MutableLiveData<Profile> registrationFormData = new MutableLiveData<>();

    //this object is going to store user's ethnicity input during the registration process and will populate the recyclerview
    private MutableLiveData<ArrayList<RegistrationInput>> ethnicityFormData = new MutableLiveData<>();

    //this object is going to store user's cuisine input during the registration process and will populate the recyclerview
    private MutableLiveData<ArrayList<RegistrationInput>> cuisineFormData = new MutableLiveData<>();

    //this object is going to store user's religion input during the registration process and will populate the recyclerview
    private MutableLiveData<ArrayList<RegistrationInput>> religionFormData = new MutableLiveData<>();

    public RegistrationViewModel(@NonNull Application application) {
        super(application);
        registrationRepository = new RegistrationRepository();
    }

    public void registerUser(Profile profile){
        registeredUserProfileData = registrationRepository.registerNewUser(profile, getApplication().getApplicationContext());
    }

    public void saveRegistrationFormData(Profile profile) {
        registrationFormData.setValue(profile);
    }

    public LiveData<Profile> getRegistrationFormData() {
        return registrationFormData;
    }

    public void setEthnicityList(ArrayList<RegistrationInput> ethnicityList){
        ethnicityFormData.setValue(ethnicityList);
    }

    public void setCuisineList(ArrayList<RegistrationInput> cuisineList){
        cuisineFormData.setValue(cuisineList);
    }

    public void setReligionList(ArrayList<RegistrationInput> religionList){
        religionFormData.setValue(religionList);
    }

    public LiveData<ArrayList<RegistrationInput>> getEthnicityList(){
        return ethnicityFormData;
    }

    public LiveData<ArrayList<RegistrationInput>> getCuisineList(){
        return cuisineFormData;
    }

    public LiveData<ArrayList<RegistrationInput>> getReligionList(){
        return religionFormData;
    }
}
