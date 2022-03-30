package com.eku.eku_ocr_test.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageField {
    private String name;
    private String valueType;
    private String inferText;
    private float inferConfidence;
    private Bounding bounding;
}
