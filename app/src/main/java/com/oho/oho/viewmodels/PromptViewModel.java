package com.oho.oho.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.oho.oho.models.Prompt;
import com.oho.oho.models.PromptAnswer;
import com.oho.oho.repositories.PromptRepository;

import java.io.File;
import java.util.List;

public class PromptViewModel extends AndroidViewModel {

    private PromptRepository promptRepository;
    public LiveData<List<Prompt>> promptList;

    public LiveData<Boolean> ifUploaded;

    public PromptViewModel(@NonNull Application application) {
        super(application);
        promptRepository = new PromptRepository(getApplication().getApplicationContext());
    }

    public void getAllPromptList(){
        promptList = promptRepository.getPromptList();
    }

    public void uploadPromptAnswer(String prompt, String answer, int user_id, String caption, File image){
        ifUploaded = promptRepository.uploadUserPromptAnswer(prompt,answer,user_id,caption,image,getApplication().getApplicationContext());
    }
}
