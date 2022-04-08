package com.eku.eku_ocr_test.utils;

import com.eku.eku_ocr_test.domain.Grade;

import javax.persistence.AttributeConverter;

public class GradeConverter implements AttributeConverter<Grade, Double> {

    @Override
    public Double convertToDatabaseColumn(Grade attribute) {
        return attribute.getScore();
    }

    @Override
    public Grade convertToEntityAttribute(Double dbData) {
        if (dbData == 4.5) {
            return Grade.AP;
        } else if (dbData == 4.0) {
            return Grade.A;
        } else if (dbData == 3.5) {
            return Grade.BP;
        } else if (dbData == 3.0) {
            return Grade.B;
        } else if (dbData == 2.5) {
            return Grade.CP;
        } else if (dbData == 2.0) {
            return Grade.C;
        } else if (dbData == 1.5) {
            return Grade.DP;
        } else if (dbData == 1.0) {
            return Grade.D;
        } else if (dbData == 0.0) {
            return Grade.F;
        } else return null;
    }
}
