package com.kyonggi.eku.utils.callbacks;

import com.kyonggi.eku.model.SignUpForm;

public interface OnSignUpConfirmedListener {
    void onConfirmed(SignUpForm form);
    void onSignUpEnd();
}
