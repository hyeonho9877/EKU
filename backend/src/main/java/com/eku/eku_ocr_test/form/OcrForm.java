package com.eku.eku_ocr_test.form;

import lombok.Data;

@Data
public class OcrForm {
    private String format;
    private String name;

    @Override
    public String toString(){
        return String.format("{\"format\":\"%s\", \"name\":\"%s\"}", format, name);
    }
}
