package com.eku.eku_ocr_test.utils;

import com.eku.eku_ocr_test.domain.Grade;

/**
 * AP -> A+
 * 보기 좋게 바꾸기
 */
public class GradeResponseConverter {

    public static String convert(Grade grade) {
        switch (grade.name()) {
            case "AP":
                return "A+";
            case "BP":
                return "B+";
            case "CP":
                return "C+";
            case "DP":
                return "D+";
            default:
                return grade.name();
        }
    }
}
