package com.oho.oho.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.oho.oho.models.Prompt;
import com.oho.oho.repositories.PromptRepository;

import java.util.List;

public class PromptViewModel extends AndroidViewModel {

    private PromptRepository promptRepository;
    public LiveData<List<Prompt>> promptList;

    public PromptViewModel(@NonNull Application application) {
        super(application);
        promptRepository = new PromptRepository();
    }

    public void getAllPromptList(){
        promptList = promptRepository.getPromptList();
    }
}
