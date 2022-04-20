package com.eku.EKU.form;

import com.eku.EKU.domain.LecturePrototype;
import lombok.Data;

/**
 * 강의명이나 교수명으로 강평 검색할 때 쓰는 리스폰스 폼
 */
@Data
public class CriticLectureSearchResponse {
    private String lectureName;
    private String professor;
    private Float star;

    public CriticLectureSearchResponse(LecturePrototype lecture) {
        this.lectureName = lecture.getLectureName();
        this.professor = lecture.getProfessor();
        this.star = lecture.getStar();
    }
}
