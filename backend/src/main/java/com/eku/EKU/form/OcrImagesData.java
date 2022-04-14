package com.eku.EKU.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OcrImagesData {
    private String format;
    private String name;

    @Override
    public String toString(){
        return String.format("{\"format\":\"%s\", \"name\":\"%s\"}", format, name);
    }
}
