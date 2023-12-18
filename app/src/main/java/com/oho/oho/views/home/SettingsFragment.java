package com.oho.oho.views.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oho.oho.R;
import com.oho.oho.databinding.FragmentSettingsBinding;
import com.oho.oho.models.Profile;
import com.oho.oho.utils.HelperClass;
import com.oho.oho.views.settings.AccountSettingsActivity;
import com.oho.oho.views.settings.AvailabilitySettingsActivity;
import com.oho.oho.views.settings.FAQActivity;
import com.oho.oho.views.LoginActivity;
import com.oho.oho.views.settings.PreferenceSettingsActivity;
import com.oho.oho.views.settings.PrivacyPolicyActivity;
import com.oho.oho.views.settings.SafeDatingTipsActivity;
import com.oho.oho.views.settings.TermsOfUseActivity;
import com.oho.oho.views.settings.UpcomingDatesActivity;

public class SettingsFragment extends Fragment implements View.OnClickListener {

    FragmentSettingsBinding binding;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentSettingsBinding.inflate(inflater,container,false);

        binding.buttonAccountSettings.setOnClickListener(this);
        binding.buttonPreferenceSettings.setOnClickListener(this);
        binding.buttonFaqSettings.setOnClickListener(this);
        binding.buttonAvailabilitySettings.setOnClickListener(this);
        binding.buttonHelpcenterSettings.setOnClickListener(this);
        binding.buttonTermsSettings.setOnClickListener(this);
        binding.buttonPrivacySettings.setOnClickListener(this);
        binding.buttonSignoutSettings.setOnClickListener(this);
        binding.buttonUpcomingDates.setOnClickListener(this);
        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_account_settings:
                startActivity(new Intent(requireActivity(), AccountSettingsActivity.class));
                break;
            case R.id.button_preference_settings:
                startActivity(new Intent(requireActivity(), PreferenceSettingsActivity.class));
                break;
            case R.id.button_faq_settings:
                startActivity(new Intent(requireActivity(), FAQActivity.class));
                break;
            case R.id.button_availability_settings:
                startActivity(new Intent(requireActivity(), AvailabilitySettingsActivity.class));
                break;
            case R.id.button_helpcenter_settings:
                startActivity(new Intent(requireActivity(), SafeDatingTipsActivity.class));
                break;
            case R.id.button_terms_settings:
                startActivity(new Intent(requireActivity(), TermsOfUseActivity.class));
                break;
            case R.id.button_privacy_settings:
                startActivity(new Intent(requireActivity(), PrivacyPolicyActivity.class));
                break;
            case R.id.button_signout_settings:
                HelperClass helperClass = new HelperClass();
                Profile profile = null;

                helperClass.saveProfile(requireContext(),profile);
                startActivity(new Intent(requireActivity(), LoginActivity.class));
                requireActivity().finish();
                break;
            case R.id.button_upcoming_dates:
                startActivity(new Intent(requireActivity(), UpcomingDatesActivity.class));
                break;
        }
    }
}