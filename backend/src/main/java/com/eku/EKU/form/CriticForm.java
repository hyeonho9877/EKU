package com.eku.EKU.form;

import com.eku.EKU.domain.LecturePrototype;
import com.eku.EKU.enums.Grade;
import lombok.Data;

@Data
public class CriticForm {
    private Long criticId;
    private Long studNo;
    private String content;
    private Grade grade;
    private Float star;
    private LecturePrototype lecture;
}
