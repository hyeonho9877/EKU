package com.eku.EKU.domain;

import com.eku.EKU.enums.Grade;
import com.eku.EKU.utils.GradeConverter;
import lombok.*;

import javax.persistence.*;

/**
 * 강의평가 DB 저장 객체
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Critic {
    @Id
    @Column(name = "c_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cId;
    @Column(name = "content", nullable = false)
    private String content;
    @Column(name = "grade", nullable = false)
    @Convert(converter = GradeConverter.class)
    private Grade grade;
    @Column(name = "star", nullable = false)
    private Float star;

    @ManyToOne
    private Student writer;

    @ManyToOne
    private LecturePrototype lecture;
}
