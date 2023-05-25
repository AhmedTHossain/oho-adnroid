package com.oho.oho.interfaces;

import com.github.ybq.android.spinkit.SpinKitView;

public interface OnPromptAnswerListener {
    void onPromptAnswer(String prompt, String answer, String caption, SpinKitView progressview);
}
