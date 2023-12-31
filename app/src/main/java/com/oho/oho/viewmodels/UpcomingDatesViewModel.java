package com.oho.oho.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.oho.oho.repositories.UpcomingDatesRepository;
import com.oho.oho.responses.upcomingdates.GetUpcomingDatesResponse;

public class UpcomingDatesViewModel extends AndroidViewModel {
    private final UpcomingDatesRepository repository;
    public LiveData<GetUpcomingDatesResponse> upcomingDatesData;
    public UpcomingDatesViewModel(@NonNull Application application){
        super(application);
        repository = new UpcomingDatesRepository(getApplication().getApplicationContext());
    }
    public void getUpcomingDates(){
        upcomingDatesData = repository.getUpcomingDates();
    }
}
