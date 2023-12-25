package com.oho.oho.repositories;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.oho.oho.models.CreateDeviceId;
import com.oho.oho.network.APIService;
import com.oho.oho.network.RetrofitInstance;
import com.oho.oho.responses.StoreDeviceIdResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainRepository {

    public MutableLiveData<StoreDeviceIdResponse> storeDeviceId(CreateDeviceId createDeviceId, Context context){
        MutableLiveData<StoreDeviceIdResponse> storedIdResponse = new MutableLiveData<>();

        APIService service = RetrofitInstance.getRetrofitClient().create(APIService.class);
        Call<StoreDeviceIdResponse> call = service.storeDeviceId(createDeviceId);

        call.enqueue(new Callback<StoreDeviceIdResponse>() {
            @Override
            public void onResponse(@NonNull Call<StoreDeviceIdResponse> call, @NonNull Response<StoreDeviceIdResponse> response) {
                if (response.isSuccessful()){
                    storedIdResponse.setValue(response.body());
                    Toast.makeText(context,"Device ID stored successfully!",Toast.LENGTH_SHORT).show();
                } else {
                    storedIdResponse.setValue(null);
                    Toast.makeText(context,"Device ID already created!",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<StoreDeviceIdResponse> call, @NonNull Throwable t) {
                storedIdResponse.setValue(null);
                Toast.makeText(context,"Failed to store Device ID!",Toast.LENGTH_SHORT).show();
            }
        });
        return storedIdResponse;
    }

    public MutableLiveData<StoreDeviceIdResponse> updateDeviceId(CreateDeviceId createDeviceId, Context context){
        MutableLiveData<StoreDeviceIdResponse> storedIdResponse = new MutableLiveData<>();

        APIService service = RetrofitInstance.getRetrofitClient().create(APIService.class);
        Call<StoreDeviceIdResponse> call = service.updateDeviceId(createDeviceId);

        call.enqueue(new Callback<StoreDeviceIdResponse>() {
            @Override
            public void onResponse(@NonNull Call<StoreDeviceIdResponse> call, @NonNull Response<StoreDeviceIdResponse> response) {
                if (response.isSuccessful()){
                    storedIdResponse.setValue(response.body());
                    Toast.makeText(context,"Device ID updated successfully!",Toast.LENGTH_SHORT).show();
                } else {
                    storedIdResponse.setValue(null);
                    Toast.makeText(context,"Device ID update failed!",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<StoreDeviceIdResponse> call, @NonNull Throwable t) {
                storedIdResponse.setValue(null);
                Toast.makeText(context,"Failed to update Device ID!",Toast.LENGTH_SHORT).show();
            }
        });
        return storedIdResponse;
    }

}
