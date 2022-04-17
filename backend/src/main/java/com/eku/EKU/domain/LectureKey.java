package com.eku.EKU.domain;

import com.eku.EKU.utils.LectureTypeConverter;
import lombok.Data;

import javax.persistence.Convert;
import java.io.Serializable;

@Data
public class LectureKey implements Serializable {
    private int year;
    private String lectureNo;
    private int semester;
    @Convert(converter = LectureTypeConverter.class)
    private LectureType lectureType;
}
