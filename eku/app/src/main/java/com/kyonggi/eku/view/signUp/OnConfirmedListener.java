package com.kyonggi.eku.view.signUp;

import com.kyonggi.eku.model.SignUpForm;

public interface OnConfirmedListener {
    void onConfirmed(SignUpForm form);
    void onSignUpEnd();
}
