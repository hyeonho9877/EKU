package com.eku.EKU.utils;

import com.eku.EKU.enums.Grade;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Grade를 DB에 저장하거나 DB에서 읽어올 때 매핑해주는 클래스
 * 예) DB에는 4.5라고 저장 돼 있으면 이를 자바 Enum 클래스인 Grade의 AP로 매핑시켜줌
 */
@Converter
public class GradeConverter implements AttributeConverter<Grade, Double> {

    @Override
    public Double convertToDatabaseColumn(Grade attribute) {
        return attribute.getScore();
    }

    @Override
    public Grade convertToEntityAttribute(Double dbData) {
        return switch(String.valueOf(dbData)){
            case "4.5" -> Grade.AP;
            case "4.0" -> Grade.A;
            case "3.5"-> Grade.BP;
            case "3.0" -> Grade.B;
            case "2.5" -> Grade.CP;
            case "2.0" -> Grade.C;
            case "1.5" -> Grade.DP;
            case "1.0" -> Grade.D;
            case "0.0" -> Grade.F;
            default -> null;
        };
    }
}
