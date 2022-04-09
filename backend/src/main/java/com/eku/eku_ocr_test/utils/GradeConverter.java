package com.eku.eku_ocr_test.utils;

import com.eku.eku_ocr_test.domain.Grade;

import javax.persistence.AttributeConverter;

/**
 * Grade를 DB에 저장하거나 DB에서 읽어올 때 매핑해주는 클래스
 * 예) DB에는 4.5라고 저장 돼 있으면 이를 자바 Enum 클래스인 Grade의 AP로 매핑시켜줌
 */
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
