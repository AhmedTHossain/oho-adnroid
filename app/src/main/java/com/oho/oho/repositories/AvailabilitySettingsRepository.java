package com.oho.oho.repositories;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.oho.oho.models.Availability;
import com.oho.oho.network.APIService;
import com.oho.oho.network.RetrofitInstance;
import com.oho.oho.responses.availability.GetAvailabilityStatusResponse;
import com.oho.oho.utils.HelperClass;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AvailabilitySettingsRepository {
    private HelperClass helperClass = new HelperClass();

    public MutableLiveData<Availability> updateUserAvailability(int user_id, Availability availableTimeSlotsList, Context context) {
        MutableLiveData<Availability> selectedAvailableTimeSlots = new MutableLiveData<>();

        APIService apiService = RetrofitInstance.getRetrofitClient().create(APIService.class);
        Call<Availability> call = apiService.addAvailability(user_id, availableTimeSlotsList);
        call.enqueue(new Callback<Availability>() {
            @Override
            public void onResponse(@NonNull Call<Availability> call, @NonNull Response<Availability> response) {
                Toast.makeText(context, "We have successfully stored your availability for the weekend!", Toast.LENGTH_SHORT).show();

                selectedAvailableTimeSlots.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<Availability> call, @NonNull Throwable t) {
                Toast.makeText(context, "Failed store your availability for the weekend, Please check your connection!", Toast.LENGTH_SHORT).show();
            }
        });
        return selectedAvailableTimeSlots;
    }

    public MutableLiveData<Availability> getUserAvailability(int user_id, Context context) {
        MutableLiveData<Availability> selectedAvailableTimeSlots = new MutableLiveData<>();

        APIService apiService = RetrofitInstance.getRetrofitClient().create(APIService.class);
        Call<Availability> call = apiService.getAvailability(user_id);
        call.enqueue(new Callback<Availability>() {
            @Override
            public void onResponse(@NonNull Call<Availability> call, @NonNull Response<Availability> response) {
                Toast.makeText(context, "response code if available = " + response.code(), Toast.LENGTH_SHORT).show();
                selectedAvailableTimeSlots.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<Availability> call, @NonNull Throwable t) {
                Toast.makeText(context, "response code if failed = " + t.getCause(), Toast.LENGTH_SHORT).show();
                selectedAvailableTimeSlots.setValue(null);
            }
        });

        return selectedAvailableTimeSlots;
    }

    public MutableLiveData<Boolean> checkIfAvailable(Context context) {
        MutableLiveData<Boolean> isAvailable = new MutableLiveData<>();
        APIService apiService = RetrofitInstance.getRetrofitClient().create(APIService.class);
        Call<GetAvailabilityStatusResponse> call = apiService.ifAvailable(helperClass.getJWTToken(context));
        call.enqueue(new Callback<GetAvailabilityStatusResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetAvailabilityStatusResponse> call, @NonNull Response<GetAvailabilityStatusResponse> response) {
                if (response.body() != null) {
                    isAvailable.setValue(response.body().getData().getAvailabilityStatus());
                    Log.d("AvailabilitySettingsRepository", "availability status = " + isAvailable.getValue());
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetAvailabilityStatusResponse> call, @NonNull Throwable t) {
                isAvailable.setValue(false);
            }
        });

        return isAvailable;
    }
}
