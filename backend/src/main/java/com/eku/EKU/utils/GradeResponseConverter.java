package com.eku.EKU.utils;

import com.eku.EKU.enums.Grade;

/**
 * AP -> A+
 * 보기 좋게 바꾸기
 */
public class GradeResponseConverter {

    public static String convert(Grade grade) {
        return switch (grade.name()) {
            case "AP" -> "A+";
            case "BP" -> "B+";
            case "CP" -> "C+";
            case "DP" -> "D+";
            default -> grade.name();
        };
    }
}
