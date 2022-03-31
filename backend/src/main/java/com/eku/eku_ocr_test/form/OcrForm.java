package com.eku.eku_ocr_test.form;

import lombok.Data;

import java.util.ArrayList;

@Data
public class OcrForm {
    private String version="V2";
    private String requestId;
    private int timestamp;
    private String lang="ko";
    private ArrayList<OcrImagesData> images;
}
