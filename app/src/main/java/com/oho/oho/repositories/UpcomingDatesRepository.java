package com.oho.oho.repositories;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.oho.oho.network.APIService;
import com.oho.oho.network.RetrofitInstance;
import com.oho.oho.responses.upcomingdates.GetUpcomingDatesResponse;
import com.oho.oho.utils.HelperClass;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpcomingDatesRepository {
    private Context context;
    private HelperClass helperClass = new HelperClass();

    public UpcomingDatesRepository(Context context) {
        this.context = context;
    }

    public MutableLiveData<GetUpcomingDatesResponse> getUpcomingDates(){
        MutableLiveData<GetUpcomingDatesResponse> upcomingDatesData = new MutableLiveData<>();
        APIService service = RetrofitInstance.getRetrofitClient().create(APIService.class);
        Call<GetUpcomingDatesResponse> call = service.getUpcomingDates(helperClass.getJWTToken(context));
        call.enqueue(new Callback<GetUpcomingDatesResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetUpcomingDatesResponse> call, @NonNull Response<GetUpcomingDatesResponse> response) {
                if (response.isSuccessful()){
                    upcomingDatesData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetUpcomingDatesResponse> call, Throwable t) {
                upcomingDatesData.setValue(null);
            }
        });
        return upcomingDatesData;
    }
}
