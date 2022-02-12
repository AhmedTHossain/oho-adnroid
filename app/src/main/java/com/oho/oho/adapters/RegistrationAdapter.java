package com.oho.oho.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.oho.oho.views.registration.CompleteFragment;
import com.oho.oho.views.registration.CuisinePreferenceInputFragment;
import com.oho.oho.views.registration.DobInputFragment;
import com.oho.oho.views.registration.EducationInputFragment;
import com.oho.oho.views.registration.EthnicityInputFragment;
import com.oho.oho.views.registration.FinishFragment;
import com.oho.oho.views.registration.GenderInputFragment;
import com.oho.oho.views.registration.GenderPreferenceInputFragment;
import com.oho.oho.views.registration.HeightInputFragment;
import com.oho.oho.views.registration.IntroFragment;
import com.oho.oho.views.registration.LocationInputFragment;
import com.oho.oho.views.registration.PhoneInputFragment;
import com.oho.oho.views.registration.ProfessionInputFragment;
import com.oho.oho.views.registration.ReligionInputFragment;
import com.oho.oho.views.registration.VaccineInputFragment;

public class RegistrationAdapter extends FragmentStateAdapter {
    private String onBoardingUserName;
    public RegistrationAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, String onBoardingUserName) {
        super(fragmentManager, lifecycle);
        this.onBoardingUserName = onBoardingUserName;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new IntroFragment(onBoardingUserName);
            case 1:
                return new PhoneInputFragment();
            case 2:
                return new GenderInputFragment();
            case 3:
                return new HeightInputFragment();
            case 4:
                return new DobInputFragment();
            case 5:
                return new LocationInputFragment();
            case 6:
                return new EducationInputFragment();
            case 7:
                return new ProfessionInputFragment();
            case 8:
                return new EthnicityInputFragment();
            case 9:
                return new ReligionInputFragment();
            case 10:
                return new VaccineInputFragment();
            case 11:
                return new CompleteFragment();
            case 12:
                return new GenderPreferenceInputFragment();
            case 13:
                return new CuisinePreferenceInputFragment();
            default:
                return new FinishFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 15;
    }
}
