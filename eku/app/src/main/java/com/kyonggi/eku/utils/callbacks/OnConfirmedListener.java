package com.kyonggi.eku.utils.callbacks;

import com.kyonggi.eku.model.SignUpForm;

public interface OnConfirmedListener {
    void onConfirmed(SignUpForm form);
    void onSignUpEnd();
}
