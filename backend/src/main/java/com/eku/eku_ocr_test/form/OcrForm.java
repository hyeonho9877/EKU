package com.eku.eku_ocr_test.form;

import lombok.Data;

@Data
public class OcrForm {
    private String format;
    private byte[] data;
    private String name;
}
