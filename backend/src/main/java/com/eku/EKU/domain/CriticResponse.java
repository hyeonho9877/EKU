package com.eku.EKU.domain;

import com.eku.EKU.utils.GradeResponseConverter;
import lombok.Data;

/**
 * 강의평가 조회에 대한 리스폰스 객체
 */
@Data
public class CriticResponse {
    private long cId;
    private String lectureName;
    private String profName;
    private String content;
    private String grade;
    private String studNo;
    private String department;
    private float star;

    public CriticResponse(Critic critic) {
        this.cId = critic.getCId();
        this.content = critic.getContent();
        this.grade = GradeResponseConverter.convert(critic.getGrade());
        this.studNo = String.valueOf(critic.getWriter().getStudNo()).substring(2, 4);
        this.department = critic.getWriter().getDepartment();
        this.star = critic.getStar();
    }
}
