package com.eku.EKU.utils;

import com.eku.EKU.enums.LectureType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class LectureTypeConverter implements AttributeConverter<LectureType, String> {
    @Override
    public String convertToDatabaseColumn(LectureType attribute) {
        return attribute.getType();
    }

    @Override
    public LectureType convertToEntityAttribute(String dbData) {
        return LectureType.valueOf(dbData);
    }
}
