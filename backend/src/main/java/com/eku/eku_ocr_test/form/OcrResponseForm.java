package com.eku.eku_ocr_test.form;

import lombok.Data;

import java.util.HashMap;

@Data
public class OcrResponseForm {
    private String uid;
    private String name;
    private String inferResult;
    private String message;
    private ImageField fields;
    private HashMap<String, String> validationResult;
}
