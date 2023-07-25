package com.oho.oho.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.oho.oho.models.NewPromptAnswer;
import com.oho.oho.models.SelectedPrompt;
import com.oho.oho.repositories.ProfileSetupRepository;

public class AddPromptViewModel extends AndroidViewModel {

    private MutableLiveData<Boolean> onPromptSelect = new MutableLiveData<>();
    private MutableLiveData<SelectedPrompt> selectedPrompt = new MutableLiveData<>();
    public LiveData<Boolean> ifUploaded;
    private ProfileSetupRepository repository;

    public AddPromptViewModel(@NonNull Application application) {
        super(application);
        repository = new ProfileSetupRepository(getApplication().getApplicationContext());
    }

    public void setOnPromptSelect(SelectedPrompt prompt) {
        onPromptSelect.setValue(true);
        selectedPrompt.setValue(prompt);
    }

    public LiveData<SelectedPrompt> getSelectedPrompt() {
        return selectedPrompt;
    }

    public LiveData<Boolean> getOnPromptSelect() {
        return onPromptSelect;
    }

    public void uploadPromptAnswer(NewPromptAnswer newPromptAnswer) {
        ifUploaded = repository.uploadNewPromptAnswer(newPromptAnswer, getApplication().getApplicationContext());
    }
}
