package com.kyonggi.eku.utils.callbacks;

import com.kyonggi.eku.model.BoardPreview;

import java.util.List;

public interface OnResponseListeners {
    void onSuccess(List<? extends BoardPreview> articles, String purpose);
    void onFailed();
}
