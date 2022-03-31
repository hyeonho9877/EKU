package com.eku.eku_ocr_test.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientOcrRequestForm {
    private String version="V2";
    private String requestId;
    private int timestamp=0;
    private String lang = "ko";
    private List<OcrImagesData> images;
}
