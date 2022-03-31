package com.eku.eku_ocr_test.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoundingPoly {
    private ArrayList<Vertices> vertices;
}
