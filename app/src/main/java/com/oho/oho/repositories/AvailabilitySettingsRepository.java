package com.oho.oho.repositories;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.oho.oho.MainActivity;
import com.oho.oho.network.APIService;
import com.oho.oho.network.RetrofitInstance;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AvailabilitySettingsRepository {


    public MutableLiveData<ArrayList<String>> updateUserAvailability(int user_id, ArrayList<String> availableTimeSlotsList, Context context){
        MutableLiveData<ArrayList<String>> selectedAvailableTimeSlots = new MutableLiveData<>();

        APIService apiService = RetrofitInstance.getRetrofitClient().create(APIService.class);
        Call<ArrayList<String>> call = apiService.addAvailability(user_id,availableTimeSlotsList);
        call.enqueue(new Callback<ArrayList<String>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<String>> call, @NonNull Response<ArrayList<String>> response) {
                Toast.makeText(context,"response code = "+response.code(),Toast.LENGTH_SHORT).show();

                selectedAvailableTimeSlots.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<String>> call, @NonNull Throwable t) {

            }
        });
        return selectedAvailableTimeSlots;
    }

    public MutableLiveData<ArrayList<String>> getUserAvailability(int user_id, Context context){
        MutableLiveData<ArrayList<String>> selectedAvailableTimeSlots = new MutableLiveData<>();

        APIService apiService = RetrofitInstance.getRetrofitClient().create(APIService.class);
        Call<ArrayList<String>> call = apiService.getAvailability(user_id);
        call.enqueue(new Callback<ArrayList<String>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<String>> call, @NonNull Response<ArrayList<String>> response) {
                Toast.makeText(context,"response code = "+response.code(),Toast.LENGTH_SHORT).show();
                selectedAvailableTimeSlots.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<String>> call, @NonNull Throwable t) {

            }
        });

        return selectedAvailableTimeSlots;
    }

}
