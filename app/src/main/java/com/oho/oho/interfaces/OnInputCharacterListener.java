package com.oho.oho.interfaces;

import android.view.View;
import android.widget.TextView;

public interface OnInputCharacterListener {
    void onInputCharacter(View view, int currentCount, TextView charCountText, String inputField);
}
