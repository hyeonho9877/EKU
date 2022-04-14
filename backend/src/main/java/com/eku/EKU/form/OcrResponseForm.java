package com.eku.EKU.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OcrResponseForm {
    private String version;
    private String requestId;
    private long timestamp;
    private ArrayList<ImageResponse> images;
}
