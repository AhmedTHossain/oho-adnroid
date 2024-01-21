package com.oho.oho.utils;

import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.text.TextUtils;
import android.widget.EditText;

public class PhoneNumberFormatter {

    public static void formatPhoneNumber(final EditText editText) {

        // Set the maximum length of the phone number with hyphens
        final int maxLength = 12;
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {
                // Do nothing before text changes
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                // Do nothing on text changing
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String input = editable.toString();

                // Remove hyphens from the previous input
                input = input.replace("-", "");

                // Add hyphens based on the phone number format "XXX-XXX-XXXX"
                if (input.length() >= 3 && input.length() < 6) {
                    input = input.substring(0, 3) + "-" + input.substring(3);
                } else if (input.length() >= 6 && input.length() < 10) {
                    input = input.substring(0, 3) + "-" + input.substring(3, 6) + "-" + input.substring(6);
                } else if (input.length() == 10) {
                    input = input.substring(0, 3) + "-" + input.substring(3, 6) + "-" + input.substring(6);
                }

                // Update the EditText with the formatted phone number
                editText.removeTextChangedListener(this);
                editText.setText(input);
                editText.setSelection(input.length());
                editText.addTextChangedListener(this);
            }
        });
    }
}
