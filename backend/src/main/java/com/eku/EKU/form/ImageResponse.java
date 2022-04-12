package com.eku.EKU.form;

import lombok.Data;

import java.util.ArrayList;

@Data
public class ImageResponse {
    private String uid;
    private String name;
    private String inferResult;
    private String message;
    private String title;
    private ArrayList<ImageField> fields;
    private ValidationResult validationResult;
}
