package com.eku.eku_ocr_test.callbacks;

import com.eku.eku_ocr_test.form.OcrResponseForm;

import java.io.IOException;

public interface OcrCallBack {

    void onSuccess(OcrResponseForm ocrResponseForm) throws IOException;
    void onFailed() throws IOException;
}
