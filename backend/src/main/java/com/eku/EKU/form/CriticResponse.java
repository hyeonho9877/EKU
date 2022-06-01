package com.eku.EKU.form;

import com.eku.EKU.domain.Critic;
import com.eku.EKU.domain.LecturePrototype;
import com.eku.EKU.utils.GradeResponseConverter;
import lombok.Data;
import lombok.Getter;

/**
 * 최근 강의평가 조회에 쓰는 리스폰스 폼
 */
@Data
public class CriticResponse {
    private long cId;
    private String content;
    private String grade;
    private String studNo;
    private String department;
    private Float star;
    private LectureResponse lecture;

    public CriticResponse(Critic critic) {
        this.cId = critic.getCriticId();
        this.content = critic.getContent();
        this.grade = GradeResponseConverter.convert(critic.getGrade());
        this.studNo = String.valueOf(critic.getWriter().getStudNo()).substring(2, 4);
        this.department = critic.getWriter().getDepartment();
        this.star = critic.getStar();
        this.lecture = new LectureResponse(critic.getLecture());
    }

    @Getter
    private static class LectureResponse{
        private final String lectureName;
        private final String professor;

        public LectureResponse(LecturePrototype lecture) {
            this.lectureName = lecture.getLectureName();
            this.professor = lecture.getProfessor();
        }
    }
}
