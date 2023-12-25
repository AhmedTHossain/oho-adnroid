package com.oho.oho.repositories;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.oho.oho.MainActivity;
import com.oho.oho.models.Availability;
import com.oho.oho.network.APIService;
import com.oho.oho.network.RetrofitInstance;
import com.oho.oho.responses.CheckAvailabilityResponse;
import com.oho.oho.utils.HelperClass;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AvailabilitySettingsRepository {
    private HelperClass helperClass = new HelperClass();
    public MutableLiveData<Availability> updateUserAvailability(int user_id, Availability availableTimeSlotsList, Context context){
        MutableLiveData<Availability> selectedAvailableTimeSlots = new MutableLiveData<>();

        APIService apiService = RetrofitInstance.getRetrofitClient().create(APIService.class);
        Call<Availability> call = apiService.addAvailability(user_id,availableTimeSlotsList);
        call.enqueue(new Callback<Availability>() {
            @Override
            public void onResponse(@NonNull Call<Availability> call, @NonNull Response<Availability> response) {
                Toast.makeText(context,"We have successfully stored your availability for the weekend!",Toast.LENGTH_SHORT).show();

                selectedAvailableTimeSlots.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<Availability> call, @NonNull Throwable t) {
                Toast.makeText(context,"Failed store your availability for the weekend, Please check your connection!",Toast.LENGTH_SHORT).show();
            }
        });
        return selectedAvailableTimeSlots;
    }

    public MutableLiveData<Availability> getUserAvailability(int user_id, Context context){
        MutableLiveData<Availability> selectedAvailableTimeSlots = new MutableLiveData<>();

        APIService apiService = RetrofitInstance.getRetrofitClient().create(APIService.class);
        Call<Availability> call = apiService.getAvailability(user_id);
        call.enqueue(new Callback<Availability>() {
            @Override
            public void onResponse(@NonNull Call<Availability> call, @NonNull Response<Availability> response) {
                Toast.makeText(context,"response code if available = "+response.code(),Toast.LENGTH_SHORT).show();
                selectedAvailableTimeSlots.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<Availability> call, @NonNull Throwable t) {
                Toast.makeText(context,"response code if failed = "+t.getCause(),Toast.LENGTH_SHORT).show();
                selectedAvailableTimeSlots.setValue(null);
            }
        });

        return selectedAvailableTimeSlots;
    }

    public MutableLiveData<Boolean> checkIfAvailable(Context context){
        MutableLiveData<Boolean> isAvailable = new MutableLiveData<>();
        APIService apiService = RetrofitInstance.getRetrofitClient().create(APIService.class);
        Call<JSONObject> call = apiService.ifAvailable(helperClass.getJWTToken(context));
        call.enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(@NonNull Call<JSONObject> call, @NonNull Response<JSONObject> response) {
                if (response.body()!=null){
//                    try {
//                        JSONObject responseObject = response.body();
//                        JSONObject dataObject = responseObject.getJSONObject("data");
//                        Boolean status = dataObject.getBoolean("availability_status");
//                        Log.d("AvailabilitySettingsRepository","status: "+status);
//                        isAvailable.setValue(status);
//                    } catch (JSONException e){
//                        e.printStackTrace();
//                        Log.d("AvailabilitySettingsRepository","status: "+e);
//                    }
                    try {
                        JSONObject responseObject = response.body();

                        // Check if the "data" field exists
                        if (responseObject.has("data")) {
                            // Extract availability status from the "data" object
                            JSONObject dataObject = responseObject.getJSONObject("data");

                            if (dataObject.has("availability_status")) {
                                Boolean availabilityStatus = dataObject.getBoolean("availability_status");
                                Log.d("AvailabilitySettingsRepository", "Availability status: " + availabilityStatus);

                                // Update the MutableLiveData with the availability status
                                isAvailable.setValue(availabilityStatus);
                            } else {
                                Log.d("AvailabilitySettingsRepository", "No value for 'availability_status' in 'data'");
                            }
                        } else {
                            Log.d("AvailabilitySettingsRepository", "No value for 'data' in the response");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d("AvailabilitySettingsRepository", "Error parsing JSON: " + e);
                    }
                    Log.d("AvailabilitySettingsRepository","availability status = "+isAvailable.getValue());
                }
            }

            @Override
            public void onFailure(@NonNull Call<JSONObject> call, @NonNull Throwable t) {
                isAvailable.setValue(false);
            }
        });

        return isAvailable;
    }
}
