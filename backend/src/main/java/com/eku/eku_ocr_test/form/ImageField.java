package com.eku.eku_ocr_test.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ocr 리퀘스트에 포함되는 json object
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageField {
    private String name;
    private String valueType;
    private String inferText;
    private float inferConfidence;
    private BoundingPoly boundingPoly;
    private String type;
    private boolean lineBreak;
}
