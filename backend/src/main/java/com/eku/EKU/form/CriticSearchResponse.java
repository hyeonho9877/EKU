package com.eku.EKU.form;

import com.eku.EKU.domain.Critic;
import com.eku.EKU.enums.Grade;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 특정 강의평가 조회에 대한 리스폰스 폼
 */
@Data
public class CriticSearchResponse {
    private String lectureName;
    private String professor;
    private Float star;
    private List<CriticInSpecificLecture> critics;

    public CriticSearchResponse(List<Critic> critics) {
        Critic rep = critics.get(0);
        this.star = rep.getLecture().getStar();
        this.lectureName = rep.getLecture().getLectureName();
        this.professor = rep.getLecture().getProfessor();
        this.critics = critics.stream().map(CriticInSpecificLecture::new).collect(Collectors.toList());
    }

    @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
    private static class CriticInSpecificLecture {
        private final Long cId;
        private final String content;
        private final Float star;
        private final Grade grade;

        public CriticInSpecificLecture(Critic critic) {
            this.content = critic.getContent();
            this.star = critic.getStar();
            this.grade = critic.getGrade();
            this.cId = critic.getCId();
        }
    }
}
