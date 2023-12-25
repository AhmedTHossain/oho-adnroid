package com.oho.oho.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.oho.oho.R;
import com.oho.oho.models.CreateDeviceId;
import com.oho.oho.models.JWTTokenRequest;
import com.oho.oho.repositories.ChatRepository;
import com.oho.oho.repositories.MainRepository;
import com.oho.oho.responses.StoreDeviceIdResponse;
import com.oho.oho.utils.HelperClass;

public class MainViewModel extends AndroidViewModel {
    public LiveData<StoreDeviceIdResponse> storedIdResponse;
    private MainRepository repository;
    private ChatRepository chatRepository;
    public LiveData<String> jwtToken;

    public MainViewModel(@NonNull Application application) {
        super(application);
        repository = new MainRepository();
        chatRepository = new ChatRepository(getApplication().getApplicationContext());
    }

    public void storeDeviceId(CreateDeviceId createDeviceId) {
        storedIdResponse = repository.storeDeviceId(createDeviceId, getApplication().getApplicationContext());
    }

    public void updateDeviceId(CreateDeviceId createDeviceId) {
        storedIdResponse = repository.updateDeviceId(createDeviceId, getApplication().getApplicationContext());
    }

    public void replaceFragment(Fragment fragment, FragmentManager supportFragmentManager) {
        FragmentManager fragmentManager = supportFragmentManager;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    public void getFCMToken() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("MainViewModel", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        HelperClass helperClass = new HelperClass();

//                        storeDeviceToken(token, helperClass.getProfile(MainActivity.this).getId(), "android"); //TODO: later replace the hard coded user_id with logged in user's id

                        // Log and toast
                        String msg = "FCM token of this device: " + token;
                        Log.d("MainViewModel", "fcm token = "+msg);
                    }
                });
    }

    public void getJWTToken(JWTTokenRequest jwtTokenRequest){
        jwtToken = chatRepository.getJwtToken(jwtTokenRequest);
    }

//    private void storeDeviceToken(String token, int user_id, String device_type) {
//        CreateDeviceId createDeviceId = new CreateDeviceId();
//        createDeviceId.setToken(token);
//        createDeviceId.setUserId(user_id);
//        createDeviceId.setDeviceType(device_type);
//
//        viewModel.storeDeviceId(createDeviceId);
//        viewModel.storedIdResponse.observe(this, storedIdResponse -> {
//            if (storedIdResponse != null)
//                Toast.makeText(this, "Device ID found in response!!", Toast.LENGTH_SHORT).show();
//            else {
////                Toast.makeText(this, "No Device ID found in response!!", Toast.LENGTH_SHORT).show();
//                viewModel.updateDeviceId(createDeviceId);
//                viewModel.storedIdResponse.observe(this, updatedIdResponse -> {
//                    if (updatedIdResponse != null)
//                        Toast.makeText(this, "Device ID found in response!!", Toast.LENGTH_SHORT).show();
//                    else
//                        Toast.makeText(this, "No Device ID found in response!!", Toast.LENGTH_SHORT).show();
//                });
//            }
//        });
//    }
}
